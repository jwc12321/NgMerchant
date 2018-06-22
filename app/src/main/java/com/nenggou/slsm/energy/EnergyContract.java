package com.nenggou.slsm.energy;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.EnergyDetailInfo;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public interface EnergyContract {
    interface EnergyListPresenter extends BasePresenter {
        void getEnergyList(String type);

        void getMoreEnergyList(String type);
    }

    interface EnergyListView extends BaseView<EnergyListPresenter> {
        void render(List<EnergyDetailInfo> energyDetailInfos);

        void renderMore(List<EnergyDetailInfo> energyDetailInfos);
    }
}
