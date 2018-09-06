package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.BuildConfig;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.GradationScrollView;
import com.nenggou.slsm.common.widget.list.BaseListAdapter;
import com.nenggou.slsm.common.widget.web.NoScrollWebView;
import com.nenggou.slsm.data.entity.FinancingItemInfo;
import com.nenggou.slsm.webview.ui.WebViewFragment;
import com.nenggou.slsm.webview.unit.JSBridgeWebChromeClient;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/31.
 * 理财新手专享
 */

public class NoviceActivity extends BaseActivity {

    @BindView(R.id.magic_indicator_title)
    MagicIndicator magicIndicatorTitle;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.interestRate)
    TextView interestRate;
    @BindView(R.id.additional)
    TextView additional;
    @BindView(R.id.interestRate_ll)
    LinearLayout interestRateLl;
    @BindView(R.id.closed_period_info)
    TextView closedPeriodInfo;
    @BindView(R.id.closed_period)
    TextView closedPeriod;
    @BindView(R.id.surplus_amount)
    TextView surplusAmount;
    @BindView(R.id.surplus_amount_info)
    TextView surplusAmountInfo;
    @BindView(R.id.progress_first_iv)
    ImageView progressFirstIv;
    @BindView(R.id.progress_second_iv)
    ImageView progressSecondIv;
    @BindView(R.id.progress_third_iv)
    ImageView progressThirdIv;
    @BindView(R.id.progress_ll)
    LinearLayout progressLl;
    @BindView(R.id.progress_first)
    TextView progressFirst;
    @BindView(R.id.progress_second)
    TextView progressSecond;
    @BindView(R.id.progress_third)
    TextView progressThird;
    @BindView(R.id.storage_mode)
    TextView storageMode;
    @BindView(R.id.storage_mode_info)
    TextView storageModeInfo;
    @BindView(R.id.detail_tv)
    TextView detailTv;
    @BindView(R.id.detail_view)
    View detailView;
    @BindView(R.id.detail_rl)
    RelativeLayout detailRl;
    @BindView(R.id.problem_tv)
    TextView problemTv;
    @BindView(R.id.problem_view)
    View problemView;
    @BindView(R.id.problem_rl)
    RelativeLayout problemRl;
    @BindView(R.id.d_interest_rate)
    TextView dInterestRate;
    @BindView(R.id.d_additional)
    TextView dAdditional;
    @BindView(R.id.d_interestRate_ll)
    LinearLayout dInterestRateLl;
    @BindView(R.id.project_total_price)
    TextView projectTotalPrice;
    @BindView(R.id.d_closed_period)
    TextView dClosedPeriod;
    @BindView(R.id.d_interest_type)
    TextView dInterestType;
    @BindView(R.id.d_poundage)
    TextView dPoundage;
    @BindView(R.id.detail_ll)
    LinearLayout detailLl;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.webView)
    NoScrollWebView webView;
    @BindView(R.id.next)
    Button next;
    private FinancingItemInfo financingItemInfo;
    private BigDecimal deviationDecimal;//偏差率
    private BigDecimal interestRateDecimal;//年利率
    private BigDecimal addDecimal;//年利率+偏差率
    private BigDecimal reduceDecimal;//年利率-偏差率

    public static void start(Context context, FinancingItemInfo financingItemInfo) {
        Intent intent = new Intent(context, NoviceActivity.class);
        intent.putExtra(StaticData.FINANCING_ITEM_INFO, financingItemInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novice);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initWeb();
        initView();
    }

    private void initView() {
        financingItemInfo = (FinancingItemInfo) getIntent().getSerializableExtra(StaticData.FINANCING_ITEM_INFO);
        if (financingItemInfo != null) {
            title.setText(financingItemInfo.getTitle());
            if (TextUtils.equals("0.00", financingItemInfo.getDeviation())) {
                interestRate.setText(financingItemInfo.getInterestRate() + "%");
            } else {
                interestRateDecimal = new BigDecimal(financingItemInfo.getInterestRate()).setScale(2, BigDecimal.ROUND_DOWN);
                deviationDecimal = new BigDecimal(financingItemInfo.getDeviation()).setScale(2, BigDecimal.ROUND_DOWN);
                addDecimal = interestRateDecimal.add(deviationDecimal);
                reduceDecimal = interestRateDecimal.subtract(deviationDecimal);
                interestRate.setText(reduceDecimal.toString() + "%~" + addDecimal.toString() + "%");
            }
            if (TextUtils.equals("0.00", financingItemInfo.getAdditional())) {
                additional.setText("");
            } else {
                additional.setText("+" + financingItemInfo.getAdditional() + "%(" + financingItemInfo.getAdditionaltype() + ")");
            }
            closedPeriodInfo.setText(financingItemInfo.getCycle() + "天");
            surplusAmountInfo.setText(financingItemInfo.getSurplus() + "元");
            progressSecond.setText(financingItemInfo.getCycle() + "天\n持续享收益");
            storageModeInfo.setText(financingItemInfo.getStoragetype());
            if (TextUtils.equals("0", financingItemInfo.getStatus())) {
                progressFirstIv.setSelected(false);
                progressSecondIv.setSelected(false);
                progressThirdIv.setSelected(false);
            } else if (TextUtils.equals("1", financingItemInfo.getStatus())) {
                progressFirstIv.setSelected(true);
                progressSecondIv.setSelected(false);
                progressThirdIv.setSelected(false);
            } else if (TextUtils.equals("2", financingItemInfo.getStatus())) {
                progressFirstIv.setSelected(true);
                progressSecondIv.setSelected(true);
                progressThirdIv.setSelected(false);
            } else if (TextUtils.equals("3", financingItemInfo.getStatus())) {
                progressFirstIv.setSelected(true);
                progressSecondIv.setSelected(true);
                progressThirdIv.setSelected(true);
            }


            if (TextUtils.equals("0.00", financingItemInfo.getDeviation())) {
                dInterestRate.setText(financingItemInfo.getInterestRate() + "%");
            } else {
                interestRateDecimal = new BigDecimal(financingItemInfo.getInterestRate()).setScale(2, BigDecimal.ROUND_DOWN);
                deviationDecimal = new BigDecimal(financingItemInfo.getDeviation()).setScale(2, BigDecimal.ROUND_DOWN);
                addDecimal = interestRateDecimal.add(deviationDecimal);
                reduceDecimal = interestRateDecimal.subtract(deviationDecimal);
                dInterestRate.setText(reduceDecimal.toString() + "%~" + addDecimal.toString() + "%");
            }
            if (TextUtils.equals("0.00", financingItemInfo.getAdditional())) {
                dAdditional.setText("");
            } else {
                dAdditional.setText("+" + financingItemInfo.getAdditional() + "%(" + financingItemInfo.getAdditionaltype() + ")");
            }
            projectTotalPrice.setText(financingItemInfo.getTotalAmount() + "元");
            dClosedPeriod.setText(financingItemInfo.getCycle() + "天");
            dInterestType.setText(financingItemInfo.getType());
            dPoundage.setText(financingItemInfo.getServicecharge());
        }
    }

    //初始化web
    private void initWeb() {
        WebSettings settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setJavaScriptEnabled(true);

        //设置是否支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        //设置是否显示缩放按钮
        settings.setDisplayZoomControls(true);

        //设置自适应屏幕宽度
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);


        webView.setWebChromeClient(new JSBridgeWebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        String url = BuildConfig.API_BASE_URL + "home/financing/detail";
        webView.loadUrl(url);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.detail_rl, R.id.problem_rl,R.id.next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.detail_rl:
                initTextColor("1");
                break;
            case R.id.problem_rl:
                initTextColor("2");
                break;
            case R.id.next:
                PayFinancingOrderActivity.start(this);
                break;
            default:
        }
    }

    private void initTextColor(String type) {
        detailTv.setSelected(TextUtils.equals("1", type) ? true : false);
        problemTv.setSelected(TextUtils.equals("2", type) ? true : false);
        detailView.setVisibility(TextUtils.equals("1", type) ? View.VISIBLE : View.GONE);
        problemView.setVisibility(TextUtils.equals("2", type) ? View.VISIBLE : View.GONE);
        detailLl.setVisibility(TextUtils.equals("1", type) ? View.VISIBLE : View.GONE);
        webView.setVisibility(TextUtils.equals("2", type) ? View.VISIBLE : View.GONE);
    }
}
