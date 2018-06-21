package com.nenggou.slsm.receipt;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/19.
 */
@Module
public class ReceiptModule {
    private ReceiptContract.ReceiptView receiptView;

    public ReceiptModule(ReceiptContract.ReceiptView receiptView) {
        this.receiptView = receiptView;
    }

    @Provides
    ReceiptContract.ReceiptView provideReceiptView(){
        return receiptView;
    }
}
