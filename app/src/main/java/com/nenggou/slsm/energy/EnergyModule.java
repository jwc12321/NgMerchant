package com.nenggou.slsm.energy;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/22.
 */
@Module
public class EnergyModule {
    private EnergyContract.EnergyListView energyListView;

    public EnergyModule(EnergyContract.EnergyListView energyListView) {
        this.energyListView = energyListView;
    }

    @Provides
    EnergyContract.EnergyListView provideEnergyListView() {
        return energyListView;
    }
}
