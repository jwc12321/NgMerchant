package com.nenggou.slsm.data.remote;


import com.nenggou.slsm.data.RemoteDataWrapper;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.data.request.DayIncomeRequest;
import com.nenggou.slsm.data.request.PasswordLoginRequest;
import com.nenggou.slsm.data.request.SendCodeRequest;
import com.nenggou.slsm.data.request.TokenRequest;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //密码登录
    @POST("home/business/apploginbypassword")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> passwordLogin(@Body PasswordLoginRequest passwordLoginRequest);

    //发送验证码
    @POST("home/login/sendcode")
    Flowable<RemoteDataWrapper<Ignore>> sendCode(@Body SendCodeRequest sendCodeRequest);

    //店铺列表
    @POST("home/business/appstorelist")
    Flowable<RemoteDataWrapper<List<AppstoreInfo>>> getAppstoreInfos(@Body TokenRequest tokenRequest);

    //商家今日收入
    @POST("home/bill/appstoreday")
    Flowable<RemoteDataWrapper<BillInfo>> getBillInfo(@Body DayIncomeRequest dayIncomeRequest);
}
