package com.nenggou.slsm.evaluate;


import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;
import com.nenggou.slsm.data.entity.AllEvaluationInfo;

/**
 * Created by JWC on 2018/5/5.
 */

public interface EvaluateContract {
    interface AllEvaluationPresenter extends BasePresenter {
        void getAllEvaluation(String refreshType, String storeId);

        void getMoreAllEvaluation(String storeId);
    }

    interface AllEvaluationView extends BaseView<AllEvaluationPresenter> {
        void renderAllEvaluation(AllEvaluationInfo allEvaluationInfo);

        void renderMoreAllEvaluation(AllEvaluationInfo allEvaluationInfo);
    }
}
