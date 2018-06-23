package com.nenggou.slsm.bill.ui;

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
import com.nenggou.slsm.bill.adapter.IncomeAdapter;
import com.nenggou.slsm.bill.presenter.DayIncomePresenter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.widget.GradationScrollView;
import com.nenggou.slsm.common.widget.KeywordUtil;
import com.nenggou.slsm.data.entity.BillInfo;
import com.nenggou.slsm.data.entity.InComeInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 */

public class IncomeListActivity extends BaseActivity implements BillContract.DayIncomeView,IncomeAdapter.ItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.t_income)
    TextView tIncome;
    @BindView(R.id.date_tv)
    TextView dateTv;
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
    private String date;
    private String timeType; //1:本月收入 2：历史收入 3：推荐收入

    private IncomeAdapter incomeAdapter;
    @Inject
    DayIncomePresenter dayIncomePresenter;


    public static void start(Context context, String storeId,String date,String timeType) {
        Intent intent = new Intent(context, IncomeListActivity.class);
        intent.putExtra(StaticData.STORE_ID, storeId);
        intent.putExtra(StaticData.DATE,date);
        intent.putExtra(StaticData.TIME_TYPE,timeType);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_list);
        ButterKnife.bind(this);
        setHeight(back,tIncome,null);
        initView();
    }

    private void initView(){
        storeid=getIntent().getStringExtra(StaticData.STORE_ID);
        date=getIntent().getStringExtra(StaticData.DATE);
        timeType=getIntent().getStringExtra(StaticData.TIME_TYPE);
        if(TextUtils.equals("1",timeType)){
            tIncome.setText("本月收入");
        }else if(TextUtils.equals("2",timeType)){
            tIncome.setText("历史收入");
        }else {
            tIncome.setText("推荐收入");
        }
        dateTv.setText(date+" 总收入");
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        incomeAdapter = new IncomeAdapter();
        incomeAdapter.setItemClickListener(this);
        incomeRv.setAdapter(incomeAdapter);
        if(TextUtils.equals("3",timeType)){
            dayIncomePresenter.getRdDayIncome("1",date);
        }else {
            dayIncomePresenter.getDayIncome("1", storeid, date);
        }
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(TextUtils.equals("3",timeType)){
                dayIncomePresenter.getRdDayIncome("1",date);
            }else {
                dayIncomePresenter.getDayIncome("0", storeid, date);
            }
        }

        @Override
        public void onLoadMore() {
            if(TextUtils.equals("3",timeType)){
                dayIncomePresenter.getMoreRdDayIncome(date);
            }else {
                dayIncomePresenter.getMoreDayIncome(storeid, date);
            }
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
    public void setPresenter(BillContract.DayIncomePresenter presenter) {

    }


    @Override
    public void renderDayIncome(BillInfo billInfo) {
        refreshLayout.stopRefresh();
        if (billInfo != null) {
            cashIncome.setText("现金" + billInfo.getAllmoney() + "元");
            energyIncome.setText("能量" + billInfo.getAllpower() + "个");
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
                    refreshLayout.setCanLoadMore(true);
                } else {
                    refreshLayout.setCanLoadMore(false);
                }
                incomeAdapter.setData(inComeInfos);
            }
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
    public void goIncomeDetail(String id) {
        if(!TextUtils.equals("3",timeType)) {
            IncomeDetailActivity.start(this, id);
        }
    }
}
