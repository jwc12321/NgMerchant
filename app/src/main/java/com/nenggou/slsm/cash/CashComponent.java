package com.nenggou.slsm.cash;

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.cash.ui.CashActivity;
import com.nenggou.slsm.cash.ui.IncomeFragment;
import com.nenggou.slsm.cash.ui.OutcomeFragment;

import dagger.Component;

/**
 * Created by JWC on 2018/6/22.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CashModule.class})
public interface CashComponent {
    void inject(CashActivity cashActivity);
    void inject(IncomeFragment incomeFragment);
    void inject(OutcomeFragment outcomeFragment);
}
