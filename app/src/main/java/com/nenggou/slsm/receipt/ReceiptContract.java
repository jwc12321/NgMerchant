package com.nenggou.slsm.receipt;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.entity.ChangeAppInfo;
import com.nenggou.slsm.mainframe.MainFrameContract;

import java.util.List;

/**
 * Created by JWC on 2018/6/19.
 */

public interface ReceiptContract {
    interface ReceiptPresenter extends BasePresenter{
        void getAppstoreInfos(String refreshType);
        void detectionVersion(String edition, String type);
    }
    interface ReceiptView extends BaseView<ReceiptPresenter>{
        void renderAppstoreInfos(List<AppstoreInfo> appstoreInfos);
        void detectionSuccess(ChangeAppInfo changeAppInfo);
    }

    interface QrCodePresenter extends BasePresenter{
        void uploadQrText(String text);
    }

    interface QrCodeView extends BaseView<QrCodePresenter>{
        void backQrText(String backQrtext);
    }
}
