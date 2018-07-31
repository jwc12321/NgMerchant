package com.nenggou.slsm.ranking.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.data.entity.CouponInfo;
import com.nenggou.slsm.ranking.DaggerRankingComponent;
import com.nenggou.slsm.ranking.RankingContract;
import com.nenggou.slsm.ranking.RankingModule;
import com.nenggou.slsm.ranking.adapter.CouponAdapter;
import com.nenggou.slsm.ranking.presenter.CouponPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/31.
 * 优惠券列表
 */

public class CouponListActivity extends BaseActivity implements RankingContract.CouponView,CouponAdapter.ItemClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.coupon_rv)
    RecyclerView couponRv;

    private CouponAdapter couponAdapter;
    @Inject
    CouponPresenter couponPresenter;

    private String uid;

    public static void start(Context context, String uid) {
        Intent intent = new Intent(context, CouponListActivity.class);
        intent.putExtra(StaticData.UID, uid);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        uid=getIntent().getStringExtra(StaticData.UID);
        couponAdapter=new CouponAdapter();
        couponAdapter.setItemClickListener(this);
        couponRv.setAdapter(couponAdapter);
        couponPresenter.getCouponInfos();
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
    @Override
    protected void initializeInjector() {
        DaggerRankingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .rankingModule(new RankingModule(this))
                .build()
                .inject(this);
    }
    @Override
    public void sendOutCoupon(String quanid) {
        couponPresenter.sendOutCoupon(uid,quanid);
    }

    @Override
    public void setPresenter(RankingContract.CouponPresenter presenter) {

    }

    @Override
    public void renderCouponInfos(List<CouponInfo> couponInfos) {
        couponAdapter.setData(couponInfos);
    }

    @Override
    public void sendOutSuccess() {
        showMessage("发送成功");
        couponPresenter.getCouponInfos();
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
