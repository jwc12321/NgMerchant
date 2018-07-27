package com.nenggou.slsm.ranking;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.RIncomeInfo;
import com.nenggou.slsm.data.entity.RankingListInfo;

/**
 * Created by JWC on 2018/7/24.
 */

public interface RankingContract {
    interface CRankingPresenter extends BasePresenter{
        void getCRankingList(String refreshType,String type,String starttime);
        void getMoreCRankingList(String type,String starttime);
    }
    interface CRankingView extends BaseView<CRankingPresenter>{
        void renderCRankingList(RankingListInfo rankingListInfo);
        void renderMoreCRankingList(RankingListInfo rankingListInfo);
    }

    interface RRankingPresenter extends BasePresenter{
        void getRRankingList(String refreshType,String type,String starttime);
        void getMoreRRankingList(String type,String starttime);
    }
    interface RRankingView extends BaseView<RRankingPresenter>{
        void renderRRankingList(RankingListInfo rankingListInfo);
        void renderMoreRRankingList(RankingListInfo rankingListInfo);
    }

    interface RIncomePresenter extends BasePresenter{
        void getRIncomeList(String refreshType,String uid,String type, String starttime);
        void getMoreRIncomeList(String uid,String type, String starttime);
    }

    interface RIncomeView extends BaseView<RIncomePresenter>{
        void renderRIncomeList(RIncomeInfo rIncomeInfo);
        void renderMoreRIncomeList(RIncomeInfo rIncomeInfo);
    }
}
