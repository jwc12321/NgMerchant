package com.nenggou.slsm.receipt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.ui.MonthIncomeActivity;
import com.nenggou.slsm.common.widget.viewpagecards.CardPagerAdapter;
import com.nenggou.slsm.common.widget.viewpagecards.ShadowTransformer;
import com.nenggou.slsm.data.RemoteDataException;
import com.nenggou.slsm.data.entity.AppstoreInfo;
import com.nenggou.slsm.receipt.DaggerReceiptComponent;
import com.nenggou.slsm.receipt.ReceiptContract;
import com.nenggou.slsm.receipt.ReceiptModule;
import com.nenggou.slsm.receipt.presenter.ReceiptPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/19.
 */

public class ReceiptFragment extends BaseFragment implements ReceiptContract.ReceiptView, CardPagerAdapter.ItemClickListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private CardPagerAdapter cardPagerAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Inject
    ReceiptPresenter receiptPresenter;
    private String refreshType = "2";  //1：刷新 2：不刷新

    public ReceiptFragment() {
    }

    public static ReceiptFragment newInstance() {
        ReceiptFragment receiptFragment = new ReceiptFragment();
        return receiptFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_receipt, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    private void initView() {
        receiptPresenter.getAppstoreInfos();
    }


    private boolean isFirstLoad = true;

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
        DaggerReceiptComponent.builder()
                .applicationComponent(getApplicationComponent())
                .receiptModule(new ReceiptModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("1", refreshType)) {
            refreshType = "2";
            receiptPresenter.getAppstoreInfos();
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException) {
            if (((RemoteDataException) e).isAuthFailed()) {
                refreshType = "1";
            }
        }
        super.showError(e);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(ReceiptContract.ReceiptPresenter presenter) {

    }

    @Override
    public void renderAppstoreInfos(List<AppstoreInfo> appstoreInfos) {
        if (appstoreInfos != null && appstoreInfos.size() > 0) {
            cardPagerAdapter = new CardPagerAdapter(appstoreInfos);
            cardPagerAdapter.setOnItemClickListener(this);
            mCardShadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
            if (appstoreInfos.size() != 1) {
                mCardShadowTransformer.enableScaling(true);
            }
            viewPager.setAdapter(cardPagerAdapter);
            viewPager.setPageTransformer(false, mCardShadowTransformer);
            viewPager.setOffscreenPageLimit(appstoreInfos.size() - 1);
        }
    }

    @Override
    public void goMonthIncome(String storeid) {
        MonthIncomeActivity.start(getActivity(),storeid);
    }

    @Override
    public void goBuyerEvaluate(String storeid) {

    }
}
