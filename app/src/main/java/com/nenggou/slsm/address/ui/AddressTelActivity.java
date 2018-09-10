package com.nenggou.slsm.address.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.address.AddressContract;
import com.nenggou.slsm.address.AddressModule;
import com.nenggou.slsm.address.DaggerAddressComponent;
import com.nenggou.slsm.address.adapter.AddressTelAdapter;
import com.nenggou.slsm.address.presenter.AddressPresenter;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.entity.AppstoreInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/23.
 */

public class AddressTelActivity extends BaseActivity implements AddressContract.AddressView{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.address_rv)
    RecyclerView addressRv;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.empty_view)
    NestedScrollView emptyView;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;

    @Inject
    AddressPresenter addressPresenter;

    private AddressTelAdapter addressTelAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressTelActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresstel_list);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        addressTelAdapter=new AddressTelAdapter(this);
        addressRv.setAdapter(addressTelAdapter);
        addressPresenter.getAddressInfos("1");
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            addressPresenter.getAddressInfos("0");
        }

        @Override
        public void onLoadMore() {

        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerAddressComponent.builder()
                .applicationComponent(getApplicationComponent())
                .addressModule(new AddressModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(AddressContract.AddressPresenter presenter) {

    }

    @Override
    public void renderAddressInfos(List<AppstoreInfo> appstoreInfos) {
        refreshLayout.stopRefresh();
        addressTelAdapter.setData(appstoreInfos);
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
