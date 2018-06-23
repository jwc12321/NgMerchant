package com.nenggou.slsm.feedback;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/6/23.
 */
@Module
public class FeedbackModule {
    private FeedbackContract.FeedbackView feedbackView;

    public FeedbackModule(FeedbackContract.FeedbackView feedbackView) {
        this.feedbackView = feedbackView;
    }
    @Provides
    FeedbackContract.FeedbackView provideFeedbackView(){
        return feedbackView;
    }
}
