package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/9/4.
 */

public class FinancingOrderDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.order_type)
    TextView orderType;
    @BindView(R.id.investment_number)
    TextView investmentNumber;
    @BindView(R.id.expected_earnings)
    TextView expectedEarnings;
    @BindView(R.id.already_earnings)
    TextView alreadyEarnings;
    @BindView(R.id.closed_period)
    TextView closedPeriod;

    public static void start(Context context) {
        Intent intent = new Intent(context, FinancingOrderDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_order_detail);
        ButterKnife.bind(this);
        setHeight(back,title,null);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
