package com.nenggou.slsm.receipt;

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.receipt.ui.ReceiptFragment;

import dagger.Component;

/**
 * Created by JWC on 2018/6/19.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ReceiptModule.class})
public interface ReceiptComponent {
    void inject(ReceiptFragment receiptFragment);
}
