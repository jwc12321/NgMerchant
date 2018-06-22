package com.nenggou.slsm.cash.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.cash.CashContract;
import com.nenggou.slsm.cash.CashModule;
import com.nenggou.slsm.cash.DaggerCashComponent;
import com.nenggou.slsm.cash.adapter.IncomeListAdapter;
import com.nenggou.slsm.cash.presenter.CashListPresenter;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.widget.list.BaseListFragment;
import com.nenggou.slsm.data.entity.CashDetailInfo;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/6/22.
 * 现金收入
 */

public class IncomeFragment extends BaseListFragment<CashDetailInfo> implements CashContract.CashListView, HeaderViewLayout.OnRefreshListener {

    @Inject
    CashListPresenter cashListPresenter;
    private IncomeListAdapter incomeListAdapter;

    public static IncomeFragment newInstance() {
        IncomeFragment fragment = new IncomeFragment();
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
    public RecyclerView.Adapter initAdapter(List<CashDetailInfo> list) {
        incomeListAdapter = new IncomeListAdapter("0");
        incomeListAdapter.setData(list);
        return incomeListAdapter;
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (cashListPresenter != null) {
                    cashListPresenter.getCashList("1", "0");
                }
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cashListPresenter != null && getUserVisibleHint()) {
            cashListPresenter.getCashList("1", "0");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMoreLoadable(true);
    }

    @Override
    public void onRefresh() {
        cashListPresenter.getCashList("0", "0");
    }

    @Override
    public void setPresenter(CashContract.CashListPresenter presenter) {
    }

    @Override
    public void onLoadMore() {
        cashListPresenter.getMoreCashList("0");
    }

    @Override
    protected void initializeInjector() {
        DaggerCashComponent.builder()
                .applicationComponent(getApplicationComponent())
                .cashModule(new CashModule(this))
                .build()
                .inject(this);
    }
}
