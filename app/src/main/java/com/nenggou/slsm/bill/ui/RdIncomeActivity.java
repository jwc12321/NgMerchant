package com.nenggou.slsm.bill.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.bill.BillModule;
import com.nenggou.slsm.bill.DaggerBillComponent;
import com.nenggou.slsm.bill.adapter.HMIncomeAdapter;
import com.nenggou.slsm.bill.presenter.RdIncomePresenter;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.widget.GradationScrollView;
import com.nenggou.slsm.common.widget.KeywordUtil;
import com.nenggou.slsm.data.entity.HistoryIncomAll;
import com.nenggou.slsm.data.entity.HistoryIncomInfo;
import com.nenggou.slsm.data.entity.HistoryIncomeItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 * 推荐收益
 */

public class RdIncomeActivity extends BaseActivity implements BillContract.RdIncomeView, HMIncomeAdapter.ItemClickListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.h_income)
    TextView hIncome;
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
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    private String storeid;
    private String startTime;
    private String endTime;

    @Inject
    RdIncomePresenter rdIncomePresenter;

    private HMIncomeAdapter hmIncomeAdapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, RdIncomeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd_income);
        ButterKnife.bind(this);
        setHeight(back, hIncome, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        hmIncomeAdapter = new HMIncomeAdapter();
        hmIncomeAdapter.setItemClickListener(this);
        incomeRv.setAdapter(hmIncomeAdapter);
        rdIncomePresenter.getRdIncome("1");
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            rdIncomePresenter.getRdIncome("0");
        }

        @Override
        public void onLoadMore() {
            rdIncomePresenter.getMoreRdIncome();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerBillComponent.builder()
                .applicationComponent(getApplicationComponent())
                .billModule(new BillModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderRdIncome(HistoryIncomInfo historyIncomInfo) {
        refreshLayout.stopRefresh();
        if (historyIncomInfo != null) {
            List<HistoryIncomAll> historyIncomAlls = historyIncomInfo.getHistoryIncomAlls();
            if (historyIncomAlls != null && historyIncomAlls.size() > 0) {
                HistoryIncomAll historyIncomAll = historyIncomAlls.get(0);
                cashIncome.setText("现金:" + historyIncomAll.getAllmoney() + "元");
                energyIncome.setText("能量" + historyIncomAll.getAllpower() + "个");
                String totalNumberStr;
                if (!TextUtils.isEmpty(historyIncomAll.getAllTotal())) {
                    totalNumberStr = "共计" + historyIncomAll.getAllTotal() + "笔";
                } else {
                    totalNumberStr = "共计0笔";
                }
                totalNumber.setText(KeywordUtil.matcherActivity(Color.parseColor("#FF8E61"), totalNumberStr));
            }
            if (historyIncomInfo.getHistoryIncomCount() != null) {
                List<HistoryIncomeItem> historyIncomeItems = historyIncomInfo.getHistoryIncomCount().getHistoryIncomeItems();
                if (historyIncomeItems != null && historyIncomeItems.size() > 0) {
                    refreshLayout.setCanLoadMore(true);
                } else {
                    refreshLayout.setCanLoadMore(false);
                }
                hmIncomeAdapter.setData(historyIncomeItems);
            }
        }
    }

    @Override
    public void renderMoreRdIncome(HistoryIncomInfo historyIncomInfo) {
        refreshLayout.stopRefresh();
        if (historyIncomInfo != null && historyIncomInfo.getHistoryIncomCount() != null) {
            List<HistoryIncomeItem> historyIncomeItems = historyIncomInfo.getHistoryIncomCount().getHistoryIncomeItems();
            if (historyIncomeItems != null && historyIncomeItems.size() > 0) {
                refreshLayout.setCanLoadMore(true);
            } else {
                refreshLayout.setCanLoadMore(false);
            }
            hmIncomeAdapter.addMore(historyIncomeItems);
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }
    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }


    @Override
    public void goIncomeList(String date) {
        IncomeListActivity.start(this, storeid, date, "3");
    }

    @Override
    public void setPresenter(BillContract.RdIncomePresenter presenter) {

    }
}
