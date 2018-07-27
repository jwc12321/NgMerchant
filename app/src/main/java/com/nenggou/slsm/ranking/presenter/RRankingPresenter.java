package com.nenggou.slsm.ranking.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.RankingListInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.CRankingRequest;
import com.nenggou.slsm.ranking.RankingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/27.
 */

public class RRankingPresenter implements RankingContract.RRankingPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private RankingContract.RRankingView rRankingView;
    private int currentIndex = 1;

    @Inject
    public RRankingPresenter(RestApiService restApiService, RankingContract.RRankingView rRankingView) {
        this.restApiService = restApiService;
        this.rRankingView = rRankingView;
    }

    @Inject
    public void setupListener() {
        rRankingView.setPresenter(this);
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
    public void getRRankingList(String refreshType, String type, String starttime) {
        if (TextUtils.equals("1", refreshType)) {
            rRankingView.showLoading();
        }
        currentIndex = 1;
        CRankingRequest rankingRequest = new CRankingRequest(String.valueOf(currentIndex), type, starttime);
        Disposable disposable = restApiService.getRRankingListInfo(rankingRequest)
                .flatMap(new RxRemoteDataParse<RankingListInfo>())
                .compose(new RxSchedulerTransformer<RankingListInfo>())
                .subscribe(new Consumer<RankingListInfo>() {
                    @Override
                    public void accept(RankingListInfo rankingListInfo) throws Exception {
                        rRankingView.dismissLoading();
                        rRankingView.renderRRankingList(rankingListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rRankingView.dismissLoading();
                        rRankingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreRRankingList(String type, String starttime) {
        currentIndex = currentIndex + 1;
        CRankingRequest rankingRequest = new CRankingRequest(String.valueOf(currentIndex), type, starttime);
        Disposable disposable = restApiService.getRRankingListInfo(rankingRequest)
                .flatMap(new RxRemoteDataParse<RankingListInfo>())
                .compose(new RxSchedulerTransformer<RankingListInfo>())
                .subscribe(new Consumer<RankingListInfo>() {
                    @Override
                    public void accept(RankingListInfo rankingListInfo) throws Exception {
                        rRankingView.dismissLoading();
                        rRankingView.renderRRankingList(rankingListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rRankingView.dismissLoading();
                        rRankingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
