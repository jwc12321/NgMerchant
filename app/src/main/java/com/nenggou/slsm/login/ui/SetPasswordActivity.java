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
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.AccountUtils;
import com.nenggou.slsm.login.DaggerLoginComponent;
import com.nenggou.slsm.login.LoginContract;
import com.nenggou.slsm.login.LoginModule;
import com.nenggou.slsm.login.presenter.SetPasswordPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/25.
 */

public class SetPasswordActivity extends BaseActivity implements LoginContract.SetPasswordVeiw{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.phone_rl)
    RelativeLayout phoneRl;
    @BindView(R.id.confirm_bt)
    Button confirmBt;
    @BindView(R.id.setting_item)
    LinearLayout settingItem;

    private String phoneNumber;
    private String phoneCode;
    private String password;
    @Inject
    SetPasswordPresenter setPasswordPresenter;

    public static void start(Context context, String phoneNumber, String phoneCode) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        intent.putExtra(StaticData.PHONE_CODE, phoneCode);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        phoneNumber=getIntent().getStringExtra(StaticData.PHONE_NUMBER);
        phoneCode=getIntent().getStringExtra(StaticData.PHONE_CODE);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(LoginContract.SetPasswordPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.password_et})
    public void checkLoginEnable() {
        password = passwordEt.getText().toString().trim();
        confirmBt.setEnabled(!(TextUtils.isEmpty(password)));
    }

    @Override
    public void setPasswroeSuccess() {
        showMessage("密码修改成功");
        finish();
    }

    @OnClick({R.id.back, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                setPasswordPresenter.setPassword(phoneNumber,phoneCode,password);
                break;
            default:
        }
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
}
