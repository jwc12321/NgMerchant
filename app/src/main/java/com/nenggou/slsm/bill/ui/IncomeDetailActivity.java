package com.nenggou.slsm.bill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.BillContract;
import com.nenggou.slsm.bill.BillModule;
import com.nenggou.slsm.bill.DaggerBillComponent;
import com.nenggou.slsm.bill.presenter.IncomeDetailPresenter;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.IncomeDetailInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 */

public class IncomeDetailActivity extends BaseActivity implements BillContract.IncomeDetailView {


    @Inject
    IncomeDetailPresenter incomeDetailPresenter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.photo)
    RoundedImageView photo;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.trading_state)
    TextView tradingState;
    @BindView(R.id.payment_method)
    TextView paymentMethod;
    @BindView(R.id.go_arrow)
    ImageView goArrow;
    @BindView(R.id.cash_store)
    TextView cashStore;
    @BindView(R.id.created_at)
    TextView createdAt;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.merchant_order_number)
    TextView merchantOrderNumber;
    @BindView(R.id.go_recode_rl)
    RelativeLayout goRecodeRl;
    private String billId;
    private String uid;
    private String shopName;

    public static void start(Context context, String billId) {
        Intent intent = new Intent(context, IncomeDetailActivity.class);
        intent.putExtra(StaticData.BILL_ID, billId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        billId = getIntent().getStringExtra(StaticData.BILL_ID);
        incomeDetailPresenter.getIncomeDetail(billId);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BillContract.IncomeDetailPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerBillComponent.builder()
                .applicationComponent(getApplicationComponent())
                .billModule(new BillModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderIncomeDetail(IncomeDetailInfo incomeDetailInfo) {
        if (incomeDetailInfo != null) {
            uid=incomeDetailInfo.getUid();
            shopName=incomeDetailInfo.getTitle();
            GlideHelper.load(this, incomeDetailInfo.getAvatar(), R.mipmap.app_icon, photo);
            businessName.setText(incomeDetailInfo.getNickname());
            price.setText(incomeDetailInfo.getAllprice());
            cashStore.setText(incomeDetailInfo.getTitle());
            createdAt.setText(FormatUtil.formatDateByLine(incomeDetailInfo.getCreatedAt()));
            orderNumber.setText(incomeDetailInfo.getOrderno());
            merchantOrderNumber.setText(incomeDetailInfo.getOrderno());
        }
    }

    @OnClick({R.id.back,R.id.go_recode_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.go_recode_rl:
                if(!TextUtils.isEmpty(uid)){
                    IntercourseRecordActivity.start(this,uid,shopName);
                }
                break;
            default:
        }
    }
}
