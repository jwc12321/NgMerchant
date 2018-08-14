package com.nenggou.slsm.referee.ui;

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
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.entity.RdList;
import com.nenggou.slsm.ranking.ui.RIncomeListActivity;
import com.nenggou.slsm.referee.DaggerRefereeComponent;
import com.nenggou.slsm.referee.RefereeContract;
import com.nenggou.slsm.referee.RefereeModule;
import com.nenggou.slsm.referee.adapter.RdItemAdapter;
import com.nenggou.slsm.referee.presenter.RdListPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/8/14.
 */

public class RdListActivity extends BaseActivity implements RefereeContract.RdListView,RdItemAdapter.ItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.rd_rv)
    RecyclerView rdRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    RdListPresenter rdListPresenter;

    private RdItemAdapter rdItemAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, RdListActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rd_list);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        rdItemAdapter=new RdItemAdapter();
        rdItemAdapter.setItemClickListener(this);
        rdRv.setAdapter(rdItemAdapter);
        rdListPresenter.getRdList("1");

    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            rdListPresenter.getRdList("0");
        }

        @Override
        public void onLoadMore() {
           rdListPresenter.getRdMoreList();
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
        DaggerRefereeComponent.builder()
                .applicationComponent(getApplicationComponent())
                .refereeModule(new RefereeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(RefereeContract.RdListPresenter presenter) {

    }

    @Override
    public void renderRdList(RdList rdList) {
        refreshLayout.stopRefresh();
        if(rdList!=null){
            if(!TextUtils.isEmpty(rdList.getTotal())){
                title.setText("推荐人列表( "+rdList.getTotal()+" )");
            }
            if(rdList.getRdListInfos()!=null&&rdList.getRdListInfos().size()>0){
                rdRv.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                refreshLayout.setCanLoadMore(true);
            }else {
                rdRv.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                refreshLayout.setCanLoadMore(false);
            }
            rdItemAdapter.setData(rdList.getRdListInfos());
        }
    }

    @Override
    public void renderMoreRdList(RdList rdList) {
        refreshLayout.stopRefresh();
        if(rdList!=null){
            if(rdList.getRdListInfos()!=null&&rdList.getRdListInfos().size()>0){
                refreshLayout.setCanLoadMore(true);
                rdItemAdapter.addMore(rdList.getRdListInfos());
            }else {
                refreshLayout.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void goRRecord(String id) {
        RIncomeListActivity.start(this,id,"","","2");
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
