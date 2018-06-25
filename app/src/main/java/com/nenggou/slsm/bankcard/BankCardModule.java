package com.nenggou.slsm.bankcard;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/23.
 */

@Module
public class BankCardModule {
    private BankCardContract.BankCardListView bankCardListView;
    private BankCardContract.AddbankcardView addbankcardView;
    private BankCardContract.PutForwardView putForwardView;

    public BankCardModule(BankCardContract.BankCardListView bankCardListView) {
        this.bankCardListView = bankCardListView;
    }

    public BankCardModule(BankCardContract.AddbankcardView addbankcardView) {
        this.addbankcardView = addbankcardView;
    }

    public BankCardModule(BankCardContract.PutForwardView putForwardView) {
        this.putForwardView = putForwardView;
    }

    @Provides
    BankCardContract.BankCardListView provideBankCardListView(){
        return bankCardListView;
    }
    @Provides
    BankCardContract.AddbankcardView provideAddbankcardView(){
        return addbankcardView;
    }

    @Provides
    BankCardContract.PutForwardView providePutForwardView(){
        return putForwardView;
    }
}
