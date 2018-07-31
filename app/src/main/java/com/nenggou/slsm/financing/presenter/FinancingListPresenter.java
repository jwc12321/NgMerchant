package com.nenggou.slsm.financing.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.FinancingInfo;
import com.nenggou.slsm.data.entity.RIncomeInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PageRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/31.
 */

public class FinancingListPresenter implements FinancingContract.FinancingListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.FinancindListView financindListView;
    private int currentIndex = 1;

    @Inject
    public FinancingListPresenter(RestApiService restApiService, FinancingContract.FinancindListView financindListView) {
        this.restApiService = restApiService;
        this.financindListView = financindListView;
    }


    @Inject
    public void setupListener() {
        financindListView.setPresenter(this);
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
    public void getFinancingInfos(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            financindListView.showLoading();
        }
        currentIndex = 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getFinancingInfos(pageRequest)
                .flatMap(new RxRemoteDataParse<FinancingInfo>())
                .compose(new RxSchedulerTransformer<FinancingInfo>())
                .subscribe(new Consumer<FinancingInfo>() {
                    @Override
                    public void accept(FinancingInfo financingInfo) throws Exception {
                        financindListView.dismissLoading();
                        financindListView.renderFinancingInfos(financingInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        financindListView.dismissLoading();
                        financindListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreFinancinInfos() {
        currentIndex = currentIndex+1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getFinancingInfos(pageRequest)
                .flatMap(new RxRemoteDataParse<FinancingInfo>())
                .compose(new RxSchedulerTransformer<FinancingInfo>())
                .subscribe(new Consumer<FinancingInfo>() {
                    @Override
                    public void accept(FinancingInfo financingInfo) throws Exception {
                        financindListView.dismissLoading();
                        financindListView.renderMoreFinancingInfos(financingInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        financindListView.dismissLoading();
                        financindListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
