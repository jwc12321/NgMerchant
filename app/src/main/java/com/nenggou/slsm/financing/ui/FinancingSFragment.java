package com.nenggou.slsm.financing.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.viewpage.ViewPagerSlide;
import com.nenggou.slsm.financing.adapter.ItemPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by JWC on 2018/9/4.
 */

public class FinancingSFragment extends BaseFragment {
    @BindView(R.id.financing_wallet)
    ImageView financingWallet;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.financing_order)
    ImageView financingOrder;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.energy_financing)
    Button energyFinancing;
    @BindView(R.id.cash_financing)
    Button cashFinancing;
    @BindView(R.id.viewPager)
    ViewPagerSlide viewPager;


    private Button[] buttons;
    private BaseFragment[] fragments;
    private ItemPagerAdapter itemPagerAdapter;

    public static FinancingSFragment newInstance() {
        FinancingSFragment fragment = new FinancingSFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_financings, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(financingWallet,title,financingOrder);
    }

    private boolean isFirstLoad = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                initView();
            }
        }
    }

    private void initView() {
        fragments = new BaseFragment[2];
        fragments[0] = EnergyFinancingFragment.newInstance();
        fragments[1] = CashFinancingFragment.newInstance();
        buttons = new Button[2];
        buttons[0] = energyFinancing;
        buttons[1] = cashFinancing;
        for (Button button : buttons) {
            button.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(1);
        itemPagerAdapter = new ItemPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(itemPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(0);
        buttons[0].setSelected(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < buttons.length; i++) {
                if (v == buttons[i]) {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setSelected(position == i);
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.financing_wallet,R.id.financing_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.financing_wallet:
                FinancingWalletActivity.start(getActivity());
                break;
            case R.id.financing_order:
                FinancingOrderActivity.start(getActivity());
                break;
            default:
        }
    }
}
