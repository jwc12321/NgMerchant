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

    public RankingModule(RankingContract.CRankingView cRankingView) {
        this.cRankingView = cRankingView;
    }

    public RankingModule(RankingContract.RRankingView rRankingView) {
        this.rRankingView = rRankingView;
    }

    @Provides
    RankingContract.CRankingView provideCRankingView(){
        return cRankingView;
    }

    @Provides
    RankingContract.RRankingView provideRRankingView(){
        return rRankingView;
    }
}
