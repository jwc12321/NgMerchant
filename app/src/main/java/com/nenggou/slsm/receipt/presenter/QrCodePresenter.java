package com.nenggou.slsm.receipt.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.TextRequest;
import com.nenggou.slsm.receipt.ReceiptContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/25.
 */

public class QrCodePresenter implements ReceiptContract.QrCodePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private ReceiptContract.QrCodeView qrCodeView;

    @Inject
    public QrCodePresenter(RestApiService restApiService, ReceiptContract.QrCodeView qrCodeView) {
        this.restApiService = restApiService;
        this.qrCodeView = qrCodeView;
    }

    @Inject
    public void setupListener() {
        qrCodeView.setPresenter(this);
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
    public void uploadQrText(String text) {
        TextRequest textRequest=new TextRequest(text);
        Disposable disposable=restApiService.uploadQrText(textRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        qrCodeView.dismissLoading();
                        qrCodeView.backQrText(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        qrCodeView.dismissLoading();
                        qrCodeView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
