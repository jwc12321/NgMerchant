package com.nenggou.slsm.bill;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.entity.HistoryIncomInfo;
import com.nenggou.slsm.data.entity.IncomeDetailInfo;
import com.nenggou.slsm.data.entity.IntercourseRecordInfo;

/**
 * Created by JWC on 2018/6/19.
 */

public interface BillContract {
    interface DayIncomePresenter extends BasePresenter {
        void getDayIncome(String refreshType, String storeid,String date);

        void getMoreDayIncome(String storeid,String date);

        void getRdDayIncome(String refreshType,String time);

        void getMoreRdDayIncome(String time);
    }

    interface DayIncomeView extends BaseView<DayIncomePresenter> {
        void renderDayIncome(BillInfo billInfo);

        void renderMoreDayIncome(BillInfo billInfo);
    }

    interface HistoryIncomePresenter extends BasePresenter {
        void getHistoryIncome(String refreshType, String storeid, String startTime, String endTime);

        void getMoreHistoryIncome(String storeid, String startTime, String endTime);
    }

    interface HistoryIncomeView extends BaseView<HistoryIncomePresenter> {
        void renderHistoryIncome(HistoryIncomInfo historyIncomInfo);

        void renderMoreHistoryIncome(HistoryIncomInfo historyIncomInfo);
    }

    interface IncomeDetailPresenter extends BasePresenter{
        void getIncomeDetail(String billId);
    }
    interface IncomeDetailView extends BaseView<IncomeDetailPresenter>{
        void renderIncomeDetail(IncomeDetailInfo incomeDetailInfo);
    }

    interface RdIncomePresenter extends BasePresenter {
        void getRdIncome(String refreshType);

        void getMoreRdIncome();
    }

    interface RdIncomeView extends BaseView<RdIncomePresenter> {
        void renderRdIncome(HistoryIncomInfo historyIncomInfo);

        void renderMoreRdIncome(HistoryIncomInfo historyIncomInfo);
    }

    interface IntercourseRecordPresenter extends BasePresenter{
        void getIntercourseRecordInfo(String refreshType,String uid,String type, String starttime);
        void getMoreIntercourseRecordInfo(String uid,String type, String starttime);
    }
    interface IntercourseRecordView extends BaseView<IntercourseRecordPresenter>{
        void intercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo);
        void moreIntercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo);
    }
}
