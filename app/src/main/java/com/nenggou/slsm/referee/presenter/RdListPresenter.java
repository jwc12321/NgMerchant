package com.nenggou.slsm.referee.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.RdList;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PageRequest;
import com.nenggou.slsm.referee.RefereeContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/8/14.
 */

public class RdListPresenter implements RefereeContract.RdListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private RefereeContract.RdListView rdListView;
    private int currentIndex = 1;
    @Inject
    public RdListPresenter(RestApiService restApiService, RefereeContract.RdListView rdListView) {
        this.restApiService = restApiService;
        this.rdListView = rdListView;
    }

    @Inject
    public void setupListener() {
        rdListView.setPresenter(this);
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
    public void getRdList(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            rdListView.showLoading();
        }
        currentIndex=1;
        PageRequest pageRequest=new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getRdList(pageRequest)
                .flatMap(new RxRemoteDataParse<RdList>())
                .compose(new RxSchedulerTransformer<RdList>())
                .subscribe(new Consumer<RdList>() {
                    @Override
                    public void accept(RdList rdList) throws Exception {
                        rdListView.dismissLoading();
                        rdListView.renderRdList(rdList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rdListView.dismissLoading();
                        rdListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getRdMoreList() {
        currentIndex=currentIndex+1;
        PageRequest pageRequest=new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getRdList(pageRequest)
                .flatMap(new RxRemoteDataParse<RdList>())
                .compose(new RxSchedulerTransformer<RdList>())
                .subscribe(new Consumer<RdList>() {
                    @Override
                    public void accept(RdList rdList) throws Exception {
                        rdListView.renderMoreRdList(rdList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rdListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
