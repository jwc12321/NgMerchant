package com.nenggou.slsm.financing.ui;

import android.app.Activity;
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
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.entity.TurnOutRecord;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.adapter.TurnOutRecordAdapter;
import com.nenggou.slsm.financing.presenter.TurnOutRecordPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/6.
 */

public class TurnOutRecordActivity extends BaseActivity implements FinancingContract.TurnOutRecordView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.screen)
    TextView screen;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private TurnOutRecordAdapter turnOutRecordAdapter;

    @Inject
    TurnOutRecordPresenter turnOutRecordPresenter;

    private static final int REQUEST_SELECT_WHAT = 102;
    private String selectWhat = "0";

    public static void start(Context context) {
        Intent intent = new Intent(context, TurnOutRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_out_record);
        ButterKnife.bind(this);
        setHeight(back, title, screen);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        turnOutRecordAdapter = new TurnOutRecordAdapter();
        recordRv.setAdapter(turnOutRecordAdapter);
        turnOutRecordPresenter.getTurnOutRecord("1");
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            turnOutRecordPresenter.getTurnOutRecord("0");
        }

        @Override
        public void onLoadMore() {
            turnOutRecordPresenter.getMoreTurnOutRecord();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.screen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.screen:
                Intent intent = new Intent(this, TurnOutScreenActivity.class);
                intent.putExtra(StaticData.SELECT_WHAT, selectWhat);
                startActivityForResult(intent, REQUEST_SELECT_WHAT);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_WHAT:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        selectWhat = bundle.getString(StaticData.SELECT_WHAT);
                    }
                    break;

                default:
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerFinancingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .financingModule(new FinancingModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void renderRecords(TurnOutRecord turnOutRecord) {
        refreshLayout.stopRefresh();
        if (turnOutRecord != null && turnOutRecord.getTurnOutRecordItems() != null && turnOutRecord.getTurnOutRecordItems().size() > 0) {
            recordRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            turnOutRecordAdapter.setData(turnOutRecord.getTurnOutRecordItems());

        } else {
            recordRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
            turnOutRecordAdapter.setData(null);
        }
    }

    @Override
    public void renderMoreRecords(TurnOutRecord turnOutRecord) {
        refreshLayout.stopRefresh();
        if (turnOutRecord != null && turnOutRecord.getTurnOutRecordItems() != null && turnOutRecord.getTurnOutRecordItems().size() > 0) {
            refreshLayout.setCanLoadMore(true);
            turnOutRecordAdapter.addMore(turnOutRecord.getTurnOutRecordItems());
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void setPresenter(FinancingContract.TurnOutRecordPresenter presenter) {

    }
}
