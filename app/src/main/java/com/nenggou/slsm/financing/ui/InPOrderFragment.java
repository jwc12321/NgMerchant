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
import com.nenggou.slsm.data.entity.FcOrderList;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.adapter.FinaningOrderAdapter;
import com.nenggou.slsm.financing.presenter.FcOrderListPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/4.
 * 持有中的订单
 */

public class InPOrderFragment extends BaseFragment implements FinaningOrderAdapter.ItemClickListener,FinancingContract.FcOrderListView {
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
    @Inject
    FcOrderListPresenter fcOrderListPresenter;

    public static InPOrderFragment newInstance() {
        InPOrderFragment fragment = new InPOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_in_p_order, container, false);
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
            fcOrderListPresenter.getFcOrderItemInfos("0","0");
        }

        @Override
        public void onLoadMore() {
            fcOrderListPresenter.getMoreFcOrderItemInfos("0");
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", firstIn)) {
            fcOrderListPresenter.getFcOrderItemInfos("1","0");
            firstIn="1";
        }
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
    protected void initializeInjector() {
        DaggerFinancingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .financingModule(new FinancingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(FinancingContract.FcOrderListPresenter presenter) {

    }

    @Override
    public void render(FcOrderList fcOrderList) {
        refreshLayout.stopRefresh();
        if(fcOrderList!=null&&fcOrderList.getFinaningOrderItemInfos()!=null&&fcOrderList.getFinaningOrderItemInfos().size()>0){
            orderRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
            finaningOrderAdapter.setData(fcOrderList.getFinaningOrderItemInfos());
        }else {
            orderRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
            finaningOrderAdapter.setData(null);
        }
    }

    @Override
    public void renderMore(FcOrderList fcOrderList) {
        refreshLayout.stopRefresh();
        if(fcOrderList!=null&&fcOrderList.getFinaningOrderItemInfos()!=null&&fcOrderList.getFinaningOrderItemInfos().size()>0){
            refreshLayout.setCanLoadMore(true);
            finaningOrderAdapter.addMore(fcOrderList.getFinaningOrderItemInfos());
        }else {
            refreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void returnFcId(String financingId) {
        FinancingOrderDetailActivity.start(getActivity(),financingId);
    }

    @OnClick({R.id.go_investment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_investment:
                getActivity().finish();
                break;
            default:
        }
    }
}
