package com.nenggou.slsm.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.AccountUtils;
import com.nenggou.slsm.common.widget.ColdDownButton;
import com.nenggou.slsm.login.DaggerLoginComponent;
import com.nenggou.slsm.login.LoginContract;
import com.nenggou.slsm.login.LoginModule;
import com.nenggou.slsm.login.presenter.ForgetPasswordPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import static com.nenggou.slsm.common.unit.AccountUtils.isAccountValid;

/**
 * Created by JWC on 2018/6/25.
 */

public class ForgetPasswordActivity extends BaseActivity implements LoginContract.ForgetPasswrodView, ColdDownButton.OnResetListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.phone_number_et)
    EditText phoneNumberEt;
    @BindView(R.id.phone_rl)
    RelativeLayout phoneRl;
    @BindView(R.id.vcode_et)
    EditText vcodeEt;
    @BindView(R.id.send_vcode)
    ColdDownButton sendVcode;
    @BindView(R.id.vcode_rl)
    RelativeLayout vcodeRl;
    @BindView(R.id.next_bt)
    Button nextBt;
    @BindView(R.id.setting_item)
    LinearLayout settingItem;

    private String userPhoneNumber;
    private String userVcode;
    @Inject
    ForgetPasswordPresenter forgetPasswordPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }

    private void initView() {

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.phone_number_et, R.id.vcode_et})
    public void checkLoginEnable() {
        userPhoneNumber = phoneNumberEt.getText().toString().trim();
        userVcode = vcodeEt.getText().toString().trim();
        sendVcode.setEnabled(!TextUtils.isEmpty(userPhoneNumber) && AccountUtils.isAccountValid(userPhoneNumber));
        nextBt.setEnabled(!(TextUtils.isEmpty(userPhoneNumber) || TextUtils.isEmpty(userVcode)));
    }

    @OnClick({R.id.back, R.id.send_vcode, R.id.next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.send_vcode:
                sendCode();
                break;
            case R.id.next_bt:
                if(TextUtils.isEmpty(userVcode)){
                    showMessage("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(userPhoneNumber)){
                    showMessage("请输入手机号");
                    return;
                }
                forgetPasswordPresenter.checkVcode(userPhoneNumber, userVcode, "changepwd");
                break;
            default:
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        userPhoneNumber = phoneNumberEt.getText().toString();
        if (TextUtils.isEmpty(userPhoneNumber)) {
            showMessage("请输入手机号");
            return;
        }
        if (!isAccountValid(userPhoneNumber)) {
            showError("请正确输入手机号");
            return;
        }
        sendVcode.startCold();
        forgetPasswordPresenter.sendVcode(userPhoneNumber, "changepwd");
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
    public void setPresenter(LoginContract.ForgetPasswordPresenter presenter) {

    }

    @Override
    public void vcodeSuccess() {
        showMessage("验证码发送成功");
    }

    @Override
    public void checkVcodeSuccess() {
        SetPasswordActivity.start(this,userPhoneNumber,userVcode);
        finish();
    }

    @Override
    public void onReset() {

    }
}
