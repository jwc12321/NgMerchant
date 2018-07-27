package com.nenggou.slsm.bill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/27.
 * 现金转能量比介绍
 */

public class ExplainCashActivity extends BaseActivity {
    @BindView(R.id.explain_cash_iv)
    ImageView explainCashIv;
    @BindView(R.id.explain_cash_item)
    RelativeLayout explainCashItem;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExplainCashActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_cash);
        ButterKnife.bind(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.explain_cash_iv, R.id.explain_cash_item,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.explain_cash_iv:
                finish();
                break;
            case R.id.explain_cash_item:
                finish();
                break;
            default:
        }
    }
}
