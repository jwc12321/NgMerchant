package com.nenggou.slsm.bill.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.HistoryIncomInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.HistoryIncomeRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/22.
 */

public class HistoryIncomePresenter implements BillContract.HistoryIncomePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BillContract.HistoryIncomeView historyIncomeView;
    private int currentIndex = 1;  //待接单当前index

    @Inject
    public HistoryIncomePresenter(RestApiService restApiService, BillContract.HistoryIncomeView historyIncomeView) {
        this.restApiService = restApiService;
        this.historyIncomeView = historyIncomeView;
    }

    @Inject
    public void setupListener() {
        historyIncomeView.setPresenter(this);
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
    public void getHistoryIncome(String refreshType, String storeid, String startTime, String endTime) {
        if (TextUtils.equals("1", refreshType)) {
            historyIncomeView.showLoading();
        }
        currentIndex = 1;
        HistoryIncomeRequest historyIncomeRequest = new HistoryIncomeRequest(storeid, startTime, endTime, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getHistoryIncomInfo(historyIncomeRequest)
                .flatMap(new RxRemoteDataParse<HistoryIncomInfo>())
                .compose(new RxSchedulerTransformer<HistoryIncomInfo>())
                .subscribe(new Consumer<HistoryIncomInfo>() {
                    @Override
                    public void accept(HistoryIncomInfo historyIncomInfo) throws Exception {
                        historyIncomeView.dismissLoading();
                        historyIncomeView.renderHistoryIncome(historyIncomInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        historyIncomeView.dismissLoading();
                        historyIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreHistoryIncome(String storeid, String startTime, String endTime) {
        currentIndex = currentIndex + 1;
        HistoryIncomeRequest historyIncomeRequest = new HistoryIncomeRequest(storeid, startTime, endTime, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getHistoryIncomInfo(historyIncomeRequest)
                .flatMap(new RxRemoteDataParse<HistoryIncomInfo>())
                .compose(new RxSchedulerTransformer<HistoryIncomInfo>())
                .subscribe(new Consumer<HistoryIncomInfo>() {
                    @Override
                    public void accept(HistoryIncomInfo historyIncomInfo) throws Exception {
                        historyIncomeView.dismissLoading();
                        historyIncomeView.renderMoreHistoryIncome(historyIncomInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        historyIncomeView.dismissLoading();
                        historyIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
