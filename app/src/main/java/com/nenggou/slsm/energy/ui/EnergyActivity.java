package com.nenggou.slsm.energy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.ui.PutForwardActivity;
import com.nenggou.slsm.common.widget.dialog.CommonDialog;
import com.nenggou.slsm.common.widget.list.BaseListAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyActivity extends BaseActivity implements InEnergyFragment.InBackEnergyListener, OutEnergyFragment.OutBackEnergyListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.energy_number)
    TextView energyNumber;
    @BindView(R.id.energy_purforward)
    Button energyPurforward;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private BaseListAdapter baseListAdapter;
    private InEnergyFragment inEnergyFragment;
    private OutEnergyFragment outEnergyFragment;
    private CommonDialog putForwardDialog;
    private String sum;
    private String proportion;
    private BigDecimal sumDecimal;//总能量
    private BigDecimal ptDecimal;//兑换比例
    private BigDecimal percentageDecimal;//能量兑换比是200，要除以100才行
    private BigDecimal offsetCashDecimal;//能抵用的现金金额
    private String content;

    public static void start(Context context) {
        Intent intent = new Intent(context, EnergyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        ButterKnife.bind(this);
        setHeight(back, title, explain);
        initView();
    }

    private void initView() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewpager.setOffscreenPageLimit(1);
        inEnergyFragment = new InEnergyFragment();
        outEnergyFragment = new OutEnergyFragment();
        inEnergyFragment.setInBackEnergyListener(this);
        outEnergyFragment.setOutBackEnergyListener(this);
        fragmentList.add(inEnergyFragment);
        fragmentList.add(outEnergyFragment);
        titleList.add("收益");
        titleList.add("支出");
        baseListAdapter = new BaseListAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewpager.setAdapter(baseListAdapter);
        viewpager.setCurrentItem(0);
        indicator.removeAllTabs();
        indicator.setupWithViewPager(viewpager);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void inBackEnergySum(String sum, String proportion) {
        this.sum = sum;
        this.proportion = proportion;
        energyNumber.setText("当前" + sum + "个能量");
    }

    @Override
    public void outBackEnergySum(String sum, String proportion) {
        this.sum = sum;
        this.proportion = proportion;
        energyNumber.setText("当前" + sum + "个能量");
    }

    @OnClick({R.id.back, R.id.energy_purforward})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.energy_purforward:
                goPutFroward();
                break;
            default:
        }
    }

    private void goPutFroward() {
        if (!TextUtils.isEmpty(sum) && !TextUtils.isEmpty(proportion)) {
            sumDecimal = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_DOWN);
            ptDecimal = new BigDecimal(proportion).setScale(2, BigDecimal.ROUND_DOWN);
            percentageDecimal = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
            offsetCashDecimal = sumDecimal.multiply(ptDecimal).divide(percentageDecimal, 2, BigDecimal.ROUND_DOWN);
            content = "能量比例为" + proportion + "%,可兑换¥" + offsetCashDecimal.toString();
            if (putForwardDialog == null)
                putForwardDialog = new CommonDialog.Builder()
                        .showTitle(false)
                        .setContent(content)
                        .setContentGravity(Gravity.CENTER)
                        .setCancelButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                putForwardDialog.dismiss();
                            }
                        })
                        .setConfirmButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                putForwardDialog.dismiss();
                                PutForwardActivity.start(EnergyActivity.this, "2", offsetCashDecimal.toString());
                            }
                        }).create();
            putForwardDialog.show(getSupportFragmentManager(), "");
        }
    }
}
