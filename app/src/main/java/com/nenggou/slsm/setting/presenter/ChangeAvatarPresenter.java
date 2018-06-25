package com.nenggou.slsm.setting.presenter;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.nenggou.slsm.data.RxSchedulerTransformer;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.remote.RestApiService;
import com.nenggou.slsm.data.remote.RxRemoteDataParse;
import com.nenggou.slsm.data.request.AvatarRequest;
import com.nenggou.slsm.setting.SettingContract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by JWC on 2018/6/25.
 */

public class ChangeAvatarPresenter implements SettingContract.ChangeAvatarPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private SettingContract.ChangeAvatarView changeAvatarView;

    @Inject
    public ChangeAvatarPresenter(RestApiService restApiService, SettingContract.ChangeAvatarView changeAvatarView) {
        this.restApiService = restApiService;
        this.changeAvatarView = changeAvatarView;
    }

    @Inject
    public void setupListener() {
        changeAvatarView.setPresenter(this);
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
    public void uploadFile(String photoUrl) {
        AvatarRequest avatarRequest = new AvatarRequest(photoUrl);
        Gson gson = new Gson();
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        File file = new File(photoUrl);
        String fileName = file.getName();
        RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        RequestBody json = RequestBody.create(MediaType.parse("application/json"), gson.toJson(avatarRequest));
        requestBodyMap.put("json_data", json);
        Disposable disposable = restApiService.uploadFile(requestBodyMap)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String string) throws Exception {
                        changeAvatarView.uploadFileSuccess(string);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        changeAvatarView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void changeAvata(String avatar) {
        AvatarRequest avatarRequest = new AvatarRequest(avatar);
        Disposable disposable = restApiService.changeAvatar(avatarRequest)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        changeAvatarView.changeAvataSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        changeAvatarView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
}
