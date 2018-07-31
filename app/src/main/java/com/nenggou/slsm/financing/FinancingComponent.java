package com.nenggou.slsm.financing;

/**
 * Created by JWC on 2018/7/24.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.financing.ui.FinancingFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {FinancingModule.class})
public interface FinancingComponent {
    void inject(FinancingFragment financingFragment);
}
