package com.nenggou.slsm.ranking.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.RIncomeInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.RIncomeRequest;
import com.nenggou.slsm.ranking.RankingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/27.
 */

public class RIncomePresenter implements RankingContract.RIncomePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private RankingContract.RIncomeView rIncomeView;
    private int currentIndex = 1;

    @Inject
    public RIncomePresenter(RestApiService restApiService, RankingContract.RIncomeView rIncomeView) {
        this.restApiService = restApiService;
        this.rIncomeView = rIncomeView;
    }

    @Inject
    public void setupListener() {
        rIncomeView.setPresenter(this);
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
    public void getRIncomeList(String refreshType, String uid, String type, String starttime) {
        if (TextUtils.equals("1", refreshType)) {
            rIncomeView.showLoading();
        }
        currentIndex = 1;
        RIncomeRequest rIncomeRequest = new RIncomeRequest(String.valueOf(currentIndex),uid, type, starttime);
        Disposable disposable = restApiService.getRIncomeInfo(rIncomeRequest)
                .flatMap(new RxRemoteDataParse<RIncomeInfo>())
                .compose(new RxSchedulerTransformer<RIncomeInfo>())
                .subscribe(new Consumer<RIncomeInfo>() {
                    @Override
                    public void accept(RIncomeInfo rIncomeInfo) throws Exception {
                        rIncomeView.dismissLoading();
                        rIncomeView.renderRIncomeList(rIncomeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rIncomeView.dismissLoading();
                        rIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreRIncomeList(String uid, String type, String starttime) {
        currentIndex = currentIndex+1;
        RIncomeRequest rIncomeRequest = new RIncomeRequest(String.valueOf(currentIndex),uid, type, starttime);
        Disposable disposable = restApiService.getRIncomeInfo(rIncomeRequest)
                .flatMap(new RxRemoteDataParse<RIncomeInfo>())
                .compose(new RxSchedulerTransformer<RIncomeInfo>())
                .subscribe(new Consumer<RIncomeInfo>() {
                    @Override
                    public void accept(RIncomeInfo rIncomeInfo) throws Exception {
                        rIncomeView.dismissLoading();
                        rIncomeView.renderMoreRIncomeList(rIncomeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rIncomeView.dismissLoading();
                        rIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
