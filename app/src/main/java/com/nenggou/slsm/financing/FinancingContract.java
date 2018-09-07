package com.nenggou.slsm.financing;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.FinancingInfo;
import com.nenggou.slsm.data.entity.FinancingItemInfo;
import com.nenggou.slsm.data.entity.PayFcOrderInfo;

import java.util.List;

/**
 * Created by JWC on 2018/7/24.
 */

public interface FinancingContract {
    interface FinancingListPresenter extends BasePresenter{
        void getFinancingInfos(String refreshType,String pricetype);
        void getMoreFinancinInfos(String pricetype);
    }

    interface FinancindListView extends BaseView<FinancingListPresenter>{
        void render(List<FinancingItemInfo> financingItemInfos);
        void renderMore(List<FinancingItemInfo> financingItemInfos);
    }

    interface PayFcOrderPresenter extends BasePresenter{
        void getPayFcOrderInfo(String financingid);
    }

    interface PayFcOrderView extends BaseView<PayFcOrderPresenter>{
        void renderPayFcOrderInfo(PayFcOrderInfo payFcOrderInfo);
    }

}
