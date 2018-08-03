package com.nenggou.slsm.energy.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.EnergyInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.EnergyRequest;
import com.nenggou.slsm.energy.EnergyContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/23.
 */

public class EnergyListPresenter implements EnergyContract.EnergyListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private EnergyContract.EnergyListView energyListView;
    private int inCurrentIndex = 1;  //收益
    private int outCurrentIndex = 1;  //支出

    @Inject
    public EnergyListPresenter(RestApiService restApiService, EnergyContract.EnergyListView energyListView) {
        this.restApiService = restApiService;
        this.energyListView = energyListView;
    }

    @Inject
    public void setupListener() {
        energyListView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void getEnergyList(String refreshType, String type) {
        if (TextUtils.equals("1", refreshType)) {
            energyListView.showLoading();
        }
        if (TextUtils.equals("0", type)) {
            inCurrentIndex = 1;
        } else {
            outCurrentIndex = 1;
        }
        EnergyRequest energyRequest = new EnergyRequest(type, String.valueOf(1));
        Disposable disposable = restApiService.getEnergyInfo(energyRequest)
                .flatMap(new RxRemoteDataParse<EnergyInfo>())
                .compose(new RxSchedulerTransformer<EnergyInfo>())
                .subscribe(new Consumer<EnergyInfo>() {
                    @Override
                    public void accept(EnergyInfo energyInfo) throws Exception {
                        energyListView.dismissLoading();
                        if(energyInfo!=null) {
                            energyListView.backEnergy(energyInfo.getSum(), energyInfo.getProportion());
                            energyListView.render(energyInfo.getEnergyListInfo().getEnergyDetailInfos());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        energyListView.dismissLoading();
                        energyListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreEnergyList(String type) {
        EnergyRequest energyRequest;
        if (TextUtils.equals("0", type)) {
            inCurrentIndex = inCurrentIndex + 1;
            energyRequest = new EnergyRequest(type, String.valueOf(inCurrentIndex));
        } else {
            outCurrentIndex = outCurrentIndex + 1;
            energyRequest = new EnergyRequest(type, String.valueOf(outCurrentIndex));
        }
        Disposable disposable = restApiService.getEnergyInfo(energyRequest)
                .flatMap(new RxRemoteDataParse<EnergyInfo>())
                .compose(new RxSchedulerTransformer<EnergyInfo>())
                .subscribe(new Consumer<EnergyInfo>() {
                    @Override
                    public void accept(EnergyInfo energyInfo) throws Exception {
                        if(energyInfo!=null) {
                            energyListView.renderMore(energyInfo.getEnergyListInfo().getEnergyDetailInfos());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        energyListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
