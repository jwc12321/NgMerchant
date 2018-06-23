package com.nenggou.slsm.bankcard.presenter;

import android.media.session.MediaSession;
import android.text.TextUtils;

import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.BankCardInfo;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.TokenRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/23.
 */

public class BankCardListPresenter implements BankCardContract.BankCardListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.BankCardListView bankCardListView;

    @Inject
    public BankCardListPresenter(RestApiService restApiService, BankCardContract.BankCardListView bankCardListView) {
        this.restApiService = restApiService;
        this.bankCardListView = bankCardListView;
    }

    @Inject
    public void setupListener() {
        bankCardListView.setPresenter(this);
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
    public void getBankCardList(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            bankCardListView.showLoading();
        }
        TokenRequest tokenRequest = new TokenRequest();
        Disposable disposable = restApiService.getBankCardInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<BankCardInfo>>())
                .compose(new RxSchedulerTransformer<List<BankCardInfo>>())
                .subscribe(new Consumer<List<BankCardInfo>>() {
                    @Override
                    public void accept(List<BankCardInfo> bankCardInfos) throws Exception {
                        bankCardListView.dismissLoading();
                        bankCardListView.renderBankCardList(bankCardInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardListView.dismissLoading();
                        bankCardListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
