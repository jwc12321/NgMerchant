package com.nenggou.slsm.bankcard.presenter;

import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.AddbankcardInfo;
import com.nenggou.slsm.data.entity.BankCardInfo;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.AddbankcardRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JWC on 2018/6/23.
 */

public class AddbankcardPresenter implements BankCardContract.AddbankcardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankCardContract.AddbankcardView addbankcardView;

    @Inject
    public AddbankcardPresenter(RestApiService restApiService, BankCardContract.AddbankcardView addbankcardView) {
        this.restApiService = restApiService;
        this.addbankcardView = addbankcardView;
    }

    @Inject
    public void setupListener() {
        addbankcardView.setPresenter(this);
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
    public void addBankCard(String cardname, String cardbank, String carddbank, String cardno, String telbank) {
        addbankcardView.showLoading();
        AddbankcardRequest addbankcardRequest=new AddbankcardRequest(cardname,cardbank,carddbank,cardno,telbank);
        Disposable disposable = restApiService.addbankcard(addbankcardRequest)
                .flatMap(new RxRemoteDataParse<AddbankcardInfo>())
                .compose(new RxSchedulerTransformer<AddbankcardInfo>())
                .subscribe(new Consumer<AddbankcardInfo>() {
                    @Override
                    public void accept(AddbankcardInfo addbankcardInfo) throws Exception {
                        addbankcardView.dismissLoading();
                        addbankcardView.addSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addbankcardView.dismissLoading();
                        addbankcardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
