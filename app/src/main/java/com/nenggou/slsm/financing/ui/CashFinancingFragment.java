package com.nenggou.slsm.financing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.widget.list.BaseListFragment;
import com.nenggou.slsm.data.entity.FinancingItemInfo;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.adapter.FinancingTypeItemAdapter;
import com.nenggou.slsm.financing.presenter.FinancingListPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/9/4.
 */

public class CashFinancingFragment extends BaseListFragment<FinancingItemInfo> implements FinancingContract.FinancindListView, HeaderViewLayout.OnRefreshListener, FinancingTypeItemAdapter.ItemClickListener{

    @Inject
    FinancingListPresenter financingListPresenter;
    private FinancingTypeItemAdapter financingTypeItemAdapter;
    public static CashFinancingFragment newInstance() {
        CashFinancingFragment fragment = new CashFinancingFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean isFirstLoad = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (financingListPresenter != null) {
                    financingListPresenter.getFinancingInfos("1");
                }
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(FinancingContract.FinancingListPresenter presenter) {

    }

    @Override
    public RecyclerView.Adapter initAdapter(List<FinancingItemInfo> list) {
        financingTypeItemAdapter=new FinancingTypeItemAdapter();
        financingTypeItemAdapter.setItemClickListener(this);
        financingTypeItemAdapter.setData(list);
        return financingTypeItemAdapter;
    }

    @Override
    public void goNovice(FinancingItemInfo financingItemInfo) {

    }

    @Override
    public void onRefresh() {
        financingListPresenter.getFinancingInfos("0");
    }

    @Override
    public void onLoadMore() {
        financingListPresenter.getMoreFinancinInfos();
    }

    @Override
    protected void initializeInjector() {
        DaggerFinancingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .financingModule(new FinancingModule(this))
                .build()
                .inject(this);
    }
}
