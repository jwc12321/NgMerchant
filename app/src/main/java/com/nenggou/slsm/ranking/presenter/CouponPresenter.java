package com.nenggou.slsm.ranking.presenter;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.CouponInfo;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.SendOutCouponRequest;
import com.nenggou.slsm.data.request.TokenRequest;
import com.nenggou.slsm.ranking.RankingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/7/31.
 */

public class CouponPresenter implements RankingContract.CouponPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private RankingContract.CouponView couponView;

    @Inject
    public CouponPresenter(RestApiService restApiService, RankingContract.CouponView couponView) {
        this.restApiService = restApiService;
        this.couponView = couponView;
    }

    @Inject
    public void setupListener() {
        couponView.setPresenter(this);
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
    public void getCouponInfos() {
        couponView.showLoading();
        TokenRequest tokenRequest=new TokenRequest();
        Disposable disposable = restApiService.getCouoponInfos(tokenRequest)
                .flatMap(new RxRemoteDataParse<List<CouponInfo>>())
                .compose(new RxSchedulerTransformer<List<CouponInfo>>())
                .subscribe(new Consumer<List<CouponInfo>>() {
                    @Override
                    public void accept(List<CouponInfo> couponInfos) throws Exception {
                        couponView.dismissLoading();
                        couponView.renderCouponInfos(couponInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponView.dismissLoading();
                        couponView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void sendOutCoupon(String uid, String quanId) {
        couponView.showLoading();
        SendOutCouponRequest sendOutCouponRequest=new SendOutCouponRequest(uid,quanId);
        Disposable disposable = restApiService.sendOutCoupon(sendOutCouponRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        couponView.dismissLoading();
                        couponView.sendOutSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponView.dismissLoading();
                        couponView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
