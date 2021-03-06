package com.nenggou.slsm.bill.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.bill.BillModule;
import com.nenggou.slsm.bill.DaggerBillComponent;
import com.nenggou.slsm.bill.adapter.IncomeAdapter;
import com.nenggou.slsm.bill.presenter.DayIncomePresenter;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.unit.CommonAppPreferences;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.widget.KeywordUtil;
import com.nenggou.slsm.data.RemoteDataException;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.entity.InComeInfo;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/19.
 */

public class BillFragment extends BaseFragment implements BillContract.DayIncomeView, IncomeAdapter.ItemClickListener {
    @BindView(R.id.r_income)
    TextView rIncome;
    @BindView(R.id.t_income)
    TextView tIncome;
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
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.energy_cash)
    TextView energyCash;
    private IncomeAdapter incomeAdapter;
    @Inject
    DayIncomePresenter dayIncomePresenter;

    private CommonAppPreferences commonAppPreferences;

    private String today;
    private String firstIn = "1";
    private String proportion;

    private BigDecimal offsetCashDecimal;//兑换现金
    private BigDecimal ptDecimal;//兑换比例
    private BigDecimal percentageDecimal;//能量兑换比是200，要除以100才行
    private BigDecimal energyDecimal;//总能量

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
        setHeight(rIncome, tIncome, hIncome);
        initView();
    }

    private void initView() {
        commonAppPreferences=new CommonAppPreferences(getActivity());
        today = FormatUtil.formatYMDByLine();
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        incomeAdapter = new IncomeAdapter();
        incomeAdapter.setItemClickListener(this);
        incomeRv.setAdapter(incomeAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            dayIncomePresenter.getDayIncome("0", "", today);
        }

        @Override
        public void onLoadMore() {
            dayIncomePresenter.getMoreDayIncome("", today);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (dayIncomePresenter != null) {
                    dayIncomePresenter.getDayIncome("1", "", today);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", firstIn)) {
            if (dayIncomePresenter != null) {
                dayIncomePresenter.getDayIncome("1", "", today);
            }
            firstIn = "1";
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException && ((RemoteDataException) e).isAuthFailed()) {
            firstIn = "0";
        }
        super.showError(e);
    }

    @Override
    protected void initializeInjector() {
        DaggerBillComponent.builder()
                .applicationComponent(getApplicationComponent())
                .billModule(new BillModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void setPresenter(BillContract.DayIncomePresenter presenter) {

    }

    @Override
    public void renderDayIncome(BillInfo billInfo) {
        refreshLayout.stopRefresh();
        if (billInfo != null) {
            proportion = billInfo.getPowerRate();
            incomeAdapter.setProportion(proportion);
            cashIncome.setText("现金:" + billInfo.getAllmoney() + "元");
            energyIncome.setText("能量:" + billInfo.getAllpower() + "个");
            energyDecimal = new BigDecimal(billInfo.getAllpower()).setScale(2, BigDecimal.ROUND_DOWN);
            ptDecimal = new BigDecimal(proportion).setScale(2, BigDecimal.ROUND_DOWN);
            percentageDecimal = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
            offsetCashDecimal = energyDecimal.multiply(ptDecimal).divide(percentageDecimal, 2, BigDecimal.ROUND_DOWN);
            energyCash.setText("(可兑换现金¥"+offsetCashDecimal.toString()+")");
            String totalNumberStr;
            if (billInfo.getIncomeList() != null) {
                if (!TextUtils.isEmpty(billInfo.getIncomeList().getTotal())) {
                    totalNumberStr = "共计" + billInfo.getIncomeList().getTotal() + "笔";
                } else {
                    totalNumberStr = "共计0笔";
                }
                totalNumber.setText(KeywordUtil.matcherActivity(Color.parseColor("#FF8E61"), totalNumberStr));
                List<InComeInfo> inComeInfos = billInfo.getIncomeList().getInComeInfos();
                if (inComeInfos != null && inComeInfos.size() > 0) {
                    incomeRv.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    refreshLayout.setCanLoadMore(true);
                } else {
                    incomeRv.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    refreshLayout.setCanLoadMore(false);
                }
                incomeAdapter.setData(inComeInfos);
            } else {
                incomeRv.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }
        }
        if(!TextUtils.equals("1",commonAppPreferences.getExplainCash())){
            commonAppPreferences.setExplainCash("1");
            ExplainCashActivity.start(getActivity());
        }
    }

    @Override
    public void renderMoreDayIncome(BillInfo billInfo) {
        refreshLayout.stopRefresh();
        if (billInfo != null && billInfo.getIncomeList() != null) {
            List<InComeInfo> inComeInfos = billInfo.getIncomeList().getInComeInfos();
            if (inComeInfos != null && inComeInfos.size() > 0) {
                refreshLayout.setCanLoadMore(true);
                incomeAdapter.addMore(inComeInfos);
            } else {
                refreshLayout.setCanLoadMore(false);
            }
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @OnClick({R.id.r_income, R.id.h_income,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.r_income:
                RdIncomeActivity.start(getActivity());
                break;
            case R.id.h_income:
                HistoryIncomeActivity.start(getActivity());
                break;
            default:
        }
    }

    @Override
    public void goIncomeDetail(String id) {
        IncomeDetailActivity.start(getActivity(), id);
    }
}
