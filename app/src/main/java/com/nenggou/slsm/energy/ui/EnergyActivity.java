package com.nenggou.slsm.energy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.list.BaseListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyActivity extends BaseActivity implements InEnergyFragment.InBackEnergyListener,OutEnergyFragment.OutBackEnergyListener{
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

    public static void start(Context context) {
        Intent intent = new Intent(context, EnergyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy);
        ButterKnife.bind(this);
        setHeight(back,title,explain);
        initView();
    }

    private void initView(){
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewpager.setOffscreenPageLimit(1);
        inEnergyFragment=new InEnergyFragment();
        outEnergyFragment=new OutEnergyFragment();
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
        energyNumber.setText("当前"+sum+"个能量");
    }

    @Override
    public void outBackEnergySum(String sum, String proportion) {
        energyNumber.setText("当前"+sum+"个能量");
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
