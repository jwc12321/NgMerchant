package com.nenggou.slsm.bill.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.HistoryIncomInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.HistoryIncomeRequest;
import com.nenggou.slsm.data.request.PageRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/22.
 */

public class RdIncomePresenter implements BillContract.RdIncomePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BillContract.RdIncomeView rdIncomeView;
    private int currentIndex = 1;  //待接单当前index

    @Inject
    public RdIncomePresenter(RestApiService restApiService, BillContract.RdIncomeView rdIncomeView) {
        this.restApiService = restApiService;
        this.rdIncomeView = rdIncomeView;
    }


    @Inject
    public void setupListener() {
        rdIncomeView.setPresenter(this);
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
    public void getRdIncome(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            rdIncomeView.showLoading();
        }
        currentIndex = 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getRdIncomeInfo(pageRequest)
                .flatMap(new RxRemoteDataParse<HistoryIncomInfo>())
                .compose(new RxSchedulerTransformer<HistoryIncomInfo>())
                .subscribe(new Consumer<HistoryIncomInfo>() {
                    @Override
                    public void accept(HistoryIncomInfo historyIncomInfo) throws Exception {
                        rdIncomeView.dismissLoading();
                        rdIncomeView.renderRdIncome(historyIncomInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rdIncomeView.dismissLoading();
                        rdIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreRdIncome() {
        currentIndex = currentIndex + 1;
        PageRequest pageRequest = new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getRdIncomeInfo(pageRequest)
                .flatMap(new RxRemoteDataParse<HistoryIncomInfo>())
                .compose(new RxSchedulerTransformer<HistoryIncomInfo>())
                .subscribe(new Consumer<HistoryIncomInfo>() {
                    @Override
                    public void accept(HistoryIncomInfo historyIncomInfo) throws Exception {
                        rdIncomeView.dismissLoading();
                        rdIncomeView.renderMoreRdIncome(historyIncomInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rdIncomeView.dismissLoading();
                        rdIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
