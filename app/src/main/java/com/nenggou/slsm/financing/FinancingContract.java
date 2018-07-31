package com.nenggou.slsm.financing;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.FinancingInfo;

/**
 * Created by JWC on 2018/7/24.
 */

public interface FinancingContract {
    interface FinancingListPresenter extends BasePresenter{
        void getFinancingInfos(String refreshType);
        void getMoreFinancinInfos();
    }

    interface FinancindListView extends BaseView<FinancingListPresenter>{
        void renderFinancingInfos(FinancingInfo financingInfo);
        void renderMoreFinancingInfos(FinancingInfo financingInfo);
    }

}
