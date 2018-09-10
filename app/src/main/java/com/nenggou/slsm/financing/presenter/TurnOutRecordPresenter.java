package com.nenggou.slsm.financing.presenter;

import android.text.TextUtils;

import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.FcOrderList;
import com.nenggou.slsm.data.entity.TurnOutRecord;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.PageRequest;
import com.nenggou.slsm.financing.FinancingContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/9/10.
 */

public class TurnOutRecordPresenter implements FinancingContract.TurnOutRecordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private FinancingContract.TurnOutRecordView turnOutRecordView;
    private int currentIndex = 1;

    @Inject
    public TurnOutRecordPresenter(RestApiService restApiService, FinancingContract.TurnOutRecordView turnOutRecordView) {
        this.restApiService = restApiService;
        this.turnOutRecordView = turnOutRecordView;
    }

    @Inject
    public void setupListener() {
        turnOutRecordView.setPresenter(this);
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
    public void getTurnOutRecord(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            turnOutRecordView.showLoading();
        }
        currentIndex=1;
        PageRequest pageRequest=new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getTurnOutRecordInfos(pageRequest)
                .flatMap(new RxRemoteDataParse<TurnOutRecord>())
                .compose(new RxSchedulerTransformer<TurnOutRecord>())
                .subscribe(new Consumer<TurnOutRecord>() {
                    @Override
                    public void accept(TurnOutRecord turnOutRecord) throws Exception {
                        turnOutRecordView.dismissLoading();
                        turnOutRecordView.renderRecords(turnOutRecord);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        turnOutRecordView.dismissLoading();
                        turnOutRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);

    }

    @Override
    public void getMoreTurnOutRecord() {
        currentIndex=currentIndex+1;
        PageRequest pageRequest=new PageRequest(String.valueOf(currentIndex));
        Disposable disposable = restApiService.getTurnOutRecordInfos(pageRequest)
                .flatMap(new RxRemoteDataParse<TurnOutRecord>())
                .compose(new RxSchedulerTransformer<TurnOutRecord>())
                .subscribe(new Consumer<TurnOutRecord>() {
                    @Override
                    public void accept(TurnOutRecord turnOutRecord) throws Exception {
                        turnOutRecordView.dismissLoading();
                        turnOutRecordView.renderMoreRecords(turnOutRecord);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        turnOutRecordView.dismissLoading();
                        turnOutRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
