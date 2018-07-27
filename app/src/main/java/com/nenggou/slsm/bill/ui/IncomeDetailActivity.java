package com.nenggou.slsm.bill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.math.BigDecimal;

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
    @BindView(R.id.energy_numbe)
    TextView energyNumbe;
    @BindView(R.id.energy_ll)
    LinearLayout energyLl;
    @BindView(R.id.cash_numbe)
    TextView cashNumbe;
    @BindView(R.id.cash_ll)
    LinearLayout cashLl;
    @BindView(R.id.coupon_numbe)
    TextView couponNumbe;
    @BindView(R.id.coupon_ll)
    LinearLayout couponLl;
    @BindView(R.id.money_ll)
    LinearLayout moneyLl;
    @BindView(R.id.service_c_proportion)
    TextView serviceCProportion;
    @BindView(R.id.service_c_price)
    TextView serviceCPrice;
    @BindView(R.id.service_c_p_ll)
    LinearLayout serviceCPLl;
    @BindView(R.id.proportion)
    TextView proportion;
    @BindView(R.id.subsidy_e_out_price)
    TextView subsidyEOutPrice;
    @BindView(R.id.subsidy_e_out_p_ll)
    LinearLayout subsidyEOutPLl;
    @BindView(R.id.service_ll)
    LinearLayout serviceLl;
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
    private String nickName;

    private BigDecimal priceDecimal;//现金
    private BigDecimal energyDecimal;//能量
    private BigDecimal ptDecimal;//兑换比例
    private BigDecimal percentageDecimal;//能量兑换比是200，要除以100才行
    private BigDecimal divisorDecimal;

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
            uid = incomeDetailInfo.getUid();
            shopName = incomeDetailInfo.getTitle();
            GlideHelper.load(this, incomeDetailInfo.getAvatar(), R.mipmap.app_icon, photo);
            nickName = incomeDetailInfo.getNickname();
            businessName.setText(incomeDetailInfo.getNickname());
            price.setText(incomeDetailInfo.getAllprice());
            cashStore.setText(incomeDetailInfo.getTitle());
            createdAt.setText(FormatUtil.formatDateByLine(incomeDetailInfo.getCreatedAt()));
            orderNumber.setText(incomeDetailInfo.getOrderno());
            merchantOrderNumber.setText(incomeDetailInfo.getOrderno());
            if (TextUtils.equals("1", incomeDetailInfo.getPaytype())) {
                paymentMethod.setText("支付宝");
            } else {
                paymentMethod.setText("微信");
            }
            int zeroTotal = 0;
            if (TextUtils.equals("0.00", incomeDetailInfo.getPower())) {
                energyLl.setVisibility(View.GONE);
                zeroTotal++;
            }
            if (TextUtils.equals("0.00", incomeDetailInfo.getPrice())) {
                cashLl.setVisibility(View.GONE);
                zeroTotal++;
            }
            if (TextUtils.equals("0.00", incomeDetailInfo.getQuannum())) {
                couponLl.setVisibility(View.GONE);
                zeroTotal++;
            }
            energyNumbe.setText(incomeDetailInfo.getPower() + "能量");
            cashNumbe.setText("¥" + incomeDetailInfo.getPrice());
            couponNumbe.setText("¥" + incomeDetailInfo.getQuannum());
            if (zeroTotal == 3) {
                moneyLl.setVisibility(View.GONE);
            }

            priceDecimal = new BigDecimal(incomeDetailInfo.getAprice()).setScale(2, BigDecimal.ROUND_DOWN);
            energyDecimal = new BigDecimal(incomeDetailInfo.getApower()).setScale(2, BigDecimal.ROUND_DOWN);
            serviceCPrice.setText("¥" + priceDecimal.add(energyDecimal).toString());
            serviceCProportion.setText("(现金" + incomeDetailInfo.getScharge() + "%+能量" + incomeDetailInfo.getScharge() + "%)");
            ptDecimal = new BigDecimal(incomeDetailInfo.getPowerRate()).setScale(2, BigDecimal.ROUND_DOWN);
            percentageDecimal = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
            divisorDecimal = ptDecimal.divide(percentageDecimal, 2, BigDecimal.ROUND_DOWN);
            String divisorStr = divisorDecimal.toString();
            String lastDiv = divisorStr.substring(divisorStr.length() - 1, divisorStr.length());
            String sLastDiv = divisorStr.substring(divisorStr.length() - 2, divisorStr.length() - 1);
            String intercept;
            if (TextUtils.equals("0", lastDiv) && TextUtils.equals("0", sLastDiv)) {
                intercept = divisorStr.substring(0, divisorStr.length() - 3);
            } else if (TextUtils.equals("0", lastDiv + "0") && !TextUtils.equals("0", sLastDiv + "")) {
                intercept = divisorStr.substring(0, divisorStr.length() - 1);
            } else {
                intercept = divisorStr;
            }
            proportion.setText("能量 : 现金 = 1 : " + intercept);
            subsidyEOutPrice.setText(incomeDetailInfo.getBpower() + "能量");
            int serviceZero = 0;
            if (TextUtils.equals("0.00", serviceCPrice.toString())) {
                serviceCPLl.setVisibility(View.GONE);
                serviceZero++;

            }
            if (TextUtils.equals("0.00", incomeDetailInfo.getBpower())
                    || TextUtils.equals("0.0", incomeDetailInfo.getBpower())
                    || TextUtils.equals("0", incomeDetailInfo.getBpower())) {
                subsidyEOutPLl.setVisibility(View.GONE);
                serviceZero++;
            }
            if (serviceZero == 2) {
                serviceLl.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.back, R.id.go_recode_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.go_recode_rl:
                if (!TextUtils.isEmpty(uid)) {
                    IntercourseRecordActivity.start(this, uid, nickName);
                }
                break;
            default:
        }
    }
}
