package com.nenggou.slsm.financing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.FinancingItemInfo;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/8/1.
 */

public class ProjectDetailsFragment extends BaseFragment {

    @BindView(R.id.interest_rate)
    TextView interestRate;
    @BindView(R.id.additional)
    TextView additional;
    @BindView(R.id.interestRate_ll)
    LinearLayout interestRateLl;
    @BindView(R.id.project_total_price)
    TextView projectTotalPrice;
    @BindView(R.id.closed_period)
    TextView closedPeriod;
    @BindView(R.id.interest_type)
    TextView interestType;
    @BindView(R.id.poundage)
    TextView poundage;
    private BigDecimal deviationDecimal;//偏差率
    private BigDecimal interestRateDecimal;//年利率
    private BigDecimal addDecimal;//年利率+偏差率
    private BigDecimal reduceDecimal;//年利率-偏差率
    private FinancingItemInfo financingItemInfo;

    public void addFinancingItemInfo(FinancingItemInfo financingItemInfo) {
        this.financingItemInfo=financingItemInfo;
        if (!isFirstLoad && getUserVisibleHint()&&financingItemInfo!=null) {
            if (TextUtils.equals("0.00", financingItemInfo.getDeviation())) {
                interestRate.setText(financingItemInfo.getInterestRate() + "%");
            } else {
                interestRateDecimal = new BigDecimal(financingItemInfo.getInterestRate()).setScale(2, BigDecimal.ROUND_DOWN);
                deviationDecimal = new BigDecimal(financingItemInfo.getDeviation()).setScale(2, BigDecimal.ROUND_DOWN);
                addDecimal = interestRateDecimal.add(deviationDecimal);
                reduceDecimal = interestRateDecimal.subtract(deviationDecimal);
                interestRate.setText(reduceDecimal.toString() + "%~" + addDecimal.toString() + "%");
            }
            if (TextUtils.equals("0.00", financingItemInfo.getAdditional())) {
                additional.setText("");
            } else {
                additional.setText("+" + financingItemInfo.getAdditional() + "%(" + financingItemInfo.getAdditionaltype() + ")");
            }
            projectTotalPrice.setText(financingItemInfo.getTotalAmount()+"元");
            closedPeriod.setText(financingItemInfo.getCycle() + "天");
            interestType.setText(financingItemInfo.getType());
            poundage.setText(financingItemInfo.getServicecharge());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_project_detail, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint()&&financingItemInfo!=null) {
            addFinancingItemInfo(financingItemInfo);
        }
    }


    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
