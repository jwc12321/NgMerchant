package com.nenggou.slsm.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.ColdDownButton;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.login.DaggerLoginComponent;
import com.nenggou.slsm.login.LoginContract;
import com.nenggou.slsm.login.LoginModule;
import com.nenggou.slsm.login.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/5.
 */

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    @Inject
    LoginPresenter loginPresenter;
    @BindView(R.id.login_password)
    TextView loginPassword;
    @BindView(R.id.login_vcode)
    TextView loginVcode;
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.phone_rl)
    RelativeLayout phoneRl;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password_rl)
    RelativeLayout passwordRl;
    @BindView(R.id.vcode)
    EditText vcode;
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
    @BindView(R.id.password_t_iv)
    ImageView passwordTIv;
    @BindView(R.id.vcode_t_iv)
    ImageView vcodeTIv;


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
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
    public void accountLoginSuccess(PersionInfoResponse persionInfoResponse) {

    }

    @Override
    public void codeSuccess() {

    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void setPasswordSuccess() {

    }

    @OnClick({R.id.login_password, R.id.login_vcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_password:
                final TranslateAnimation passwordAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(LoginActivity.this, R.anim.back_triangle_translate);
                passwordAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        vcodeTIv.setVisibility(View.GONE);
                        passwordTIv.setVisibility(View.VISIBLE);
                        loginPassword.setEnabled(false);
                        loginVcode.setEnabled(true);
                        passwordAnimation.cancel();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                vcodeTIv.startAnimation(passwordAnimation);
                break;
            case R.id.login_vcode:
                final TranslateAnimation vcodeAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(LoginActivity.this, R.anim.triangle_translate);
                vcodeAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        vcodeTIv.setVisibility(View.VISIBLE);
                        passwordTIv.setVisibility(View.GONE);
                        loginPassword.setEnabled(true);
                        loginVcode.setEnabled(false);
                        vcodeAnimation.cancel();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                passwordTIv.startAnimation(vcodeAnimation);
                break;
        }
    }
}
