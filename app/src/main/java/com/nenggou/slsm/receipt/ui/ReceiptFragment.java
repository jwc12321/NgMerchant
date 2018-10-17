package com.nenggou.slsm.receipt.ui;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.BuildConfig;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.ui.MonthIncomeActivity;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.unit.CommonAppPreferences;
import com.nenggou.slsm.common.unit.DownloadService;
import com.nenggou.slsm.common.unit.PermissionUtil;
import com.nenggou.slsm.common.widget.dialog.CommonDialog;
import com.nenggou.slsm.common.widget.viewpagecards.CardPagerAdapter;
import com.nenggou.slsm.common.widget.viewpagecards.ShadowTransformer;
import com.nenggou.slsm.data.RemoteDataException;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.data.entity.ChangeAppInfo;
import com.nenggou.slsm.evaluate.ui.AllEvaluationActivity;
import com.nenggou.slsm.receipt.DaggerReceiptComponent;
import com.nenggou.slsm.receipt.ReceiptContract;
import com.nenggou.slsm.receipt.ReceiptModule;
import com.nenggou.slsm.receipt.presenter.ReceiptPresenter;
import com.nenggou.slsm.service.VoiceService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/19.
 */

public class ReceiptFragment extends BaseFragment implements ReceiptContract.ReceiptView, CardPagerAdapter.ItemClickListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    private CardPagerAdapter cardPagerAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private ChangeAppInfo changeAppInfo;

    private static final int REQUEST_PERMISSION_WRITE = 2;
    private static final int REQUEST_CODE_CAMERA = 4;

    @Inject
    ReceiptPresenter receiptPresenter;
    private String storeId;

    private String firstIn = "1";
    private String firstUpdate = "1";
    private String canUpload="0";

    public ReceiptFragment() {
    }

    public static ReceiptFragment newInstance() {
        ReceiptFragment receiptFragment = new ReceiptFragment();
        return receiptFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_receipt, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.STORAGE);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_PERMISSION_WRITE)) {
            canUpload="1";
            Intent intent = new Intent(getActivity(), VoiceService.class);
            getActivity().startService(intent);
        }
        receiptPresenter.getAppstoreInfos("1");
    }


    private boolean isFirstLoad = true;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
            }
        }
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            receiptPresenter.getAppstoreInfos("0");
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerReceiptComponent.builder()
                .applicationComponent(getApplicationComponent())
                .receiptModule(new ReceiptModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", firstIn)) {
            if (receiptPresenter != null) {
                receiptPresenter.getAppstoreInfos("1");
            }
            firstIn = "1";
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException && ((RemoteDataException) e).isAuthFailed()) {
            firstIn = "0";
        }
        super.showError(e);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(ReceiptContract.ReceiptPresenter presenter) {

    }

    @Override
    public void renderAppstoreInfos(List<AppstoreInfo> appstoreInfos) {
        if (TextUtils.equals("1", firstUpdate)&&TextUtils.equals("1",canUpload)) {
            receiptPresenter.detectionVersion(BuildConfig.VERSION_NAME, "android");
            firstUpdate = "0";
        }
        refreshLayout.stopRefresh();
        if (appstoreInfos != null && appstoreInfos.size() > 0) {
            cardPagerAdapter = new CardPagerAdapter(appstoreInfos);
            cardPagerAdapter.setOnItemClickListener(this);
            mCardShadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
            if (appstoreInfos.size() != 1) {
                mCardShadowTransformer.enableScaling(true);
            }
            viewPager.setAdapter(cardPagerAdapter);
            viewPager.setPageTransformer(false, mCardShadowTransformer);
            if (appstoreInfos.size() > 0) {
                viewPager.setOffscreenPageLimit(appstoreInfos.size() - 1);
            }
        }
    }


    @Override
    public void goMonthIncome(String storeid) {
        MonthIncomeActivity.start(getActivity(), storeid);
    }

    @Override
    public void goBuyerEvaluate(String storeid) {
        AllEvaluationActivity.start(getActivity(), storeid);
    }

    @Override
    public void goScan(String storeid) {
        this.storeId = storeid;
        scan();
    }

    /**
     * 扫描
     */
    void scan() {
        List<String> group = new ArrayList<>();
        group.add(Manifest.permission_group.CAMERA);
        if (requestRuntimePermissions(PermissionUtil.permissionGroup(group, null), REQUEST_CODE_CAMERA)) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            QrCodeScanActivity.start(getActivity(), storeId);
        }
    }

    private CommonDialog dialogUpdate;
    private CommonDialog dialogToSetPush;

    @Override
    public void detectionSuccess(ChangeAppInfo changeAppInfo) {
        this.changeAppInfo = changeAppInfo;
        if (changeAppInfo != null && TextUtils.equals("1", changeAppInfo.getStatus())) {
            if (requestRuntimePermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION_WRITE)) {
                showUpdate(changeAppInfo);
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                boolean isPm = isNotificationEnabled(getActivity());
                if (!isPm) {
                    toSetPush();
                }
            }
        }
    }

    private void showUpdate(final ChangeAppInfo changeAppInfo) {
        if (dialogUpdate == null) {
            dialogUpdate = new CommonDialog.Builder()
                    .setTitle("版本更新")
                    .setContent(changeAppInfo.getTitle())
                    .setContentGravity(Gravity.CENTER)
                    .setCancelButton("忽略", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogUpdate.dismiss();
                        }
                    })
                    .setConfirmButton("更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showMessage("开始下载");
                            updateApk(changeAppInfo.getUrl());
                        }
                    }).create();
            dialogUpdate.show(getFragmentManager(), "");
        }
    }

    private void toSetPush() {
        if (dialogToSetPush == null) {
            dialogToSetPush = new CommonDialog.Builder()
                    .setTitle("推送权限")
                    .setContent("点击确定，找到通知，打开其中的权限，就可以收到语音提示了")
                    .setContentGravity(Gravity.CENTER)
                    .setCancelButton("忽略", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogToSetPush.dismiss();
                        }
                    })
                    .setConfirmButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toSetting();
                            dialogToSetPush.dismiss();
                        }
                    }).create();
            dialogToSetPush.show(getFragmentManager(), "");
        }
    }

    private MaterialDialog materialDialog;

    private void updateApk(String downUrl) {
        materialDialog = new MaterialDialog.Builder(getActivity())

                .title("版本升级")
                .content("正在下载安装包，请稍候")

                .progress(false, 100, false)
                .cancelable(false)
                .negativeText("取消")

                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        DownloadService.stopDownload();
                    }
                })
                .show();
        DownloadService.setMaterialDialog(materialDialog);
        DownloadService.start(getActivity(), downUrl, "6F7FBCECD46341DF08BE8B11A09E6925");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0) {
                    for (int gra : grantResults) {
                        if (gra != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                }
                scan();
                break;
            case REQUEST_PERMISSION_WRITE:
                for (int gra : grantResults) {
                    if (gra != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                Intent intent = new Intent(getActivity(), VoiceService.class);
                getActivity().startService(intent);
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
  /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void toSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", BuildConfig.APPLICATION_ID, null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", BuildConfig.APPLICATION_ID);
        }
        startActivity(localIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
