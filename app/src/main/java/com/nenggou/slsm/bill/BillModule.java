package com.nenggou.slsm.bill;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/19.
 */
@Module
public class BillModule {
    private BillContract.DayIncomeView dayIncomeView;

    public BillModule(BillContract.DayIncomeView dayIncomeView) {
        this.dayIncomeView = dayIncomeView;
    }
    @Provides
    BillContract.DayIncomeView provideDayIncomeView(){
        return dayIncomeView;
    }
}
