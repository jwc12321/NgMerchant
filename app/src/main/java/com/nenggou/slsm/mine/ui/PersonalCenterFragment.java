package com.nenggou.slsm.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.address.ui.AddressTelActivity;
import com.nenggou.slsm.cash.ui.CashActivity;
import com.nenggou.slsm.evaluate.ui.AllEvaluationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/19.
 */

public class PersonalCenterFragment extends BaseFragment {
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.item_cash)
    RelativeLayout itemCash;
    @BindView(R.id.item_energy)
    RelativeLayout itemEnergy;
    @BindView(R.id.item_address)
    RelativeLayout itemAddress;
    @BindView(R.id.item_feedback)
    RelativeLayout itemFeedback;

    public PersonalCenterFragment() {
    }

    public static PersonalCenterFragment newInstance() {
        PersonalCenterFragment receiptFragment = new PersonalCenterFragment();
        return receiptFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_persional_center, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null, null, setting);
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

    @OnClick({R.id.item_cash, R.id.item_energy, R.id.item_address, R.id.item_feedback})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_cash: //现金
                CashActivity.start(getActivity());
                break;
            case R.id.item_energy://能量
                break;
            case R.id.item_address://地址电话
                AddressTelActivity.start(getActivity());
                break;
            case R.id.item_feedback://意见反馈
                break;
            default:
        }
    }
}
