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

public class CRankingPresenter implements RankingContract.CRankingPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private RankingContract.CRankingView cRankingView;
    private int currentIndex = 1;

    @Inject
    public CRankingPresenter(RestApiService restApiService, RankingContract.CRankingView cRankingView) {
        this.restApiService = restApiService;
        this.cRankingView = cRankingView;
    }

    @Inject
    public void setupListener() {
        cRankingView.setPresenter(this);
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
    public void getCRankingList(String refreshType, String type, String starttime) {
        if (TextUtils.equals("1", refreshType)) {
            cRankingView.showLoading();
        }
        currentIndex = 1;
        CRankingRequest rankingRequest = new CRankingRequest(String.valueOf(currentIndex), type, starttime);
        Disposable disposable = restApiService.getCRankingListInfo(rankingRequest)
                .flatMap(new RxRemoteDataParse<RankingListInfo>())
                .compose(new RxSchedulerTransformer<RankingListInfo>())
                .subscribe(new Consumer<RankingListInfo>() {
                    @Override
                    public void accept(RankingListInfo rankingListInfo) throws Exception {
                        cRankingView.dismissLoading();
                        cRankingView.renderCRankingList(rankingListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cRankingView.dismissLoading();
                        cRankingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreCRankingList(String type, String starttime) {
        currentIndex = currentIndex + 1;
        CRankingRequest rankingRequest = new CRankingRequest(String.valueOf(currentIndex), type, starttime);
        Disposable disposable = restApiService.getCRankingListInfo(rankingRequest)
                .flatMap(new RxRemoteDataParse<RankingListInfo>())
                .compose(new RxSchedulerTransformer<RankingListInfo>())
                .subscribe(new Consumer<RankingListInfo>() {
                    @Override
                    public void accept(RankingListInfo rankingListInfo) throws Exception {
                        cRankingView.dismissLoading();
                        cRankingView.renderMoreCRankingList(rankingListInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cRankingView.dismissLoading();
                        cRankingView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
