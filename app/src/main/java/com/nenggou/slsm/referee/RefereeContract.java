package com.nenggou.slsm.referee;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.RdList;

/**
 * Created by JWC on 2018/8/14.
 */

public interface RefereeContract {
    interface RdListPresenter extends BasePresenter{
        void getRdList(String refreshType);
        void getRdMoreList();
    }
    interface RdListView extends BaseView<RdListPresenter>{
        void renderRdList(RdList rdList);
        void renderMoreRdList(RdList rdList);
    }
}
