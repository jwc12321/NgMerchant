package com.nenggou.slsm.receipt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.viewpagecards.CardPagerAdapter;
import com.nenggou.slsm.common.widget.viewpagecards.ShadowTransformer;
import com.nenggou.slsm.data.entity.ReceivablesInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/19.
 */

public class ReceiptFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private CardPagerAdapter cardPagerAdapter;
    private ShadowTransformer mCardShadowTransformer;

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
        cardPagerAdapter = new CardPagerAdapter();
        cardPagerAdapter.addCardItem(new ReceivablesInfo());
        cardPagerAdapter.addCardItem(new ReceivablesInfo());
        cardPagerAdapter.addCardItem(new ReceivablesInfo());
        cardPagerAdapter.addCardItem(new ReceivablesInfo());
        mCardShadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
        mCardShadowTransformer.enableScaling(true);
        viewPager.setAdapter(cardPagerAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
