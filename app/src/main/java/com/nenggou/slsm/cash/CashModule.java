package com.nenggou.slsm.cash;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/22.
 */
@Module
public class CashModule {
    private CashContract.CashView cashView;
    private CashContract.CashListView cashListView;

    public CashModule(CashContract.CashView cashView) {
        this.cashView = cashView;
    }

    public CashModule(CashContract.CashListView cashListView) {
        this.cashListView = cashListView;
    }

    @Provides
    CashContract.CashView provideCashView() {
        return cashView;
    }

    @Provides
    CashContract.CashListView provideCashListView(){
        return cashListView;
    }
}
