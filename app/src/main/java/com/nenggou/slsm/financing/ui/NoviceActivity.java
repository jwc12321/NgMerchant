package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.BuildConfig;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.ColorFlipPagerTitleView;
import com.nenggou.slsm.common.widget.GradationScrollView;
import com.nenggou.slsm.common.widget.list.BaseListAdapter;
import com.nenggou.slsm.data.entity.FinancingItemInfo;
import com.nenggou.slsm.webview.ui.WebViewFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/31.
 * 理财新手专享
 */

public class NoviceActivity extends BaseActivity {
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
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
//    @BindView(R.id.scrollview)
//    GradationScrollView scrollview;
    @BindView(R.id.magic_indicator_title)
    MagicIndicator magicIndicatorTitle;
    private FinancingItemInfo financingItemInfo;
    private BigDecimal deviationDecimal;//偏差率
    private BigDecimal interestRateDecimal;//年利率
    private BigDecimal addDecimal;//年利率+偏差率
    private BigDecimal reduceDecimal;//年利率-偏差率
    private ProjectDetailsFragment projectDetailsFragment;
    private WebViewFragment webViewFragment;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private BaseListAdapter baseListAdapter;

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
        initView();
    }

    private void initView() {
        financingItemInfo = (FinancingItemInfo) getIntent().getSerializableExtra(StaticData.FINANCING_ITEM_INFO);
        if (financingItemInfo != null) {
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
            fragmentList = new ArrayList<>();
            titleList = new ArrayList<>();
            viewpager.setOffscreenPageLimit(1);
            projectDetailsFragment = new ProjectDetailsFragment();
            webViewFragment = new WebViewFragment();
            fragmentList.add(projectDetailsFragment);
            fragmentList.add(webViewFragment);
            titleList.add("项目详情");
            titleList.add("常见问题");
            baseListAdapter = new BaseListAdapter(getSupportFragmentManager(), fragmentList, titleList);
            viewpager.setAdapter(baseListAdapter);
            viewpager.setCurrentItem(0);
            indicator.removeAllTabs();
            indicator.setupWithViewPager(viewpager);
            projectDetailsFragment.addFinancingItemInfo(financingItemInfo);
            String url = BuildConfig.API_BASE_URL + "home/financing/detail";
            webViewFragment.addUrl(url);
        }
    }
    @Override
    public View getSnackBarHolderView() {
        return null;
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
}
