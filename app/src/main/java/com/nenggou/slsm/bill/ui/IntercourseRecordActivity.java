package com.nenggou.slsm.bill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.bill.BillModule;
import com.nenggou.slsm.bill.DaggerBillComponent;
import com.nenggou.slsm.bill.adapter.IntercourseRecordAdapter;
import com.nenggou.slsm.bill.presenter.IntercourseRecordPresenter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.entity.IntercourseRecordInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/5/2.
 * 来往记录
 */

public class IntercourseRecordActivity extends BaseActivity implements BillContract.IntercourseRecordView,IntercourseRecordAdapter.ItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.ir_rv)
    RecyclerView irRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private IntercourseRecordAdapter intercourseRecordAdapter;
    private String uid;
    private String businessName;
    private String timeType;
    private String startTime;
    @Inject
    IntercourseRecordPresenter intercourseRecordPresenter;


    public static void start(Context context, String uid,String businessName,String type, String starttime) {
        Intent intent = new Intent(context, IntercourseRecordActivity.class);
        intent.putExtra(StaticData.UID, uid);
        intent.putExtra(StaticData.TIME_TYPE,type);
        intent.putExtra(StaticData.START_TIME,starttime);
        intent.putExtra(StaticData.BUSINESS_NAME,businessName);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercourse_record);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();

    }

    private void initView() {
        uid = getIntent().getStringExtra(StaticData.UID);
        businessName=getIntent().getStringExtra(StaticData.BUSINESS_NAME);
        timeType=getIntent().getStringExtra(StaticData.TIME_TYPE);
        startTime=getIntent().getStringExtra(StaticData.START_TIME);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        addAdapter();
        intercourseRecordPresenter.getIntercourseRecordInfo("1",uid,timeType,startTime);
    }

    private void addAdapter() {
        if(!TextUtils.isEmpty(businessName)) {
            if(businessName.length()==1){
                businessName="*"+businessName;
            }else if(businessName.length()==11){
                businessName="*"+businessName.substring(businessName.length()-4,businessName.length());
            }else {
                businessName="*"+businessName.substring(businessName.length()-1,businessName.length());
            }
        }else {
            businessName="*";
        }
        intercourseRecordAdapter = new IntercourseRecordAdapter(businessName);
        intercourseRecordAdapter.setItemClickListener(this);
        irRv.setAdapter(intercourseRecordAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            intercourseRecordPresenter.getIntercourseRecordInfo("0",uid,timeType,startTime);
        }

        @Override
        public void onLoadMore() {
            intercourseRecordPresenter.getMoreIntercourseRecordInfo(uid,timeType,startTime);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BillContract.IntercourseRecordPresenter presenter) {

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
    public void intercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo) {
        refreshLayout.stopRefresh();
        if (intercourseRecordInfo != null && intercourseRecordInfo.getIrItemInfos() != null && intercourseRecordInfo.getIrItemInfos().size() > 0) {
            irRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            intercourseRecordAdapter.setData(intercourseRecordInfo.getIrItemInfos());
        } else {
            irRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void moreIntercourseRecordInfo(IntercourseRecordInfo intercourseRecordInfo) {
        refreshLayout.stopRefresh();
        if (intercourseRecordInfo != null && intercourseRecordInfo.getIrItemInfos() != null && intercourseRecordInfo.getIrItemInfos().size() > 0) {
            intercourseRecordAdapter.addMore(intercourseRecordInfo.getIrItemInfos());
            refreshLayout.setCanLoadMore(true);
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
        IncomeDetailActivity.start(this,id);
    }
}
