package com.nenggou.slsm.mine.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.address.ui.AddressTelActivity;
import com.nenggou.slsm.cash.ui.CashActivity;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.common.unit.PersionAppPreferences;
import com.nenggou.slsm.data.entity.PersionInfoResponse;
import com.nenggou.slsm.energy.ui.EnergyActivity;
import com.nenggou.slsm.evaluate.ui.AllEvaluationActivity;
import com.nenggou.slsm.feedback.ui.FeedBackActivity;
import com.nenggou.slsm.login.ui.LoginActivity;
import com.nenggou.slsm.setting.ui.SettingActivity;

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

    private String phoneNumber;
    private String firstIn = "0";

    private PersionAppPreferences persionAppPreferences;
    private String persionInfoStr;
    private PersionInfoResponse persionInfoResponse;
    private Gson gson;

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
        persionAppPreferences = new PersionAppPreferences(getActivity());
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
                initVeiw();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initVeiw();
    }


    private void initVeiw() {
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", firstIn)) {
            persionInfoStr = persionAppPreferences.getPersionInfo();
            gson = new Gson();
            if (!TextUtils.isEmpty(persionInfoStr)) {
                persionInfoResponse = gson.fromJson(persionInfoStr, PersionInfoResponse.class);
                GlideHelper.load(this, persionInfoResponse.getAvatar(), R.mipmap.app_icon, headPhoto);
                userName.setText(persionInfoResponse.getName());
                phoneNumber = persionInfoResponse.getTel();
                firstIn = "1";
            } else {
                LoginActivity.start(getActivity());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.item_cash, R.id.item_energy, R.id.item_address, R.id.item_feedback, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                firstIn="0";
                SettingActivity.start(getActivity(),phoneNumber);
                break;
            case R.id.item_cash: //现金
                CashActivity.start(getActivity());
                break;
            case R.id.item_energy://能量
                EnergyActivity.start(getActivity());
                break;
            case R.id.item_address://地址电话
                AddressTelActivity.start(getActivity());
                break;
            case R.id.item_feedback://意见反馈
                FeedBackActivity.start(getActivity());
                break;
            default:
        }
    }
}
