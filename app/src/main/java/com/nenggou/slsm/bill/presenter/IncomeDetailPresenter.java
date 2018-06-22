package com.nenggou.slsm.bill.presenter;

import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.IncomeDetailInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.BillIdRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/22.
 */

public class IncomeDetailPresenter implements BillContract.IncomeDetailPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BillContract.IncomeDetailView incomeDetailView;

    @Inject
    public IncomeDetailPresenter(RestApiService restApiService, BillContract.IncomeDetailView incomeDetailView) {
        this.restApiService = restApiService;
        this.incomeDetailView = incomeDetailView;
    }

    @Inject
    public void setupListener() {
        incomeDetailView.setPresenter(this);
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
    public void getIncomeDetail(String billId) {
        incomeDetailView.showLoading();
        BillIdRequest billIdRequest = new BillIdRequest(billId);
        Disposable disposable = restApiService.getIncomeDetail(billIdRequest)
                .flatMap(new RxRemoteDataParse<IncomeDetailInfo>())
                .compose(new RxSchedulerTransformer<IncomeDetailInfo>())
                .subscribe(new Consumer<IncomeDetailInfo>() {
                    @Override
                    public void accept(IncomeDetailInfo incomeDetailInfo) throws Exception {
                        incomeDetailView.dismissLoading();
                        incomeDetailView.renderIncomeDetail(incomeDetailInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        incomeDetailView.dismissLoading();
                        incomeDetailView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
