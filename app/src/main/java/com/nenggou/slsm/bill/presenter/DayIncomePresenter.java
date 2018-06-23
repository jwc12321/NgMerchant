package com.nenggou.slsm.bill.presenter;

import android.graphics.pdf.PdfDocument;
import android.text.TextUtils;

import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.DayIncomeRequest;
import com.nenggou.slsm.data.request.PageRequest;
import com.nenggou.slsm.data.request.RdIncomeRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/21.
 */

public class DayIncomePresenter implements BillContract.DayIncomePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BillContract.DayIncomeView dayIncomeView;
    private int currentIndex = 1;  //今日
    private int rdCurrentIndex = 1;//推荐

    @Inject
    public DayIncomePresenter(RestApiService restApiService, BillContract.DayIncomeView dayIncomeView) {
        this.restApiService = restApiService;
        this.dayIncomeView = dayIncomeView;
    }

    @Inject
    public void setupListener() {
        dayIncomeView.setPresenter(this);
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
    public void getDayIncome(String refreshType, String storeid, String date) {
        if (TextUtils.equals("1", refreshType)) {
            dayIncomeView.showLoading();
        }
        currentIndex = 1;
        DayIncomeRequest dayIncomeRequest = new DayIncomeRequest(storeid, date, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getBillInfo(dayIncomeRequest)
                .flatMap(new RxRemoteDataParse<BillInfo>())
                .compose(new RxSchedulerTransformer<BillInfo>())
                .subscribe(new Consumer<BillInfo>() {
                    @Override
                    public void accept(BillInfo billInfo) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.renderDayIncome(billInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreDayIncome(String storeid, String date) {
        currentIndex = currentIndex + 1;
        DayIncomeRequest dayIncomeRequest = new DayIncomeRequest(storeid, date, String.valueOf(currentIndex));
        Disposable disposable = restApiService.getBillInfo(dayIncomeRequest)
                .flatMap(new RxRemoteDataParse<BillInfo>())
                .compose(new RxSchedulerTransformer<BillInfo>())
                .subscribe(new Consumer<BillInfo>() {
                    @Override
                    public void accept(BillInfo billInfo) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.renderMoreDayIncome(billInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getRdDayIncome(String refreshType, String time) {
        if (TextUtils.equals("1", refreshType)) {
            dayIncomeView.showLoading();
        }
        rdCurrentIndex = 1;
        RdIncomeRequest rdIncomeRequest = new RdIncomeRequest(time, String.valueOf(rdCurrentIndex));
        Disposable disposable = restApiService.getRdDayIncome(rdIncomeRequest)
                .flatMap(new RxRemoteDataParse<BillInfo>())
                .compose(new RxSchedulerTransformer<BillInfo>())
                .subscribe(new Consumer<BillInfo>() {
                    @Override
                    public void accept(BillInfo billInfo) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.renderDayIncome(billInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreRdDayIncome(String time) {
        rdCurrentIndex = rdCurrentIndex + 1;
        RdIncomeRequest rdIncomeRequest = new RdIncomeRequest(time, String.valueOf(rdCurrentIndex));
        Disposable disposable = restApiService.getRdDayIncome(rdIncomeRequest)
                .flatMap(new RxRemoteDataParse<BillInfo>())
                .compose(new RxSchedulerTransformer<BillInfo>())
                .subscribe(new Consumer<BillInfo>() {
                    @Override
                    public void accept(BillInfo billInfo) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.renderMoreDayIncome(billInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dayIncomeView.dismissLoading();
                        dayIncomeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
