package com.nenggou.slsm.bill;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.BillInfo;

/**
 * Created by JWC on 2018/6/19.
 *
 */

public interface BillContract {
    interface DayIncomePresenter extends BasePresenter{
        void getDayIncome(String refreshType,String date);
        void getMoreDayIncome(String date);
    }
    interface DayIncomeView extends BaseView<DayIncomePresenter>{
        void renderDayIncome(BillInfo billInfo);
        void renderMoreDayIncome(BillInfo billInfo);
    }
}
