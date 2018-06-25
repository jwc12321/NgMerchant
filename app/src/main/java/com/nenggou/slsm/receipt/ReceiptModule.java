package com.nenggou.slsm.receipt;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/19.
 */
@Module
public class ReceiptModule {
    private ReceiptContract.ReceiptView receiptView;
    private ReceiptContract.QrCodeView qrCodeView;

    public ReceiptModule(ReceiptContract.ReceiptView receiptView) {
        this.receiptView = receiptView;
    }

    public ReceiptModule(ReceiptContract.QrCodeView qrCodeView) {
        this.qrCodeView = qrCodeView;
    }

    @Provides
    ReceiptContract.ReceiptView provideReceiptView(){
        return receiptView;
    }

    @Provides
    ReceiptContract.QrCodeView provideQrCodeView(){
        return qrCodeView;
    }
}
