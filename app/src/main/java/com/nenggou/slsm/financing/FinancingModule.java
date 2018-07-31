package com.nenggou.slsm.financing;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/7/24.
 */
@Module
public class FinancingModule {
    private FinancingContract.FinancindListView financindListView;

    public FinancingModule(FinancingContract.FinancindListView financindListView) {
        this.financindListView = financindListView;
    }

    @Provides
    FinancingContract.FinancindListView provideFinancindListView(){
        return financindListView;
    }
}
