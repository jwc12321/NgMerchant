package com.nenggou.slsm.energy.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.bill.ui.IncomeDetailActivity;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.widget.list.BaseListFragment;
import com.nenggou.slsm.data.entity.EnergyDetailInfo;
import com.nenggou.slsm.energy.DaggerEnergyComponent;
import com.nenggou.slsm.energy.EnergyContract;
import com.nenggou.slsm.energy.EnergyModule;
import com.nenggou.slsm.energy.adapter.EnergyItemAdapter;
import com.nenggou.slsm.energy.presenter.EnergyListPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JWC on 2018/6/23.
 */

public class OutEnergyFragment extends BaseListFragment<EnergyDetailInfo> implements EnergyContract.EnergyListView, HeaderViewLayout.OnRefreshListener,EnergyItemAdapter.ItemClickListener {

    @Inject
    EnergyListPresenter energyListPresenter;
    private EnergyItemAdapter energyItemAdapter;


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
        setMoreLoadable(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (energyListPresenter != null && getUserVisibleHint()) {
            energyListPresenter.getEnergyList("1", "3");
        }
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<EnergyDetailInfo> list) {
        energyItemAdapter = new EnergyItemAdapter(getActivity(), "3");
        energyItemAdapter.setItemClickListener(this);
        energyItemAdapter.setData(list);
        return energyItemAdapter;
    }

    private boolean isFirstLoad = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                if (energyListPresenter != null) {
                    energyListPresenter.getEnergyList("1", "3");
                }
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onRefresh() {
        energyListPresenter.getEnergyList("0", "3");
    }

    @Override
    public void onLoadMore() {
        energyListPresenter.getMoreEnergyList("3");
    }

    @Override
    protected void initializeInjector() {
        DaggerEnergyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .energyModule(new EnergyModule(this))
                .build().inject(this);
    }

    @Override
    public void setPresenter(EnergyContract.EnergyListPresenter presenter) {

    }

    @Override
    public void backEnergy(String sum, String proportion) {
        if(outBackEnergyListener!=null){
            outBackEnergyListener.outBackEnergySum(sum,proportion);
        }
    }

    @Override
    public void goIncomeDetail(String id) {
        IncomeDetailActivity.start(getActivity(),id);
    }

    public interface OutBackEnergyListener {
        void outBackEnergySum(String sum, String proportion);
    }

    private OutBackEnergyListener outBackEnergyListener;

    public void setOutBackEnergyListener(OutBackEnergyListener outBackEnergyListener) {
        this.outBackEnergyListener = outBackEnergyListener;
    }
}
