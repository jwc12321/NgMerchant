package com.nenggou.slsm.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.TokenManager;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.mainframe.ui.MainFrameActivity;
import com.nenggou.slsm.setting.ui.SettingActivity;
import com.nenggou.slsm.splash.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.guide_bt)
    Button guideBt;
    private List<View> views;
    private GuideAdapter vpAdapter;
    //用pref记录是否为首次载入
    private static final String SHAREDPREFERENCES_NAME = "ngm_first_pref";


    public static void start(Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        intViews();
    }


    /**
     * 初始化引导页面
     */
    private void intViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();

        //初始化引导页面列表
        views.add(inflater.inflate(R.layout.guide_page_one, null));
        views.add(inflater.inflate(R.layout.guide_page_two, null));

        //初始化Adapter
        vpAdapter = new GuideAdapter(views, this);
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==1){
            guideBt.setVisibility(View.VISIBLE);
        }else {
            guideBt.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({ R.id.guide_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_bt:
                setGuided();
                if (TextUtils.isEmpty(TokenManager.getToken())) {
                    LoginActivity.start(this);
                } else {
                    MainFrameActivity.start(this);
                }
                this.finish();
                break;
            default:
        }
    }

    /**
     * 跳转到引导页面
     */
    private void setGuided() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //走完一遍引导页面后设置标志为false
        editor.putBoolean("isFirstIn", false);
        editor.commit();
    }


}
