package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/4.
 * 理财钱包
 */

public class FinancingWalletActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.energy_alp)
    TextView energyAlp;
    @BindView(R.id.energy_eyes)
    ImageView energyEyes;
    @BindView(R.id.energy_total_assets)
    TextView energyTotalAssets;
    @BindView(R.id.energy_total_ll)
    LinearLayout energyTotalLl;
    @BindView(R.id.energy_turn_out)
    Button energyTurnOut;
    @BindView(R.id.cash_alp)
    TextView cashAlp;
    @BindView(R.id.cash_eyes)
    ImageView cashEyes;
    @BindView(R.id.cash_total_assets)
    TextView cashTotalAssets;
    @BindView(R.id.cash_total_ll)
    LinearLayout cashTotalLl;
    @BindView(R.id.cash_turn_out)
    Button cashTurnOut;
    @BindView(R.id.turn_out_record)
    TextView turnOutRecord;

    private boolean isEnergyFlag = true;
    private boolean isCashFlag = true;

    public static void start(Context context) {
        Intent intent = new Intent(context, FinancingWalletActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_wallet);
        ButterKnife.bind(this);
        setHeight(back, title, turnOutRecord);
        initView();
    }

    private void initView() {
        energyEyes.setSelected(true);
        cashEyes.setSelected(true);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.energy_eyes, R.id.cash_eyes, R.id.energy_turn_out, R.id.cash_turn_out,R.id.turn_out_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.energy_eyes:
                isEnergyFlag = !isEnergyFlag;
                energyEyes.setSelected(isEnergyFlag);
                break;
            case R.id.cash_eyes:
                isCashFlag = !isCashFlag;
                cashEyes.setSelected(isCashFlag);
                break;
            case R.id.energy_turn_out:
                TurnOutBalanceActivity.start(this);
                break;
            case R.id.cash_turn_out:
                TurnOutBalanceActivity.start(this);
                break;
            case R.id.turn_out_record:
                TurnOutRecordActivity.start(this);
                break;
            default:
        }
    }

}
