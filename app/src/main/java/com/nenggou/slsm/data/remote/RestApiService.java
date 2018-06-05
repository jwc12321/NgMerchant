package com.nenggou.slsm.data.remote;


import com.nenggou.slsm.data.RemoteDataWrapper;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.data.request.LoginRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //密码登录
    @POST("home/login/dologin")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> accountLogin(@Body LoginRequest loginRequest);

}
