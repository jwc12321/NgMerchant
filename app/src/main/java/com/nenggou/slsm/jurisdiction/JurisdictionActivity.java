package com.nenggou.slsm.jurisdiction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.common.unit.PermissionUtil;
import com.nenggou.slsm.common.unit.StaticHandler;
import com.nenggou.slsm.login.ui.LoginActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JWC on 2018/7/24.
 */

public class JurisdictionActivity extends BaseActivity {
    private static final int REQUEST_CODE = 123;
    private static final int GO_LOGIN = 124;

    public static void start(Context context) {
        Intent intent = new Intent(context, JurisdictionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        List<String> groups = new ArrayList<>();
        groups.add(Manifest.permission_group.CAMERA);
        groups.add(Manifest.permission_group.STORAGE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(groups, null), REQUEST_CODE)) {

        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                LoginActivity.start(this);
                this.finish();
                break;
        }
    }
}
