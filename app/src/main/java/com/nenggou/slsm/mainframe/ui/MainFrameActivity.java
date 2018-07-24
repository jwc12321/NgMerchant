package com.nenggou.slsm.mainframe.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.ui.BillFragment;
import com.nenggou.slsm.common.viewpage.ViewPagerSlide;
import com.nenggou.slsm.financing.ui.FinancingFragment;
import com.nenggou.slsm.mainframe.adapter.MainPagerAdapter;
import com.nenggou.slsm.mine.ui.PersonalCenterFragment;
import com.nenggou.slsm.ranking.ui.RankingFragment;
import com.nenggou.slsm.receipt.ui.ReceiptFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/19.
 */

public class MainFrameActivity extends BaseActivity {

    @BindView(R.id.receipt_iv)
    ImageView receiptIv;
    @BindView(R.id.receipt_tt)
    TextView receiptTt;
    @BindView(R.id.receipt_rl)
    RelativeLayout receiptRl;
    @BindView(R.id.bill_iv)
    ImageView billIv;
    @BindView(R.id.bill_tt)
    TextView billTt;
    @BindView(R.id.bill_rl)
    RelativeLayout billRl;
    @BindView(R.id.mine_iv)
    ImageView mineIv;
    @BindView(R.id.mine_tt)
    TextView mineTt;
    @BindView(R.id.mine_rl)
    RelativeLayout mineRl;
    @BindView(R.id.viewPager)
    ViewPagerSlide viewPager;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;
    @BindView(R.id.ranking_iv)
    ImageView rankingIv;
    @BindView(R.id.ranking_tt)
    TextView rankingTt;
    @BindView(R.id.ranking_rl)
    RelativeLayout rankingRl;
    @BindView(R.id.financing_iv)
    ImageView financingIv;
    @BindView(R.id.financing_tt)
    TextView financingTt;
    @BindView(R.id.financing_rl)
    RelativeLayout financingRl;

    private RelativeLayout[] relativeLayouts;
    private BaseFragment[] fragments;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private MainPagerAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainFrameActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfram);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        fragments = new BaseFragment[5];
        fragments[0] = ReceiptFragment.newInstance();
        fragments[1] = BillFragment.newInstance();
        fragments[2] = RankingFragment.newInstance();
        fragments[3] = FinancingFragment.newInstance();
        fragments[4] = PersonalCenterFragment.newInstance();
        relativeLayouts = new RelativeLayout[5];
        relativeLayouts[0] = receiptRl;
        relativeLayouts[1] = billRl;
        relativeLayouts[2] = rankingRl;
        relativeLayouts[3] = financingRl;
        relativeLayouts[4] = mineRl;
        imageViews = new ImageView[5];
        imageViews[0] = receiptIv;
        imageViews[1] = billIv;
        imageViews[2] = rankingIv;
        imageViews[3] = financingIv;
        imageViews[4] = mineIv;
        textViews = new TextView[5];
        textViews[0] = receiptTt;
        textViews[1] = billTt;
        textViews[2] = rankingTt;
        textViews[3] = financingTt;
        textViews[4] = mineTt;
        for (RelativeLayout relativeLayout : relativeLayouts) {
            relativeLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(4);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(0);
        imageViews[0].setSelected(true);
        textViews[0].setSelected(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < relativeLayouts.length; i++) {
                if (v == relativeLayouts[i]) {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i].setSelected(position == i);
                textViews[i].setSelected(position == i);
            }
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return mainRl;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//         super.onSaveInstanceState(outState);
    }
}
