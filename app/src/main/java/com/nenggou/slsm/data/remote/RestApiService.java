package com.nenggou.slsm.data.remote;


import com.nenggou.slsm.data.RemoteDataWrapper;
import com.nenggou.slsm.data.entity.AddbankcardInfo;
import com.nenggou.slsm.data.entity.AllEvaluationInfo;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.entity.BankCardInfo;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.entity.CashDate;
import com.nenggou.slsm.data.entity.CashInfo;
import com.nenggou.slsm.data.entity.ChangeAppInfo;
import com.nenggou.slsm.data.entity.CouponInfo;
import com.nenggou.slsm.data.entity.EnergyInfo;
import com.nenggou.slsm.data.entity.FcOrderDetailInfo;
import com.nenggou.slsm.data.entity.FcOrderList;
import com.nenggou.slsm.data.entity.FcWalletInfo;
import com.nenggou.slsm.data.entity.FinancingInfo;
import com.nenggou.slsm.data.entity.HistoryIncomInfo;
import com.nenggou.slsm.data.entity.Ignore;
import com.nenggou.slsm.data.entity.IncomeDetailInfo;
import com.nenggou.slsm.data.entity.IntercourseRecordInfo;
import com.nenggou.slsm.data.entity.PayFcOrderInfo;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.data.entity.PutForwardDetailInfo;
import com.nenggou.slsm.data.entity.PutForwardInfo;
import com.nenggou.slsm.data.entity.RIncomeInfo;
import com.nenggou.slsm.data.entity.RankingListInfo;
import com.nenggou.slsm.data.entity.RdList;
import com.nenggou.slsm.data.entity.TurnOutRecord;
import com.nenggou.slsm.data.request.AddbankcardRequest;
import com.nenggou.slsm.data.request.AvatarRequest;
import com.nenggou.slsm.data.request.BillIdRequest;
import com.nenggou.slsm.data.request.CashDetailListRequest;
import com.nenggou.slsm.data.request.CheckCodeRequest;
import com.nenggou.slsm.data.request.CheckNewCodeRequest;
import com.nenggou.slsm.data.request.CodeLoginRequest;
import com.nenggou.slsm.data.request.DayIncomeRequest;
import com.nenggou.slsm.data.request.DetectionVersionRequest;
import com.nenggou.slsm.data.request.EnergyRequest;
import com.nenggou.slsm.data.request.FcOrderListRequest;
import com.nenggou.slsm.data.request.FeedbackRequest;
import com.nenggou.slsm.data.request.FinancingListRequest;
import com.nenggou.slsm.data.request.FinancingidRequest;
import com.nenggou.slsm.data.request.HistoryIncomeRequest;
import com.nenggou.slsm.data.request.IdRequest;
import com.nenggou.slsm.data.request.IntercourseRecordRequest;
import com.nenggou.slsm.data.request.ModifyPasswordRequest;
import com.nenggou.slsm.data.request.PageRequest;
import com.nenggou.slsm.data.request.PasswordLoginRequest;
import com.nenggou.slsm.data.request.PayFcOrderRequest;
import com.nenggou.slsm.data.request.PayPasswordRequest;
import com.nenggou.slsm.data.request.PutForwardRequest;
import com.nenggou.slsm.data.request.CRankingRequest;
import com.nenggou.slsm.data.request.RIncomeRequest;
import com.nenggou.slsm.data.request.RdIncomeRequest;
import com.nenggou.slsm.data.request.SendCodeRequest;
import com.nenggou.slsm.data.request.SendOutCouponRequest;
import com.nenggou.slsm.data.request.SetPasswordRequest;
import com.nenggou.slsm.data.request.SetPayPwRequest;
import com.nenggou.slsm.data.request.StartimeRequest;
import com.nenggou.slsm.data.request.StoreIdPageRequest;
import com.nenggou.slsm.data.request.TextRequest;
import com.nenggou.slsm.data.request.TokenRequest;
import com.nenggou.slsm.data.request.TurnOutBalanceRequest;

import java.util.List;
import java.util.Map;

import dagger.Provides;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

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

    //验证码登录
    @POST("home/business/apploginbycode")
    Flowable<RemoteDataWrapper<PersionInfoResponse>> codeLogin(@Body CodeLoginRequest codeLoginRequest);

    //店铺列表
    @POST("home/business/appstorelist")
    Flowable<RemoteDataWrapper<List<AppstoreInfo>>> getAppstoreInfos(@Body TokenRequest tokenRequest);

    //商家今日收入
    @POST("home/bill/appstoreday")
    Flowable<RemoteDataWrapper<BillInfo>> getBillInfo(@Body DayIncomeRequest dayIncomeRequest);

    //历史收益
    @POST("home/bill/appstorelog")
    Flowable<RemoteDataWrapper<HistoryIncomInfo>> getHistoryIncomInfo(@Body HistoryIncomeRequest historyIncomeRequest);

    //收入详情
    @POST("home/bill/billdetail")
    Flowable<RemoteDataWrapper<IncomeDetailInfo>> getIncomeDetail(@Body BillIdRequest billIdRequest);

    //能量明细
    @POST("home/bill/appbusinesspower")
    Flowable<RemoteDataWrapper<EnergyInfo>> getEnergyInfo(@Body EnergyRequest energyRequest);

    //现金余额
    @POST("home/bill/xianjin")
    Flowable<RemoteDataWrapper<CashInfo>> getCashInfo(@Body TokenRequest tokenRequest);

    //现金明细
    @POST("home/bill/appbusinessxianjin")
    Flowable<RemoteDataWrapper<CashDate>> getCashDetailList(@Body CashDetailListRequest cashDetailListRequest);

    //获取评价列表
    @POST("home/business/appgetevaluate")
    Flowable<RemoteDataWrapper<AllEvaluationInfo>> getAllEvaluation(@Body StoreIdPageRequest storeIdPageRequest);

    //推荐收益
    @POST("home/bill/appgainslog")
    Flowable<RemoteDataWrapper<HistoryIncomInfo>> getRdIncomeInfo(@Body PageRequest pageRequest);

    //推荐收益收入某一天
    @POST("home/bill/appgainsday")
    Flowable<RemoteDataWrapper<BillInfo>> getRdDayIncome(@Body RdIncomeRequest rdIncomeRequest);

    //来往记录
    @POST("home/bill/appsubill")
    Flowable<RemoteDataWrapper<IntercourseRecordInfo>> getIntercourseRecordInfo(@Body IntercourseRecordRequest intercourseRecordRequest);

    //银行卡列表
    @POST("home/business/banklist")
    Flowable<RemoteDataWrapper<List<BankCardInfo>>> getBankCardInfos(@Body TokenRequest tokenRequest);

    //添加银行卡
    @POST("home/business/addbankcard")
    Flowable<RemoteDataWrapper<AddbankcardInfo>> addbankcard(@Body AddbankcardRequest addbankcardRequest);

    //意见反馈
    @POST("home/business/appfeedback")
    Flowable<RemoteDataWrapper<String>> subFeedBack(@Body FeedbackRequest feedbackRequest);

    //提现
    @POST("home/business/atmpaypassword")
    Flowable<RemoteDataWrapper<Ignore>> putForward(@Body PutForwardRequest putForwardRequest);

    /**
     * 修改头像
     */
    @POST("home/business/avatar")
    Flowable<RemoteDataWrapper<Ignore>> changeAvatar(@Body AvatarRequest avatarRequest);

    //上传图片(阿里云图片上传)
    @Multipart
    @POST("home/index/uploadFile")
    Flowable<RemoteDataWrapper<String>> uploadFile(@PartMap Map<String, RequestBody> multipartParams);

    //修改密码
    @POST("home/business/appchangepwd")
    Flowable<RemoteDataWrapper<Ignore>> modifyPw(@Body ModifyPasswordRequest modifyPasswordRequest);

    //验证验证码
    @POST("home/login/checkCode")
    Flowable<RemoteDataWrapper<Ignore>> checkCode(@Body CheckCodeRequest checkCodeRequest);

    //修改手机号
    @POST("home/business/appchangePhone")
    Flowable<RemoteDataWrapper<Ignore>> checkNewCode(@Body CheckNewCodeRequest checkNewCodeRequest);

    //忘记密码
    @POST("home/business/forgetPwd")
    Flowable<RemoteDataWrapper<Ignore>> setPassword(@Body SetPasswordRequest setPasswordRequest);

    //扫描
    @POST("home/scancode")
    Flowable<RemoteDataWrapper<String>> uploadQrText(@Body TextRequest textRequest);

    //版本检测
    @POST("home/businessChangeApp")
    Flowable<RemoteDataWrapper<ChangeAppInfo>> changeApp(@Body DetectionVersionRequest detectionVersionRequest);

    //提现类表
    @POST("home/business/atmlog")
    Flowable<RemoteDataWrapper<List<PutForwardInfo>>> getPutForwardInfos(@Body StartimeRequest startimeRequest);

    //提现详情
    @POST("home/business/atmdetail")
    Flowable<RemoteDataWrapper<PutForwardDetailInfo>> getPutForwardDetailInfo(@Body IdRequest idRequest);

    //获取消费排行榜
    @POST("home/bill/ranking")
    Flowable<RemoteDataWrapper<RankingListInfo>> getCRankingListInfo(@Body CRankingRequest cRankingRequest);

    //推荐收益排行
    @POST("home/bill/gainsranking")
    Flowable<RemoteDataWrapper<RankingListInfo>> getRRankingListInfo(@Body CRankingRequest cRankingRequest);

    //推荐收益
    @POST("home/bill/usergains")
    Flowable<RemoteDataWrapper<RIncomeInfo>> getRIncomeInfo(@Body RIncomeRequest rIncomeRequest);

    //获取优惠券
    @POST("home/quan/businessCoupon")
    Flowable<RemoteDataWrapper<List<CouponInfo>>> getCouoponInfos(@Body TokenRequest tokenRequest);

    //发送优惠券
    @POST("home/quan/pushquan")
    Flowable<RemoteDataWrapper<Ignore>> sendOutCoupon(@Body SendOutCouponRequest sendOutCouponRequest);

    //理财列表
    @POST("home/financing/list")
    Flowable<RemoteDataWrapper<FinancingInfo>> getFinancingInfos(@Body FinancingListRequest financingListRequest);

    //老理财列表
    @POST("home/financing/list")
    Flowable<RemoteDataWrapper<FinancingInfo>> getOldFinancingInfos(@Body PageRequest pageRequest);

    //推荐人列表
    @POST("home/business/apprecommend")
    Flowable<RemoteDataWrapper<RdList>> getRdList(@Body PageRequest pageRequest);

    //设置提现密码
    @POST("home/business/setpaypassword")
    Flowable<RemoteDataWrapper<Ignore>> setPayPassword(@Body SetPayPwRequest setPayPwRequest);

    //获取提现密码是否设置
    @POST("home/business/getpaypassword")
    Flowable<RemoteDataWrapper<String>> isSetUpPayPw(@Body TokenRequest tokenRequest);

    //验证原提现密码
    @POST("home/business/verifypaypassword")
    Flowable<RemoteDataWrapper<Ignore>> verifyPayPassword(@Body PayPasswordRequest payPasswordRequest);
    //理财点击立即加入，到支付订单时候的数据
    @POST("home/financing/getwallet")
    Flowable<RemoteDataWrapper<PayFcOrderInfo>> getPayFcOrderInfo(@Body FinancingidRequest financingidRequest);
    //购买理财
    @POST("home/financing/payfinancing")
    Flowable<RemoteDataWrapper<Ignore>> payFcOrder(@Body PayFcOrderRequest payFcOrderRequest);
    //理财详情
    @POST("home/financing/orderdetail")
    Flowable<RemoteDataWrapper<FcOrderDetailInfo>> getFcOrderDetailInfo(@Body FinancingidRequest financingidRequest);
    //理财订单列表
    @POST("home/financing/getlist")
    Flowable<RemoteDataWrapper<FcOrderList>> getFcOrderList(@Body FcOrderListRequest fcOrderListRequest);
    //理财钱包
    @POST("home/financing/mywallet")
    Flowable<RemoteDataWrapper<FcWalletInfo>> getFcWalletInfo(@Body TokenRequest tokenRequest);
    //转到余额
    @POST("home/financing/walletturnto")
    Flowable<RemoteDataWrapper<Ignore>> turnToBalance(@Body TurnOutBalanceRequest turnOutBalanceRequest);
    //转出记录
    @POST("home/financing/walletturntolog")
    Flowable<RemoteDataWrapper<TurnOutRecord>> getTurnOutRecordInfos(@Body PageRequest pageRequest);

}
