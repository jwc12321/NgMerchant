package com.nenggou.slsm.common.widget.viewpagecards;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.ReceivablesInfo;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<ReceivablesInfo> receivablesInfos;
    private float mBaseElevation;

    public CardPagerAdapter() {
        receivablesInfos = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(ReceivablesInfo receivablesInfo) {
        mViews.add(null);
        receivablesInfos.add(receivablesInfo);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return receivablesInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter_rp_code, container, false);
        container.addView(view);
        bind(receivablesInfos.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(ReceivablesInfo receivablesInfo, View view) {
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
