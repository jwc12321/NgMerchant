package com.nenggou.slsm.financing;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/7/24.
 */
@Module
public class FinancingModule {
    private FinancingContract.FinancindListView financindListView;
    private FinancingContract.PayFcOrderView payFcOrderView;

    public FinancingModule(FinancingContract.FinancindListView financindListView) {
        this.financindListView = financindListView;
    }

    public FinancingModule(FinancingContract.PayFcOrderView payFcOrderView) {
        this.payFcOrderView = payFcOrderView;
    }

    @Provides
    FinancingContract.FinancindListView provideFinancindListView(){
        return financindListView;
    }

    @Provides
    FinancingContract.PayFcOrderView providePayFcOrderView(){
        return payFcOrderView;
    }
}
