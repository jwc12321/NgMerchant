package com.nenggou.slsm.financing;

/**
 * Created by JWC on 2018/7/24.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.financing.ui.CashFinancingFragment;
import com.nenggou.slsm.financing.ui.EnergyFinancingFragment;
import com.nenggou.slsm.financing.ui.FinancingFragment;
import com.nenggou.slsm.financing.ui.FinancingOrderDetailActivity;
import com.nenggou.slsm.financing.ui.FinancingWalletActivity;
import com.nenggou.slsm.financing.ui.InPOrderFragment;
import com.nenggou.slsm.financing.ui.PayFinancingOrderActivity;
import com.nenggou.slsm.financing.ui.ReimbursementFragment;
import com.nenggou.slsm.financing.ui.TurnOutBalanceActivity;
import com.nenggou.slsm.financing.ui.TurnOutRecordActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {FinancingModule.class})
public interface FinancingComponent {
    void inject(FinancingFragment financingFragment);
    void inject(EnergyFinancingFragment energyFinancingFragment);
    void inject(CashFinancingFragment cashFinancingFragment);
    void inject(PayFinancingOrderActivity payFinancingOrderActivity);
    void inject(FinancingOrderDetailActivity financingOrderDetailActivity);
    void inject(InPOrderFragment inPOrderFragment);
    void inject(ReimbursementFragment reimbursementFragment);
    void inject(FinancingWalletActivity financingWalletActivity);
    void inject(TurnOutBalanceActivity turnOutBalanceActivity);
    void inject(TurnOutRecordActivity turnOutRecordActivity);
}
