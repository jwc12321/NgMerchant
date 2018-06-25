package com.nenggou.slsm.setting.ui;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.setting.DaggerSettingComponent;
import com.nenggou.slsm.setting.SettingContract;
import com.nenggou.slsm.setting.SettingModule;
import com.nenggou.slsm.setting.presenter.ModifyPasswordPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/25.
 * 修改密码
 */

public class ModifyPasswordActivity extends BaseActivity implements SettingContract.ModifyPasswordView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.old_password_et)
    EditText oldPasswordEt;
    @BindView(R.id.new_password_et)
    EditText newPasswordEt;
    @BindView(R.id.confirm_password_et)
    EditText confirmPasswordEt;
    @BindView(R.id.confirm_bt)
    Button confirmBt;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String phoneNumber;

    public static void start(Context context,String phoneNumber) {
        Intent intent = new Intent(context, ModifyPasswordActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER,phoneNumber);
        context.startActivity(intent);
    }

    @Inject
    ModifyPasswordPresenter modifyPasswordPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView(){
        phoneNumber=getIntent().getStringExtra(StaticData.PHONE_NUMBER);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(SettingContract.ModifyPasswordPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerSettingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.old_password_et, R.id.new_password_et,R.id.confirm_password_et})
    public void checkLoginEnable() {
        oldPassword = oldPasswordEt.getText().toString();
        newPassword = newPasswordEt.getText().toString();
        confirmPassword=confirmPasswordEt.getText().toString();
        confirmBt.setEnabled(!(TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(oldPassword)|| TextUtils.isEmpty(confirmPassword)));
    }

    @Override
    public void modifyPasswordSuccess() {
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
                if (TextUtils.equals(newPassword, confirmPassword)) {
                    modifyPasswordPresenter.modifyPassword(newPassword,oldPassword,"",phoneNumber);
                } else {
                    showMessage("两次输入得密码不一样");
                }
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
