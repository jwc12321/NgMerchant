package com.nenggou.slsm.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.AccountUtils;
import com.nenggou.slsm.common.unit.PersionAppPreferences;
import com.nenggou.slsm.common.unit.TokenManager;
import com.nenggou.slsm.common.widget.ColdDownButton;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.login.DaggerLoginComponent;
import com.nenggou.slsm.login.LoginContract;
import com.nenggou.slsm.login.LoginModule;
import com.nenggou.slsm.login.presenter.LoginPresenter;
import com.nenggou.slsm.mainframe.ui.MainFrameActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/5.
 */

public class LoginActivity extends BaseActivity implements LoginContract.LoginView, ColdDownButton.OnResetListener {

    @BindView(R.id.login_password)
    TextView loginPassword;
    @BindView(R.id.login_vcode)
    TextView loginVcode;
    @BindView(R.id.password_t_iv)
    ImageView passwordTIv;
    @BindView(R.id.vcode_t_iv)
    ImageView vcodeTIv;
    @BindView(R.id.phone_number_et)
    EditText phoneNumberEt;
    @BindView(R.id.phone_rl)
    RelativeLayout phoneRl;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.password_rl)
    RelativeLayout passwordRl;
    @BindView(R.id.vcode_et)
    EditText vcodeEt;
    @BindView(R.id.send_vcode)
    ColdDownButton sendVcode;
    @BindView(R.id.vcode_rl)
    RelativeLayout vcodeRl;
    @BindView(R.id.login_in)
    Button loginIn;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.register)
    TextView register;


    @Inject
    LoginPresenter loginPresenter;

    private PersionAppPreferences persionAppPreferences;

    private String userPhoneNumber;
    private String userPassword;
    private String userVcode;

    private String loginType = "1";   //1：密码登录 2：验证码登录

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(this);
        sendVcode.setOnResetListener(this);
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }


    @Override
    public void loginSuccess(PersionInfoResponse persionInfoResponse) {
        TokenManager.saveToken(persionInfoResponse.getToken());
        Gson gson = new Gson();
        String persionInfoResponseStr = gson.toJson(persionInfoResponse);
        persionAppPreferences.setPersionInfo(persionInfoResponseStr);
        MainFrameActivity.start(this);
    }

    @Override
    public void codeSuccess() {
        showMessage(getString(R.string.login_auth_code_sent));
    }


    @OnClick({R.id.login_password, R.id.login_vcode, R.id.login_in, R.id.send_vcode,R.id.forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_password:
                loginType = "1";
                passwordAt();
                passwordRl.setVisibility(View.VISIBLE);
                vcodeRl.setVisibility(View.GONE);
                break;
            case R.id.login_vcode:
                loginType = "2";
                vCodeAt();
                passwordRl.setVisibility(View.GONE);
                vcodeRl.setVisibility(View.VISIBLE);
                break;
            case R.id.login_in:
                if (TextUtils.equals("1", loginType)) {
                    loginPresenter.passwordLogin(userPhoneNumber, userPassword);
                } else {
                    loginPresenter.codeLogin(userPhoneNumber,userVcode);
                }
                break;
            case R.id.send_vcode:
                sendCode();
                break;
            case R.id.forget_password:
                ForgetPasswordActivity.start(this);
                break;
            default:
        }
    }

    private void sendCode() {
        if (TextUtils.isEmpty(userPhoneNumber)) {
            showMessage(getString(R.string.empty_phone));
            return;
        } else if (!AccountUtils.isAccountValid(userPhoneNumber)) {
            showError(getString(R.string.invalid_phone_input));
            return;
        }
        loginPresenter.sendCode(userPhoneNumber, "login");
        sendVcode.startCold();
        phoneNumberEt.setFocusable(false);
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.phone_number_et, R.id.password_et, R.id.vcode_et})
    public void checkLoginEnable() {
        userPhoneNumber = phoneNumberEt.getText().toString().trim();
        userPassword = passwordEt.getText().toString().trim();
        userVcode = vcodeEt.getText().toString().trim();
        sendVcode.setEnabled(!TextUtils.isEmpty(userPhoneNumber) && AccountUtils.isAccountValid(userPhoneNumber));
        if (TextUtils.equals("1", loginType)) {
            loginIn.setEnabled(!(TextUtils.isEmpty(userPhoneNumber) || TextUtils.isEmpty(userPassword)));
        } else {
            loginIn.setEnabled(!(TextUtils.isEmpty(userPhoneNumber) || TextUtils.isEmpty(userVcode)));
        }
    }

    //动画  点击密码登录
    private void passwordAt() {
        passwordTIv.clearAnimation();
        passwordTIv.invalidate();
        vcodeTIv.clearAnimation();
        vcodeTIv.invalidate();
        vcodeTIv.setVisibility(View.VISIBLE);
        passwordTIv.setVisibility(View.GONE);
        final TranslateAnimation passwordAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(LoginActivity.this, R.anim.back_triangle_translate);
        passwordAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loginPassword.setEnabled(false);
                loginVcode.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        vcodeTIv.startAnimation(passwordAnimation);
    }

    //动画 点击验证码登录
    private void vCodeAt() {
        passwordTIv.clearAnimation();
        passwordTIv.invalidate();
        vcodeTIv.clearAnimation();
        vcodeTIv.invalidate();
        vcodeTIv.setVisibility(View.GONE);
        passwordTIv.setVisibility(View.VISIBLE);
        final TranslateAnimation vcodeAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(LoginActivity.this, R.anim.triangle_translate);
        vcodeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loginPassword.setEnabled(true);
                loginVcode.setEnabled(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        passwordTIv.startAnimation(vcodeAnimation);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public void onReset() {

    }
}
