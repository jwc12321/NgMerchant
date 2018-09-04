package com.nenggou.slsm.financing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.RemoteDataException;
import com.nenggou.slsm.data.entity.FinancingInfo;
import com.nenggou.slsm.data.entity.FinancingItemInfo;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.adapter.FinancingItemAdapter;
import com.nenggou.slsm.financing.presenter.FinancingListPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/24.
 */

public class FinancingFragment extends BaseFragment implements FinancingContract.FinancindListView, FinancingItemAdapter.ItemClickListener {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.financing_rv)
    RecyclerView financingRv;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    FinancingListPresenter financingListPresenter;
    private FinancingItemAdapter financingItemAdapter;
    private String firstIn = "1";

    public static FinancingFragment newInstance() {
        FinancingFragment financingFragment = new FinancingFragment();
        return financingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_financing, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null, title, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        financingItemAdapter = new FinancingItemAdapter();
        financingItemAdapter.setItemClickListener(this);
        financingRv.setAdapter(financingItemAdapter);
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (financingListPresenter != null) {
                    financingListPresenter.getFinancingInfos("1");
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", firstIn)) {
            if (financingListPresenter != null) {
                financingListPresenter.getFinancingInfos("1");
            }
            firstIn = "1";
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException && ((RemoteDataException) e).isAuthFailed()) {
            firstIn = "0";
        }
        super.showError(e);
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            financingListPresenter.getFinancingInfos("0");
        }

        @Override
        public void onLoadMore() {
            financingListPresenter.getMoreFinancinInfos();
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

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
    public void setPresenter(FinancingContract.FinancingListPresenter presenter) {

    }

    @Override
    public void render(List<FinancingItemInfo> financingItemInfos) {
        refreshLayout.stopRefresh();
        if (financingItemInfos != null && financingItemInfos.size() > 0) {
            financingRv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshLayout.setCanLoadMore(true);
        } else {
            financingRv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            refreshLayout.setCanLoadMore(false);
            financingItemAdapter.setData(financingItemInfos);
        }
    }

    @Override
    public void renderMore(List<FinancingItemInfo> financingItemInfos) {
        refreshLayout.stopRefresh();
        if (financingItemInfos != null && financingItemInfos.size() > 0) {
            financingItemAdapter.addMore(financingItemInfos);
            refreshLayout.setCanLoadMore(true);
        } else {
            refreshLayout.setCanLoadMore(false);
        }
    }


    @Override
    public void goNovice(FinancingItemInfo financingItemInfo) {
        NoviceActivity.start(getActivity(), financingItemInfo);
    }
}
