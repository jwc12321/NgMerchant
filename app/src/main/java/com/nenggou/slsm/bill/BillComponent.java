package com.nenggou.slsm.bill;

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.bill.ui.BillFragment;
import com.nenggou.slsm.bill.ui.HistoryIncomeActivity;
import com.nenggou.slsm.bill.ui.IncomeDetailActivity;
import com.nenggou.slsm.bill.ui.IncomeListActivity;
import com.nenggou.slsm.bill.ui.IntercourseRecordActivity;
import com.nenggou.slsm.bill.ui.MonthIncomeActivity;
import com.nenggou.slsm.bill.ui.RdIncomeActivity;

import dagger.Component;

/**
 * Created by JWC on 2018/6/19.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {BillModule.class})
public interface BillComponent {
    void inject(BillFragment billFragment);
    void inject(HistoryIncomeActivity historyIncomeActivity);
    void inject(MonthIncomeActivity monthIncomeActivity);
    void inject(IncomeListActivity incomeListActivity);
    void inject(IncomeDetailActivity incomeDetailActivity);
    void inject(RdIncomeActivity rdIncomeActivity);
    void inject(IntercourseRecordActivity intercourseRecordActivity);
}
