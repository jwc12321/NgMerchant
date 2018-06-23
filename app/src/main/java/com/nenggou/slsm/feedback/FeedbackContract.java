package com.nenggou.slsm.feedback;

import com.nenggou.slsm.BasePresenter;
import com.nenggou.slsm.BaseView;

/**
 * Created by JWC on 2018/6/23.
 */

public interface FeedbackContract {
    interface FeedbackPresenter extends BasePresenter{
        void subFeedBack(String feedbackinfo);
    }
    interface FeedbackView extends BaseView<FeedbackPresenter>{
        void subSuccess();
    }
}
