package com.nenggou.slsm.receipt.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.ui.PutForwardActivity;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.dialog.CommonDialog;
import com.nenggou.slsm.data.entity.WebViewDetailInfo;
import com.nenggou.slsm.data.request.TextRequest;
import com.nenggou.slsm.energy.ui.EnergyActivity;
import com.nenggou.slsm.receipt.DaggerReceiptComponent;
import com.nenggou.slsm.receipt.ReceiptContract;
import com.nenggou.slsm.receipt.ReceiptModule;
import com.nenggou.slsm.receipt.presenter.QrCodePresenter;
import com.nenggou.slsm.setting.ui.SettingActivity;
import com.nenggou.slsm.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;


public class QrCodeScanActivity extends BaseActivity implements QRCodeView.Delegate, ReceiptContract.QrCodeView {

    private static final String TAG = "QrCodeScanActivity";

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;

    @Inject
    QrCodePresenter qrCodePresenter;

    private String storeId;

    private WebViewDetailInfo webViewDetailInfo;
    private CommonDialog textDialog;


    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    public static void start(Context context, String storeId) {
        Intent intent = new Intent(context, QrCodeScanActivity.class);
        intent.putExtra(StaticData.STORE_ID,storeId);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        storeId=getIntent().getStringExtra(StaticData.STORE_ID);
        mQRCodeView.setDelegate(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void initializeInjector() {
        super.initializeInjector();
        DaggerReceiptComponent.builder()
                .applicationComponent(getApplicationComponent())
                .receiptModule(new ReceiptModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQRCodeView.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.d(TAG, "onScanQRCodeSuccess: " + result);
        if (!TextUtils.isEmpty(result)) {
            qrCodePresenter.uploadQrText(storeId + "|||" + result);
        }
        vibrate();
        mQRCodeView.startSpot();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "onScanQRCodeOpenCameraError: ");
    }

    @Override
    public void setPresenter(ReceiptContract.QrCodePresenter presenter) {

    }

    @Override
    public void backQrText(String backQrtext) {
        if (TextUtils.isEmpty(backQrtext)) {
            if (backQrtext.startsWith("http")) {
                webViewDetailInfo = new WebViewDetailInfo();
                webViewDetailInfo.setUrl(backQrtext);
                WebViewActivity.start(this, webViewDetailInfo);
            } else {
                if (textDialog == null)
                    textDialog = new CommonDialog.Builder()
                            .showTitle(false)
                            .setContent(backQrtext)
                            .setContentGravity(Gravity.CENTER)
                            .showButton(false)
                            .setConfirmButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    textDialog.dismiss();
                                    QrCodeScanActivity.this.finish();
                                }
                            }).create();
                textDialog.show(getSupportFragmentManager(), "");
            }
        }
    }
}
