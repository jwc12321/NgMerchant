package com.nenggou.slsm.ranking.ui;

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
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.entity.RIncomeInfo;
import com.nenggou.slsm.ranking.DaggerRankingComponent;
import com.nenggou.slsm.ranking.RankingContract;
import com.nenggou.slsm.ranking.RankingModule;
import com.nenggou.slsm.ranking.adapter.RIncomeAdapter;
import com.nenggou.slsm.ranking.presenter.RIncomePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/27.
 */

public class RIncomeListActivity extends BaseActivity implements RankingContract.RIncomeView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.ri_rv)
    RecyclerView riRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private RIncomeAdapter rIncomeAdapter;

    private String uid;
    private String timeType;
    private String startTime;

    @Inject
    RIncomePresenter rIncomePresenter;

    public static void start(Context context, String uid, String type, String starttime) {
        Intent intent = new Intent(context, RIncomeListActivity.class);
        intent.putExtra(StaticData.UID, uid);
        intent.putExtra(StaticData.TIME_TYPE,type);
        intent.putExtra(StaticData.START_TIME,starttime);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_income_list);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }


    private void initView() {
        uid = getIntent().getStringExtra(StaticData.UID);
        timeType = getIntent().getStringExtra(StaticData.TIME_TYPE);
        startTime = getIntent().getStringExtra(StaticData.START_TIME);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        addAdapter();
        rIncomePresenter.getRIncomeList("1", uid, timeType, startTime);
    }

    private void addAdapter() {
        rIncomeAdapter = new RIncomeAdapter();
        riRv.setAdapter(rIncomeAdapter);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            rIncomePresenter.getRIncomeList("0",uid,timeType,startTime);
        }

        @Override
        public void onLoadMore() {
            rIncomePresenter.getMoreRIncomeList(uid,timeType,startTime);
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
    protected void initializeInjector() {
        DaggerRankingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .rankingModule(new RankingModule(this))
                .build()
                .inject(this);
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
    public void setPresenter(RankingContract.RIncomePresenter presenter) {

    }

    @Override
    public void renderRIncomeList(RIncomeInfo rIncomeInfo) {
        refreshLayout.stopRefresh();
        if (rIncomeInfo != null && rIncomeInfo.getrIncomeItemInfos() != null && rIncomeInfo.getrIncomeItemInfos().size() > 0) {
            riRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            rIncomeAdapter.setData(rIncomeInfo.getrIncomeItemInfos());
        } else {
            riRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void renderMoreRIncomeList(RIncomeInfo rIncomeInfo) {
        refreshLayout.stopRefresh();
        if (rIncomeInfo != null && rIncomeInfo.getrIncomeItemInfos() != null && rIncomeInfo.getrIncomeItemInfos().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            rIncomeAdapter.addMore(rIncomeInfo.getrIncomeItemInfos());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }
}
