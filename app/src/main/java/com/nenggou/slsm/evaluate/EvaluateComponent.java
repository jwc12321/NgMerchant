package com.nenggou.slsm.evaluate;

/**
 * Created by JWC on 2018/5/5.
 */


import com.nenggou.slsm.ActivityScope;
import com.nenggou.slsm.ApplicationComponent;
import com.nenggou.slsm.evaluate.ui.AllEvaluationActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {EvaluateModule.class})
public interface EvaluateComponent {
    void inject(AllEvaluationActivity allEvaluationActivity);
}
