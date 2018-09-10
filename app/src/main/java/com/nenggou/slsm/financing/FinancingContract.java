package com.nenggou.slsm.financing;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.FcOrderDetailInfo;
import com.nenggou.slsm.data.entity.FcOrderList;
import com.nenggou.slsm.data.entity.FcWalletInfo;
import com.nenggou.slsm.data.entity.FinancingItemInfo;
import com.nenggou.slsm.data.entity.PayFcOrderInfo;
import com.nenggou.slsm.data.entity.TurnOutRecord;

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
        void payFcOrder(String financingid, String type, String paymoney, String paypassword);
        void isSetUpPayPw();
    }

    interface PayFcOrderView extends BaseView<PayFcOrderPresenter>{
        void renderPayFcOrderInfo(PayFcOrderInfo payFcOrderInfo);
        void payFcOrderSuccess();
        void renderIsSetUpPayPw(String what);
    }

    interface FcOrderDetailPresenter extends BasePresenter{
        void getFcOrderDetailInfo(String financingid);
    }

    interface FcOrderDetailView extends BaseView<FcOrderDetailPresenter>{
        void renderFcOrderDetailInfo(FcOrderDetailInfo fcOrderDetailInfo);
    }

    interface FcOrderListPresenter extends BasePresenter{
        void getFcOrderItemInfos(String refreshType,String status);
        void getMoreFcOrderItemInfos(String status);
    }

    interface FcOrderListView extends BaseView<FcOrderListPresenter>{
        void render(FcOrderList fcOrderList);
        void renderMore(FcOrderList fcOrderList);
    }

    interface FcWalletPresenter extends BasePresenter{
        void getFcWalletInfo();
    }

    interface FcWalletView extends BaseView<FcWalletPresenter>{
        void renderFcWalletInfo(FcWalletInfo fcWalletInfo);
    }

    interface TurnOutBalancePresenter extends BasePresenter{
        void verifyPayPassword(String payPassword);
        void turnOutBalance(String sum, String type, String paypassword);
    }

    interface TurnOutBalanceView extends BaseView<TurnOutBalancePresenter>{
        void verifySuccess();
        void turnOutSuccess();
    }

    interface TurnOutRecordPresenter extends BasePresenter{
       void getTurnOutRecord(String refreshType);
       void getMoreTurnOutRecord();
    }

    interface TurnOutRecordView extends BaseView<TurnOutRecordPresenter>{
        void renderRecords(TurnOutRecord turnOutRecord);
        void renderMoreRecords(TurnOutRecord turnOutRecord);
    }
}
