package com.nenggou.slsm.financing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/6.
 */

public class TurnOutScreenActivity extends BaseActivity {
    @BindView(R.id.all_bt)
    Button allBt;
    @BindView(R.id.cash_bt)
    Button cashBt;
    @BindView(R.id.energy_bt)
    Button energyBt;
    @BindView(R.id.item_turn_out_screen)
    RelativeLayout itemTurnOutScreen;

    private String selectWhat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_out_screen);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        selectWhat = getIntent().getStringExtra(StaticData.SELECT_WHAT);
        allBt.setSelected(TextUtils.equals("0",selectWhat)?true:false);
        cashBt.setSelected(TextUtils.equals("1",selectWhat)?true:false);
        energyBt.setSelected(TextUtils.equals("2",selectWhat)?true:false);
    }

    @OnClick({R.id.item_turn_out_screen, R.id.all_bt,R.id.cash_bt,R.id.energy_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_turn_out_screen:
                finish();
                break;
            case R.id.all_bt:
                returnWhat("0");
                break;
            case R.id.cash_bt:
                returnWhat("1");
                break;
            case R.id.energy_bt:
                returnWhat("2");
                break;
            default:
        }
    }

    private void returnWhat(String what){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(StaticData.SELECT_WHAT, what);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
