package com.nenggou.slsm.setting.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.gson.Gson;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.AccountUtils;
import com.nenggou.slsm.common.unit.PersionAppPreferences;
import com.nenggou.slsm.common.widget.ColdDownButton;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.setting.DaggerSettingComponent;
import com.nenggou.slsm.setting.SettingContract;
import com.nenggou.slsm.setting.SettingModule;
import com.nenggou.slsm.setting.presenter.ShiftHandsetPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

import static com.nenggou.slsm.common.unit.AccountUtils.isAccountValid;


/**
 * Created by JWC on 2018/5/4.
 */

public class ShiftHandsetActivity extends BaseActivity implements SettingContract.ShiftHandsetView, ColdDownButton.OnResetListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.photo_number_et)
    EditText photoNumberEt;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.send_auth_code)
    ColdDownButton sendAuthCode;
    @BindView(R.id.phone_code_et)
    EditText phoneCodeEt;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.confirm_bt)
    Button confirmBt;

    private String typeWhat;
    private String phoneNumberStr;
    private String phoneCodeStr;

    @Inject
    ShiftHandsetPresenter shiftHandsetPresenter;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, ShiftHandsetActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_handset);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        phoneNumberStr = getIntent().getStringExtra(StaticData.PHONE_NUMBER);;
        typeWhat = "1";
        photoNumberEt.setText(phoneNumberStr);
        sendAuthCode.startCold();
        shiftHandsetPresenter.sendVcode(phoneNumberStr, "changetel");
        sendAuthCode.setOnResetListener(this);
        phoneCodeEt.setFocusable(true);
        phoneCodeEt.setFocusableInTouchMode(true);
        phoneCodeEt.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(phoneCodeEt, InputMethodManager.SHOW_FORCED);
            }
        }, 1000);
    }

    private void upKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(photoNumberEt, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void initializeInjector() {
        DaggerSettingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .settingModule(new SettingModule(this))
                .build().inject(this);
    }

    @Override
    public void setPresenter(SettingContract.ShiftHandsetPresenter presenter) {

    }

    @Override
    public void vcodeSuccess() {
        showMessage("验证码发送成功");
    }

    @Override
    public void checkOldCodeSuccess() {
        showMessage("验证成功");
        sendAuthCode.reset();
        typeWhat = "2";
        photoNumberEt.setText("");
        phoneCodeEt.setText("");
        photoNumberEt.setHint("请输入新手机号");
        phoneCodeEt.setHint("请输入手机验证码");
        photoNumberEt.setFocusable(true);
        photoNumberEt.setFocusableInTouchMode(true);
        photoNumberEt.requestFocus();
        upKeyboard();
        confirmBt.setText("确认");
    }

    @Override
    public void checkNewCodeSuccess() {
        showMessage("换绑成功");
        LoginActivity.start(this);
        finish();
    }

    @Override
    public void onReset() {
        photoNumberEt.setFocusable(true);
        photoNumberEt.setFocusableInTouchMode(true);
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.send_auth_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                okButton();
                break;
            case R.id.send_auth_code:
                if (!TextUtils.isEmpty(photoNumberEt.getText().toString())) {
                    sendCode();
                }
                break;
            default:
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        phoneNumberStr = photoNumberEt.getText().toString();
        if (TextUtils.isEmpty(phoneNumberStr)) {
            showMessage("请输入手机号");
            return;
        }
        if (!isAccountValid(phoneNumberStr)) {
            showError("请正确输入手机号");
            return;
        }
        sendAuthCode.startCold();
        shiftHandsetPresenter.sendVcode(phoneNumberStr, "changetel");
        sendAuthCode.setOnResetListener(this);
        photoNumberEt.setFocusable(false);
    }

    private void okButton() {
        if (TextUtils.isEmpty(phoneCodeEt.getText().toString())) {
            showMessage("请输入验证码");
            return;
        } else {
            if (TextUtils.equals("1", typeWhat)) {
                shiftHandsetPresenter.checkOldCode(phoneNumberStr, phoneCodeEt.getText().toString(), "changetel");
            } else {
                shiftHandsetPresenter.checkNewCode(phoneNumberStr, phoneCodeEt.getText().toString());
            }
        }
    }


    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.photo_number_et, R.id.phone_code_et})
    public void checkLoginEnable() {
        phoneNumberStr = photoNumberEt.getText().toString().trim();
        phoneCodeStr = phoneCodeEt.getText().toString().trim();
        sendAuthCode.setEnabled(!TextUtils.isEmpty(phoneNumberStr) && AccountUtils.isAccountValid(phoneNumberStr));
        photoNumberEt.setFocusable(!sendAuthCode.isCounting());
        confirmBt.setEnabled(!(TextUtils.isEmpty(phoneNumberStr) || TextUtils.isEmpty(phoneCodeStr)));
    }

    @OnFocusChange({R.id.photo_number_et, R.id.phone_code_et})
    public void changeFocus(View view) {
        photoNumberEt.setFocusable(!sendAuthCode.isCounting());
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

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(photoNumberEt.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(phoneCodeEt.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }
}

