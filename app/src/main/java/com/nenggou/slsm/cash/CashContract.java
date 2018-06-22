package com.nenggou.slsm.cash;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.CashDetailInfo;
import com.nenggou.slsm.data.entity.CashInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public interface CashContract {
    interface CashPresenter extends BasePresenter {
        void getCashInfo();
    }

    interface CashView extends BaseView<CashPresenter> {
        void renderCashInfo(CashInfo cashInfo);
    }

    interface CashListPresenter extends BasePresenter {
        void getCashList(String refreshType, String type);

        void getMoreCashList(String type);
    }

    interface CashListView extends BaseView<CashListPresenter> {
        void render(List<CashDetailInfo> cashDetailInfos);

        void renderMore(List<CashDetailInfo> cashDetailInfos);
    }
}
