package com.nenggou.slsm.bill;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/19.
 */
@Module
public class BillModule {
    private BillContract.DayIncomeView dayIncomeView;
    private BillContract.HistoryIncomeView historyIncomeView;
    private BillContract.IncomeDetailView incomeDetailView;

    public BillModule(BillContract.DayIncomeView dayIncomeView) {
        this.dayIncomeView = dayIncomeView;
    }

    public BillModule(BillContract.HistoryIncomeView historyIncomeView) {
        this.historyIncomeView = historyIncomeView;
    }

    public BillModule(BillContract.IncomeDetailView incomeDetailView) {
        this.incomeDetailView = incomeDetailView;
    }

    @Provides
    BillContract.DayIncomeView provideDayIncomeView(){
        return dayIncomeView;
    }

    @Provides
    BillContract.HistoryIncomeView provideHistoryIncomeView(){
        return historyIncomeView;
    }

    @Provides
    BillContract.IncomeDetailView provideIncomeDetailView(){
        return incomeDetailView;
    }
}
