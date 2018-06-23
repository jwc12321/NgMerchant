package com.nenggou.slsm.address;

/**
 * Created by JWC on 2018/6/23.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.address.ui.AddressTelActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {AddressModule.class})
public interface AddressComponent {
    void inject(AddressTelActivity addressTelActivity);
}
