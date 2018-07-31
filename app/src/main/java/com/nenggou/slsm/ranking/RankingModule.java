package com.nenggou.slsm.ranking;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/7/24.
 */
@Module
public class RankingModule {
    private RankingContract.CRankingView cRankingView;
    private RankingContract.RRankingView rRankingView;
    private RankingContract.RIncomeView rIncomeView;
    private RankingContract.CouponView couponView;

    public RankingModule(RankingContract.CRankingView cRankingView) {
        this.cRankingView = cRankingView;
    }

    public RankingModule(RankingContract.RRankingView rRankingView) {
        this.rRankingView = rRankingView;
    }

    public RankingModule(RankingContract.RIncomeView rIncomeView) {
        this.rIncomeView = rIncomeView;
    }

    public RankingModule(RankingContract.CouponView couponView) {
        this.couponView = couponView;
    }

    @Provides
    RankingContract.CRankingView provideCRankingView(){
        return cRankingView;
    }

    @Provides
    RankingContract.RRankingView provideRRankingView(){
        return rRankingView;
    }

    @Provides
    RankingContract.RIncomeView provideRIncomeView(){
        return rIncomeView;
    }

    @Provides
    RankingContract.CouponView provideCouponView(){
        return couponView;
    }
}
