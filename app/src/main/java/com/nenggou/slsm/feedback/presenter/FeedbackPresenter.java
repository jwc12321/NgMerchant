package com.nenggou.slsm.feedback.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.FeedbackRequest;
import com.nenggou.slsm.feedback.FeedbackContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/23.
 */

public class FeedbackPresenter implements FeedbackContract.FeedbackPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FeedbackContract.FeedbackView feedbackView;

    @Inject
    public FeedbackPresenter(RestApiService restApiService, FeedbackContract.FeedbackView feedbackView) {
        this.restApiService = restApiService;
        this.feedbackView = feedbackView;
    }

    @Inject
    public void setupListener() {
        feedbackView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    @Override
    public void subFeedBack(String feedbackinfo) {
        feedbackView.showLoading();
        FeedbackRequest feedbackRequest=new FeedbackRequest(feedbackinfo);
        Disposable disposable=restApiService.subFeedBack(feedbackRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String strings) throws Exception {
                        feedbackView.dismissLoading();
                        feedbackView.subSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        feedbackView.dismissLoading();
                        feedbackView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
