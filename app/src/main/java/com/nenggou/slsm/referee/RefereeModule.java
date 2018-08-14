package com.nenggou.slsm.referee;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/8/14.
 */

@Module
public class RefereeModule {
    private RefereeContract.RdListView rdListView;

    public RefereeModule(RefereeContract.RdListView rdListView) {
        this.rdListView = rdListView;
    }

    @Provides
    RefereeContract.RdListView provideRdListView(){
        return rdListView;
    }
}
