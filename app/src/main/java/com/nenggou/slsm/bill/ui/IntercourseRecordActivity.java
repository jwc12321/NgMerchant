package com.nenggou.slsm.bill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
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

public class IntercourseRecordActivity extends BaseActivity implements BillContract.IntercourseRecordView {
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
    @Inject
    IntercourseRecordPresenter intercourseRecordPresenter;

    public static void start(Context context, String uid,String businessName) {
        Intent intent = new Intent(context, IntercourseRecordActivity.class);
        intent.putExtra(StaticData.UID, uid);
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
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        addAdapter();
        intercourseRecordPresenter.getIntercourseRecordInfo("1",uid);
    }

    private void addAdapter() {
        intercourseRecordAdapter = new IntercourseRecordAdapter(businessName);
        irRv.setAdapter(intercourseRecordAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            intercourseRecordPresenter.getIntercourseRecordInfo("0",uid);
        }

        @Override
        public void onLoadMore() {
            intercourseRecordPresenter.getMoreIntercourseRecordInfo(uid);
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
}
