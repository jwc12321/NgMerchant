package com.nenggou.slsm.cash.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.cash.CashContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.CashDate;
import com.nenggou.slsm.data.entity.CashDetailList;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.CashDetailListRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/22.
 */

public class CashListPresenter implements CashContract.CashListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CashContract.CashListView cashListView;

    private int inComeCurrentIndex = 1;  //待接单当前index
    private int outComeCurrentIndex = 1; //待确定当前index

    @Inject
    public CashListPresenter(RestApiService restApiService, CashContract.CashListView cashListView) {
        this.restApiService = restApiService;
        this.cashListView = cashListView;
    }

    @Inject
    public void setupListener() {
        cashListView.setPresenter(this);
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

    /**
     * @param refreshType
     * @param type        0:收入 3：支出
     */
    @Override
    public void getCashList(String refreshType, String type) {
        if (TextUtils.equals("1", refreshType)) {
            cashListView.showLoading();
        }
        if (TextUtils.equals("0", type)) {
            inComeCurrentIndex = 1;
        } else {
            outComeCurrentIndex = 1;
        }
        CashDetailListRequest cashDetailListRequest = new CashDetailListRequest(type, String.valueOf(1));
        Disposable disposable = restApiService.getCashDetailList(cashDetailListRequest)
                .flatMap(new RxRemoteDataParse<CashDate>())
                .compose(new RxSchedulerTransformer<CashDate>())
                .subscribe(new Consumer<CashDate>() {
                    @Override
                    public void accept(CashDate cashDate) throws Exception {
                        cashListView.dismissLoading();
                        cashListView.render(cashDate.getCashDetailList().getCashDetailInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cashListView.dismissLoading();
                        cashListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreCashList(String type) {
        CashDetailListRequest cashDetailListRequest;
        if (TextUtils.equals("0", type)) {
            inComeCurrentIndex = inComeCurrentIndex + 1;
            cashDetailListRequest = new CashDetailListRequest(type, String.valueOf(inComeCurrentIndex));
        } else {
            outComeCurrentIndex = outComeCurrentIndex + 1;
            cashDetailListRequest = new CashDetailListRequest(type, String.valueOf(outComeCurrentIndex));
        }
        Disposable disposable = restApiService.getCashDetailList(cashDetailListRequest)
                .flatMap(new RxRemoteDataParse<CashDate>())
                .compose(new RxSchedulerTransformer<CashDate>())
                .subscribe(new Consumer<CashDate>() {
                    @Override
                    public void accept(CashDate cashDate) throws Exception {
                        cashListView.dismissLoading();
                        cashListView.renderMore(cashDate.getCashDetailList().getCashDetailInfos());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cashListView.dismissLoading();
                        cashListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
