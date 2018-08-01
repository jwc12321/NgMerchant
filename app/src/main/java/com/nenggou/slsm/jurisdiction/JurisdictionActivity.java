package com.nenggou.slsm.jurisdiction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.CommonAppPreferences;
import com.nenggou.slsm.common.unit.PermissionUtil;
import com.nenggou.slsm.common.unit.StaticHandler;
import com.nenggou.slsm.common.unit.TokenManager;
import com.nenggou.slsm.common.widget.pickphoto.adapter.ImgGridViewAdapter;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.mainframe.ui.MainFrameActivity;
import com.nenggou.slsm.splash.GuideActivity;
import com.nenggou.slsm.splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/24.
 */

public class JurisdictionActivity extends BaseActivity {
    private static final int REQUEST_CODE = 123;
    @BindView(R.id.bg_rl)
    RelativeLayout bgRl;
    @BindView(R.id.start_jurisdiction)
    TextView startJurisdiction;
    @BindView(R.id.camear_info)
    TextView camearInfo;
    @BindView(R.id.camear_item)
    TextView camearItem;
    @BindView(R.id.camear_select)
    ImageView camearSelect;
    @BindView(R.id.camear_ll)
    RelativeLayout camearLl;
    @BindView(R.id.storage_info)
    TextView storageInfo;
    @BindView(R.id.storage_item)
    TextView storageItem;
    @BindView(R.id.storage_select)
    ImageView storageSelect;
    @BindView(R.id.storage_ll)
    RelativeLayout storageLl;

    private boolean camearFlag = false;
    private boolean storageFlag = false;

    private static final int GO_MAIN = 1;
    private static final int GO_LOGIN = 2;
    private static final int GO_GUIDE = 3;

    private List<String> groups;
    private CommonAppPreferences commonAppPreferences;
    private Handler mHandler = new MyHandler(this);

    public static void start(Context context) {
        Intent intent = new Intent(context, JurisdictionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurisdiction);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        commonAppPreferences=new CommonAppPreferences(this);
        groups = new ArrayList<>();
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                commonAppPreferences.setFirstJurisdiction("1");
                permissions();
                break;
        }
    }

    private void permissions(){
        if (!TextUtils.equals("1", commonAppPreferences.getFirstOpenApp())) {
            commonAppPreferences.setFirstOpenApp("1");
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 1000);
        } else {
            if (TextUtils.isEmpty(TokenManager.getToken())) {
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
            } else {
                mHandler.sendEmptyMessageDelayed(GO_MAIN, 1000);
            }
        }
    }


    //跳转到主页
    private void goMain() {
        MainFrameActivity.start(this);
        finish();
    }

    //跳转到主页
    private void goLogin() {
        LoginActivity.start(this);
        finish();
    }

    //跳转引导页
    private void goGuide() {
        GuideActivity.start(this);
        finish();
    }

    public static class MyHandler extends StaticHandler<JurisdictionActivity> {

        public MyHandler(JurisdictionActivity target) {
            super(target);
        }

        @Override
        public void handle(JurisdictionActivity target, Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    target.goMain();
                    break;
                case GO_LOGIN:
                    target.goLogin();
                    break;
                case GO_GUIDE:
                    target.goGuide();
                    break;
            }
        }
    }


    @OnClick({R.id.camear_ll,R.id.storage_ll,R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camear_ll:
                camearFlag = !camearFlag;
                if (camearFlag) {
                    groups.add(Manifest.permission_group.CAMERA);
                    camearLl.setBackgroundResource(R.drawable.jurisdiction_in);
                } else {
                    groups.remove(Manifest.permission_group.CAMERA);
                    camearLl.setBackgroundResource(R.drawable.jurisdiction_out);
                }
                camearInfo.setSelected(camearFlag);
                camearItem.setSelected(camearFlag);
                camearSelect.setVisibility(camearFlag ? View.VISIBLE : View.GONE);
                break;
            case R.id.storage_ll:
                storageFlag = !storageFlag;
                if (storageFlag) {
                    groups.add(Manifest.permission_group.STORAGE);
                    storageLl.setBackgroundResource(R.drawable.jurisdiction_in);
                } else {
                    groups.remove(Manifest.permission_group.STORAGE);
                    storageLl.setBackgroundResource(R.drawable.jurisdiction_out);
                }
                storageInfo.setSelected(storageFlag);
                storageItem.setSelected(storageFlag);
                storageSelect.setVisibility(storageFlag ? View.VISIBLE : View.GONE);
                break;
            case R.id.confirm:
                if (groups.size() == 0) {
                    permissions();
                } else {
                    if (requestRuntimePermissions(PermissionUtil.permissionGroup(groups, null), REQUEST_CODE)) {
                        permissions();
                    }
                }
                break;
            default:
        }
    }

}
