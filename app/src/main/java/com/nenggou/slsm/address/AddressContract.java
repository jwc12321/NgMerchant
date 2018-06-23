package com.nenggou.slsm.address;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.AppstoreInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/23.
 */

public interface AddressContract {
    interface AddressPresenter extends BasePresenter {
        void getAddressInfos(String refreshType);
    }
    interface AddressView extends BaseView<AddressPresenter> {
        void renderAddressInfos(List<AppstoreInfo> appstoreInfos);
    }
}
