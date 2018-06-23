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

    public BankCardModule(BankCardContract.BankCardListView bankCardListView) {
        this.bankCardListView = bankCardListView;
    }

    public BankCardModule(BankCardContract.AddbankcardView addbankcardView) {
        this.addbankcardView = addbankcardView;
    }

    @Provides
    BankCardContract.BankCardListView provideBankCardListView(){
        return bankCardListView;
    }
    @Provides
    BankCardContract.AddbankcardView provideAddbankcardView(){
        return addbankcardView;
    }
}
