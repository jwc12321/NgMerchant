package com.nenggou.slsm.setting.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.PersionAppPreferences;
import com.nenggou.slsm.common.unit.TokenManager;
import com.nenggou.slsm.data.entity.WebViewDetailInfo;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.paypassword.ui.FirstPayPwActivity;
import com.nenggou.slsm.paypassword.ui.RememberPswActivity;
import com.nenggou.slsm.setting.DaggerSettingComponent;
import com.nenggou.slsm.setting.SettingContract;
import com.nenggou.slsm.setting.SettingModule;
import com.nenggou.slsm.setting.presenter.SettingPresenter;
import com.nenggou.slsm.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by JWC on 2018/6/25.
 */

public class SettingActivity extends BaseActivity implements SettingContract.SettingView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.item_shift_handset)
    RelativeLayout itemShiftHandset;
    @BindView(R.id.item_modify_password)
    RelativeLayout itemModifyPassword;
    @BindView(R.id.item_contact_us)
    RelativeLayout itemContactUs;
    @BindView(R.id.item_new_version_detection)
    RelativeLayout itemNewVersionDetection;
    @BindView(R.id.login_out)
    Button loginOut;
    @BindView(R.id.setting_item)
    LinearLayout settingItem;
    @BindView(R.id.item_business_im)
    RelativeLayout itemBusinessIm;
    @BindView(R.id.item_pay_password)
    RelativeLayout itemPayPassword;

    private String phoneNumber;
    private PersionAppPreferences persionAppPreferences;

    private WebViewDetailInfo webViewDetailInfo;

    @Inject
    SettingPresenter settingPresenter;

    public static void start(Context context, String phoneNumber) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER, phoneNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        persionAppPreferences = new PersionAppPreferences(this);
        phoneNumber = getIntent().getStringExtra(StaticData.PHONE_NUMBER);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.item_business_im, R.id.item_shift_handset, R.id.item_modify_password, R.id.item_contact_us, R.id.item_new_version_detection, R.id.login_out,R.id.item_pay_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item_business_im://商户信息
                BusinessImActivity.start(this);
                break;
            case R.id.item_shift_handset://换绑手机
                ShiftHandsetActivity.start(this, phoneNumber);
                break;
            case R.id.item_modify_password://修改密码
                ModifyPasswordActivity.start(this, phoneNumber);
                break;
            case R.id.item_contact_us://联系我们
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setTitle("联系我们");
                webViewDetailInfo.setUrl("https://open.365neng.com/api/home/index/services");
                WebViewActivity.start(this, webViewDetailInfo);
                break;
            case R.id.item_new_version_detection://版本检测
                break;
            case R.id.login_out://登出
                persionAppPreferences.clean();
                TokenManager.saveToken("");
                JPushInterface.setAliasAndTags(getApplicationContext(),
                        "",
                        null,
                        null);
                LoginActivity.start(this);
                finish();
                break;
            case R.id.item_pay_password:
                settingPresenter.isSetUpPayPw();
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerSettingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(SettingContract.SettingPresenter presenter) {

    }

    @Override
    public void renderIsSetUpPayPw(String what) {
        if(TextUtils.equals("true",what)){
            RememberPswActivity.start(this,phoneNumber);
        }else {
            FirstPayPwActivity.start(this,"0","");
        }
    }
}
