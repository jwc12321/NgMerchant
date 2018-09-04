package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 */

public class PayFinancingOrderActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.amount_et)
    EditText amountEt;
    @BindView(R.id.reduce_number)
    ImageView reduceNumber;
    @BindView(R.id.increase_number)
    ImageView increaseNumber;
    @BindView(R.id.initial_value)
    TextView initialValue;
    @BindView(R.id.income_statement)
    TextView incomeStatement;
    @BindView(R.id.input_ll)
    LinearLayout inputLl;
    @BindView(R.id.available_type)
    TextView availableType;
    @BindView(R.id.available_item)
    TextView availableItem;
    @BindView(R.id.available_total)
    TextView availableTotal;
    @BindView(R.id.confirm)
    Button confirm;

    public static void start(Context context) {
        Intent intent = new Intent(context, PayFinancingOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_financing_order);
        ButterKnife.bind(this);
        setHeight(back,title,null);
    }

    private void initView(){

    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                FinancingOrderDetailActivity.start(this);
                break;
            default:
        }
    }
}
