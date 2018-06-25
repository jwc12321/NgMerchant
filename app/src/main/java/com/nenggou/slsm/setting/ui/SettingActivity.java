package com.nenggou.slsm.setting.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.data.entity.WebViewDetailInfo;
import com.nenggou.slsm.webview.ui.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/25.
 */

public class SettingActivity extends BaseActivity {
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

    private String phoneNumber;

    private WebViewDetailInfo webViewDetailInfo;
    public static void start(Context context,String phoneNumber) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra(StaticData.PHONE_NUMBER,phoneNumber);
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
        phoneNumber=getIntent().getStringExtra(StaticData.PHONE_NUMBER);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.item_business_im, R.id.item_shift_handset, R.id.item_modify_password, R.id.item_contact_us, R.id.item_new_version_detection, R.id.login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item_business_im://商户信息
                BusinessImActivity.start(this);
                break;
            case R.id.item_shift_handset://换绑手机
                ShiftHandsetActivity.start(this,phoneNumber);
                break;
            case R.id.item_modify_password://修改密码
                ModifyPasswordActivity.start(this,phoneNumber);
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
                break;
            default:
        }
    }
}
