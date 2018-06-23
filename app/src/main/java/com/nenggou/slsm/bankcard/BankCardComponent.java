package com.nenggou.slsm.bankcard;

/**
 * Created by JWC on 2018/6/23.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.bankcard.ui.AddBankImActivity;
import com.nenggou.slsm.bankcard.ui.BankCardListActivity;
import com.nenggou.slsm.bankcard.ui.PutForwardActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {BankCardModule.class})
public interface BankCardComponent {
    void inject(PutForwardActivity putForwardActivity);
    void inject(BankCardListActivity bankCardListActivity);
    void inject(AddBankImActivity addBankImActivity);
}
