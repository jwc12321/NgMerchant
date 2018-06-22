package com.nenggou.slsm.cash.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.ui.ChoiceTimeActivity;
import com.nenggou.slsm.bill.ui.HistoryIncomeActivity;
import com.nenggou.slsm.cash.CashContract;
import com.nenggou.slsm.cash.CashModule;
import com.nenggou.slsm.cash.DaggerCashComponent;
import com.nenggou.slsm.cash.presenter.CashPresenter;
import com.nenggou.slsm.data.entity.CashInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 */

public class CashActivity extends BaseActivity implements CashContract.CashView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cash_detail)
    TextView cashDetail;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.put_forward_bt)
    Button putForwardBt;

    @Inject
    CashPresenter cashPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CashActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);
        setHeight(back, title, cashDetail);
        cashPresenter.getCashInfo();
    }

    @Override
    protected void initializeInjector() {
        DaggerCashComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cashModule(new CashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(CashContract.CashPresenter presenter) {

    }

    @Override
    public void renderCashInfo(CashInfo cashInfo) {
        if(cashInfo!=null){
            price.setText("¥ "+cashInfo.getXianJin());
        }
    }

    @OnClick({R.id.back, R.id.cash_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.cash_detail:
                CashDetailActivity.start(this);
                break;
            default:
        }
    }
}
