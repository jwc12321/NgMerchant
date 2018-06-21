package com.nenggou.slsm.receipt;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.AppstoreInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/19.
 */

public interface ReceiptContract {
    interface ReceiptPresenter extends BasePresenter{
        void getAppstoreInfos();
    }
    interface ReceiptView extends BaseView<ReceiptPresenter>{
        void renderAppstoreInfos(List<AppstoreInfo> appstoreInfos);
    }
}
