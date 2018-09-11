package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.data.entity.FcOrderDetailInfo;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.presenter.FcOrderDetailPresenter;
import com.nenggou.slsm.mainframe.ui.MainFrameActivity;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/4.
 */

public class FinancingOrderDetailActivity extends BaseActivity implements FinancingContract.FcOrderDetailView {
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
    @BindView(R.id.first_hook)
    ImageView firstHook;
    @BindView(R.id.first_speed)
    View firstSpeed;
    @BindView(R.id.second_hook)
    ImageView secondHook;
    @BindView(R.id.second_speed)
    View secondSpeed;
    @BindView(R.id.third_speed)
    View thirdSpeed;
    @BindView(R.id.third_hook)
    ImageView thirdHook;
    @BindView(R.id.fourth_speed)
    View fourthSpeed;
    @BindView(R.id.fifty_speed)
    View fiftySpeed;
    @BindView(R.id.fourth_hook)
    ImageView fourthHook;
    @BindView(R.id.sixth_speed)
    View sixthSpeed;
    @BindView(R.id.history_rate)
    TextView historyRate;
    @BindView(R.id.closed_period_number)
    TextView closedPeriodNumber;
    @BindView(R.id.interest_type)
    TextView interestType;
    @BindView(R.id.p_i_total)
    TextView pITotal;
    @BindView(R.id.poundage)
    TextView poundage;
    @BindView(R.id.confirm)
    Button confirm;

    private String financingId;
    private BigDecimal interestRateBd;//利息
    private BigDecimal financingCycleBd;//周期
    private BigDecimal percentageBd;//利息要除以100
    private BigDecimal profitBd;//收益金额
    private BigDecimal priceDd; //投资金额
    private BigDecimal allYearBd;//整年
    private BigDecimal pITotalBd;//本金加利息
    private BigDecimal fcAdditionalBd;//新手专享
    @Inject
    FcOrderDetailPresenter fcOrderDetailPresenter;

    public static void start(Context context, String financingId) {
        Intent intent = new Intent(context, FinancingOrderDetailActivity.class);
        intent.putExtra(StaticData.FINANCING_ID, financingId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_order_detail);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        financingId = getIntent().getStringExtra(StaticData.FINANCING_ID);
        percentageBd = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
        allYearBd = new BigDecimal(365).setScale(2, BigDecimal.ROUND_DOWN);
        fcOrderDetailPresenter.getFcOrderDetailInfo(financingId);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(FinancingContract.FcOrderDetailPresenter presenter) {

    }

    @Override
    public void renderFcOrderDetailInfo(FcOrderDetailInfo fcOrderDetailInfo) {
        if (fcOrderDetailInfo != null) {
            orderType.setText(fcOrderDetailInfo.getTitle());
            closedPeriod.setText(fcOrderDetailInfo.getCycle()+"天");
            if(!TextUtils.isEmpty(fcOrderDetailInfo.getStatus())) {
                state(Integer.parseInt(fcOrderDetailInfo.getStatus()));
            }

            if(TextUtils.equals("0.00", fcOrderDetailInfo.getAdditional())
                    ||TextUtils.equals("0", fcOrderDetailInfo.getAdditional())
                    ||TextUtils.equals("0.0", fcOrderDetailInfo.getAdditional())){
                historyRate.setText(fcOrderDetailInfo.getInterestRate() + "%");
            }else {
                historyRate.setText(fcOrderDetailInfo.getInterestRate() + "%+"+fcOrderDetailInfo.getAdditional()+"%(" + fcOrderDetailInfo.getAdditionaltype() + ")");
            }
            closedPeriodNumber.setText(fcOrderDetailInfo.getCycle()+"天");
            interestType.setText(fcOrderDetailInfo.getType());
            poundage.setText(fcOrderDetailInfo.getServicecharge()+"%");
            interestRateBd = new BigDecimal(fcOrderDetailInfo.getInterestRate()).setScale(2, BigDecimal.ROUND_DOWN);
            financingCycleBd = new BigDecimal(fcOrderDetailInfo.getCycle()).setScale(2, BigDecimal.ROUND_DOWN);
            fcAdditionalBd = new BigDecimal(fcOrderDetailInfo.getAdditional()).setScale(2, BigDecimal.ROUND_DOWN);
            priceDd= new BigDecimal(fcOrderDetailInfo.getPrice()).setScale(2, BigDecimal.ROUND_DOWN);
            profitBd=priceDd.multiply(interestRateBd.add(fcAdditionalBd)).divide(percentageBd).multiply(financingCycleBd).divide(allYearBd, 2, BigDecimal.ROUND_DOWN);
            pITotalBd=priceDd.add(profitBd);
            if (TextUtils.equals("0", fcOrderDetailInfo.getPricetype())) {
                investmentNumber.setText("项目投资:" + fcOrderDetailInfo.getPrice() + "个能量");
                alreadyEarnings.setText(fcOrderDetailInfo.getAccumulative()+"个");
                pITotal.setText(pITotalBd.toString()+"个能量");
            } else {
                investmentNumber.setText("项目投资:" + fcOrderDetailInfo.getPrice() + "元现金");
                alreadyEarnings.setText(fcOrderDetailInfo.getAccumulative()+"元");
                pITotal.setText(pITotalBd.toString()+"元现金");
            }
        }
    }

    private void state(int stateInt){
        firstHook.setSelected(stateInt>=0?true:false);
        secondHook.setSelected(stateInt>=1?true:false);
        thirdHook.setSelected(stateInt>=2?true:false);
        fourthHook.setSelected(stateInt>=3?true:false);
        firstSpeed.setSelected(stateInt>0?true:false);
        secondSpeed.setSelected(stateInt>0?true:false);
        thirdSpeed.setSelected(stateInt>1?true:false);
        fourthSpeed.setSelected(stateInt>1?true:false);
        fiftySpeed.setSelected(stateInt>2?true:false);
        sixthSpeed.setSelected(stateInt>2?true:false);
    }

    @Override
    protected void initializeInjector() {
        DaggerFinancingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .financingModule(new FinancingModule(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.back,R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                MainFrameActivity.start(this);
                break;
            default:
        }
    }
}
