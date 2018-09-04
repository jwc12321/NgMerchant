package com.nenggou.slsm.financing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.financing.adapter.FinaningOrderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/9/4.
 * 持有中的订单
 */

public class ReimbursementFragment extends BaseFragment implements FinaningOrderAdapter.ItemClickListener {
    @BindView(R.id.order_rv)
    RecyclerView orderRv;
    @BindView(R.id.go_investment)
    Button goInvestment;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    private FinaningOrderAdapter finaningOrderAdapter;
    private String firstIn = "0";
    private boolean isFirstLoad = true;

    public static ReimbursementFragment newInstance() {
        ReimbursementFragment fragment = new ReimbursementFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_reimbursement, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        addAdapter();
    }

    private void addAdapter() {
        finaningOrderAdapter = new FinaningOrderAdapter();
        finaningOrderAdapter.setItemClickListener(this);
        orderRv.setAdapter(finaningOrderAdapter);
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };


    @Override
    public void onResume() {
        super.onResume();
    }

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
