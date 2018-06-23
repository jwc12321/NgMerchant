package com.nenggou.slsm.address;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/23.
 */
@Module
public class AddressModule {
    private AddressContract.AddressView addressView;

    public AddressModule(AddressContract.AddressView addressView) {
        this.addressView = addressView;
    }
    @Provides
    AddressContract.AddressView provideAddressView(){
        return addressView;
    }
}
