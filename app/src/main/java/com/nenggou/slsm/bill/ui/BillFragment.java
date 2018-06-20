package com.nenggou.slsm.bill.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.adapter.IncomeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/19.
 */

public class BillFragment extends BaseFragment {
    @BindView(R.id.r_income)
    TextView rIncome;
    @BindView(R.id.h_income)
    TextView hIncome;
    @BindView(R.id.t_income)
    TextView tIncome;
    @BindView(R.id.cash_income)
    TextView cashIncome;
    @BindView(R.id.energy_income)
    TextView energyIncome;
    @BindView(R.id.total_number)
    TextView totalNumber;
    @BindView(R.id.income_ll)
    LinearLayout incomeLl;
    @BindView(R.id.income_rv)
    RecyclerView incomeRv;

    private IncomeAdapter incomeAdapter;

    public BillFragment() {
    }

    public static BillFragment newInstance() {
        BillFragment receiptFragment = new BillFragment();
        return receiptFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_bill, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(rIncome,tIncome,hIncome);
        initView();
    }

    private void initView(){
        incomeAdapter=new IncomeAdapter();
        incomeRv.setAdapter(incomeAdapter);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
