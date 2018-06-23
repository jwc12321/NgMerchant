package com.nenggou.slsm.feedback;

/**
 * Created by JWC on 2018/6/23.
 */

import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.feedback.ui.FeedBackActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {FeedbackModule.class})
public interface FeedbackComponent {
    void inject(FeedBackActivity feedBackActivity);
}
