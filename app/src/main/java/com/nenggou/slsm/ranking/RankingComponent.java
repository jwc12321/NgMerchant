package com.nenggou.slsm.ranking;

/**
 * Created by JWC on 2018/7/24.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.ranking.ui.ConsumeRankingFragment;
import com.nenggou.slsm.ranking.ui.CouponListActivity;
import com.nenggou.slsm.ranking.ui.RIncomeListActivity;
import com.nenggou.slsm.ranking.ui.RRankingFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {RankingModule.class})
public interface RankingComponent {
    void inject(ConsumeRankingFragment consumeRankingFragment);
    void inject(RRankingFragment rRankingFragment);
    void inject(RIncomeListActivity rIncomeListActivity);
    void inject(CouponListActivity couponListActivity);
}
