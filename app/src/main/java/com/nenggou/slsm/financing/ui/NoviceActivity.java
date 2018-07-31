package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.GradationScrollView;
import com.nenggou.slsm.data.entity.FinancingItemInfo;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/31.
 * 理财新手专享
 */

public class NoviceActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.interestRate)
    TextView interestRate;
    @BindView(R.id.additional)
    TextView additional;
    @BindView(R.id.interestRate_ll)
    LinearLayout interestRateLl;
    @BindView(R.id.closed_period_info)
    TextView closedPeriodInfo;
    @BindView(R.id.closed_period)
    TextView closedPeriod;
    @BindView(R.id.surplus_amount)
    TextView surplusAmount;
    @BindView(R.id.surplus_amount_info)
    TextView surplusAmountInfo;
    @BindView(R.id.progress_iv)
    ImageView progressIv;
    @BindView(R.id.progress_first)
    TextView progressFirst;
    @BindView(R.id.progress_second)
    TextView progressSecond;
    @BindView(R.id.progress_third)
    TextView progressThird;
    @BindView(R.id.storage_mode)
    TextView storageMode;
    @BindView(R.id.storage_mode_info)
    TextView storageModeInfo;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    private FinancingItemInfo financingItemInfo;
    private BigDecimal deviationDecimal;//偏差率
    private BigDecimal interestRateDecimal;//年利率
    private BigDecimal addDecimal;//年利率+偏差率
    private BigDecimal reduceDecimal;//年利率-偏差率

    public static void start(Context context, FinancingItemInfo financingItemInfo) {
        Intent intent = new Intent(context, NoviceActivity.class);
        intent.putExtra(StaticData.FINANCING_ITEM_INFO, financingItemInfo);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novice);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        financingItemInfo = (FinancingItemInfo) getIntent().getSerializableExtra(StaticData.FINANCING_ITEM_INFO);
        if(financingItemInfo!=null){
            if(TextUtils.equals("0.00",financingItemInfo.getDeviation())){
                interestRate.setText(financingItemInfo.getInterestRate()+"%");
            }else {
                interestRateDecimal = new BigDecimal(financingItemInfo.getInterestRate()).setScale(2, BigDecimal.ROUND_DOWN);
                deviationDecimal = new BigDecimal(financingItemInfo.getDeviation()).setScale(2, BigDecimal.ROUND_DOWN);
                addDecimal = interestRateDecimal.add(deviationDecimal);
                reduceDecimal =interestRateDecimal.subtract(deviationDecimal);
                interestRate.setText(reduceDecimal.toString()+"%~"+addDecimal.toString()+"%");
            }
            if(TextUtils.equals("0.00",financingItemInfo.getAdditional())){
                additional.setText("");
            }else {
                additional.setText("+"+financingItemInfo.getAdditional()+"%("+financingItemInfo.getAdditionaltype()+")");
            }
            closedPeriodInfo.setText(financingItemInfo.getCycle()+"天");
            surplusAmount.setText("剩余金额"+financingItemInfo.getSurplus()+"元");
            storageModeInfo.setText(financingItemInfo.getStoragetype());
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
