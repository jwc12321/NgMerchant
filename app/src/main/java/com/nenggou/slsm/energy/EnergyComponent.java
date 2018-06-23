package com.nenggou.slsm.energy;

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.energy.ui.EnergyActivity;
import com.nenggou.slsm.energy.ui.InEnergyFragment;
import com.nenggou.slsm.energy.ui.OutEnergyFragment;

import dagger.Component;

/**
 * Created by JWC on 2018/6/22.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {EnergyModule.class})
public interface EnergyComponent {
    void inject(EnergyActivity energyActivity);
    void inject(InEnergyFragment inEnergyFragment);
    void inject(OutEnergyFragment outEnergyFragment);
}
