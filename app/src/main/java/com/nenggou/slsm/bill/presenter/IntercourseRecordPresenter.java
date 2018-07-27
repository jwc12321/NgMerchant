package com.nenggou.slsm.bill.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.IntercourseRecordInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.IntercourseRecordRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/5/2.
 */

public class IntercourseRecordPresenter implements BillContract.IntercourseRecordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BillContract.IntercourseRecordView intercourseRecordView;
    private int currentIndex = 1;  //待接单当前index

    @Inject
    public IntercourseRecordPresenter(RestApiService restApiService, BillContract.IntercourseRecordView intercourseRecordView) {
        this.restApiService = restApiService;
        this.intercourseRecordView = intercourseRecordView;
    }

    @Inject
    public void setupListener() {
        intercourseRecordView.setPresenter(this);
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
    public void getIntercourseRecordInfo(String refreshType,String uid,String type, String starttime) {
        if(TextUtils.equals("1",refreshType)){
            intercourseRecordView.showLoading();
        }
        currentIndex = 1;
        IntercourseRecordRequest intercourseRecordRequest = new IntercourseRecordRequest(String.valueOf(currentIndex),uid,type,starttime);
        Disposable disposable = restApiService.getIntercourseRecordInfo(intercourseRecordRequest)
                .flatMap(new RxRemoteDataParse<IntercourseRecordInfo>())
                .compose(new RxSchedulerTransformer<IntercourseRecordInfo>())
                .subscribe(new Consumer<IntercourseRecordInfo>() {
                    @Override
                    public void accept(IntercourseRecordInfo intercourseRecordInfo) throws Exception {
                        intercourseRecordView.dismissLoading();
                        intercourseRecordView.intercourseRecordInfo(intercourseRecordInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        intercourseRecordView.dismissLoading();
                        intercourseRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreIntercourseRecordInfo(String uid,String type, String starttime) {
        currentIndex = currentIndex + 1;
        IntercourseRecordRequest intercourseRecordRequest = new IntercourseRecordRequest(String.valueOf(currentIndex),uid,type,starttime);
        Disposable disposable = restApiService.getIntercourseRecordInfo(intercourseRecordRequest)
                .flatMap(new RxRemoteDataParse<IntercourseRecordInfo>())
                .compose(new RxSchedulerTransformer<IntercourseRecordInfo>())
                .subscribe(new Consumer<IntercourseRecordInfo>() {
                    @Override
                    public void accept(IntercourseRecordInfo intercourseRecordInfo) throws Exception {
                        intercourseRecordView.moreIntercourseRecordInfo(intercourseRecordInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        intercourseRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
