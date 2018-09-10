package com.nenggou.slsm.paypassword.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.PersionAppPreferences;
import com.nenggou.slsm.common.widget.paypw.PayPwdEditText;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.paypassword.DaggerPayPasswordComponent;
import com.nenggou.slsm.paypassword.PayPasswordContract;
import com.nenggou.slsm.paypassword.PayPasswordModule;
import com.nenggou.slsm.paypassword.presenter.PayPwPowerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/21.
 */

public class InputPayPwActivity extends BaseActivity implements PayPasswordContract.PayPwPowerView {
    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.item_input_pw)
    RelativeLayout itemInputPw;
    @BindView(R.id.forget_password)
    TextView forgetPassword;

    @Inject
    PayPwPowerPresenter payPwPowerPresenter;

    private String password;
    private PersionAppPreferences persionAppPreferences;
    private Gson gson= new Gson();
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private String phoneNumber;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec_input_paypw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(this);
        persionInfoStr = persionAppPreferences.getPersionInfo();
        if (!TextUtils.isEmpty(persionInfoStr)) {
            persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
            phoneNumber=persionInfoResponse.getTel();
        }
        initEt();
    }

    private void initEt() {
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.appText5, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password = str;
                payPwPowerPresenter.verifyPayPassword(str);
            }
        });

    }

    @OnClick({R.id.cancel, R.id.item_input_pw,R.id.forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
            case R.id.item_input_pw:
                finish();
                break;
            case R.id.forget_password:
                SmsAuthenticationActivity.start(this,phoneNumber);
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerPayPasswordComponent.builder()
                .applicationComponent(getApplicationComponent())
                .payPasswordModule(new PayPasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(PayPasswordContract.PayPwPowerPresenter presenter) {

    }

    @Override
    public void verifySuccess() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(StaticData.PAY_PASSWORD, password);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
