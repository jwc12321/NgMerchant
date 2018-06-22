package com.nenggou.slsm.evaluate;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JWC on 2018/5/5.
 */

@Module
public class EvaluateModule {
    private EvaluateContract.AllEvaluationView allEvaluationView;


    public EvaluateModule(EvaluateContract.AllEvaluationView allEvaluationView) {
        this.allEvaluationView = allEvaluationView;
    }


    @Provides
    EvaluateContract.AllEvaluationView provideAllEvaluationView() {
        return allEvaluationView;
    }

}
