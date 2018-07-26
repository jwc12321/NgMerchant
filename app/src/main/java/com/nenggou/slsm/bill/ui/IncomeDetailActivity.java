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
    @BindView(R.id.first_info)
    TextView firstInfo;
    @BindView(R.id.first_numbe)
    TextView firstNumbe;
    @BindView(R.id.first_ll)
    LinearLayout firstLl;
    @BindView(R.id.second_info)
    TextView secondInfo;
    @BindView(R.id.second_numbe)
    TextView secondNumbe;
    @BindView(R.id.second_ll)
    LinearLayout secondLl;
    @BindView(R.id.third_info)
    TextView thirdInfo;
    @BindView(R.id.third_numbe)
    TextView thirdNumbe;
    @BindView(R.id.third_ll)
    LinearLayout thirdLl;
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
    @BindView(R.id.money_ll)
    LinearLayout moneyLl;
    @BindView(R.id.service_ll)
    LinearLayout serviceLl;
    @BindView(R.id.service_one_name)
    TextView serviceOneName;
    @BindView(R.id.service_one_info)
    TextView serviceOneInfo;
    @BindView(R.id.service_one_number)
    TextView serviceOneNumber;
    @BindView(R.id.service_one_ll)
    LinearLayout serviceOneLl;
    @BindView(R.id.service_rl)
    RelativeLayout serviceRl;
    @Inject
    IncomeDetailPresenter incomeDetailPresenter;

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
            boolean powerZero = false;
            boolean cashZero = false;
            boolean quanZero = false;
            if (TextUtils.equals("0.00", incomeDetailInfo.getPower())) {
                powerZero = true;
                zeroTotal++;
            }
            if (TextUtils.equals("0.00", incomeDetailInfo.getPrice())) {
                cashZero = true;
                zeroTotal++;
            }
            if (TextUtils.equals("0.00", incomeDetailInfo.getQuannum())) {
                quanZero = true;
                zeroTotal++;
            }
            if (zeroTotal == 0) {
                firstInfo.setText("能量");
                firstNumbe.setText(incomeDetailInfo.getPower() + "能量");
                secondInfo.setText("现金");
                secondNumbe.setText("¥" + incomeDetailInfo.getPrice());
                thirdInfo.setText("优惠券");
                thirdNumbe.setText("¥" + incomeDetailInfo.getQuannum());
            } else if (zeroTotal == 1) {
                firstLl.setVisibility(View.VISIBLE);
                secondLl.setVisibility(View.INVISIBLE);
                thirdLl.setVisibility(View.VISIBLE);
                if (powerZero) {
                    firstInfo.setText("现金");
                    firstNumbe.setText("¥" + incomeDetailInfo.getPrice());
                    thirdInfo.setText("优惠券");
                    thirdNumbe.setText("¥" + incomeDetailInfo.getQuannum());
                } else if (!cashZero) {
                    firstInfo.setText("能量");
                    firstNumbe.setText(incomeDetailInfo.getPower() + "能量");
                    thirdInfo.setText("优惠券");
                    thirdNumbe.setText("¥" + incomeDetailInfo.getQuannum());
                } else if (!quanZero) {
                    firstInfo.setText("能量");
                    firstNumbe.setText(incomeDetailInfo.getPower() + "能量");
                    thirdInfo.setText("现金");
                    thirdNumbe.setText("¥" + incomeDetailInfo.getPrice());
                }
            } else if (zeroTotal == 2) {
                firstLl.setVisibility(View.INVISIBLE);
                secondLl.setVisibility(View.VISIBLE);
                thirdLl.setVisibility(View.INVISIBLE);
                if (!powerZero) {
                    secondInfo.setText("能量");
                    secondNumbe.setText(incomeDetailInfo.getPower() + "能量");
                } else if (!cashZero) {
                    secondInfo.setText("现金");
                    secondNumbe.setText("¥" + incomeDetailInfo.getPrice());
                } else if (!quanZero) {
                    secondInfo.setText("优惠券");
                    secondNumbe.setText("¥" + incomeDetailInfo.getQuannum());
                }
            } else {
                moneyLl.setVisibility(View.GONE);
            }
            priceDecimal = new BigDecimal(incomeDetailInfo.getAprice()).setScale(2, BigDecimal.ROUND_DOWN);
            energyDecimal = new BigDecimal(incomeDetailInfo.getApower()).setScale(2, BigDecimal.ROUND_DOWN);
            serviceCPrice.setText("¥" + priceDecimal.add(energyDecimal).toString());
            serviceCProportion.setText("(现金"+incomeDetailInfo.getScharge()+"%+能量"+incomeDetailInfo.getScharge()+"%)");
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
            int serviceZero=0;
            boolean scPrice=false;
            boolean isBpower=false;

            if (TextUtils.equals("0.00", serviceCPrice.toString())) {
                scPrice=true;
                serviceZero++;

            }
            if (TextUtils.equals("0.00", incomeDetailInfo.getBpower())
                    || TextUtils.equals("0.0", incomeDetailInfo.getBpower())
                    || TextUtils.equals("0", incomeDetailInfo.getBpower())) {
                serviceZero++;
                isBpower=true;
            }
            if(serviceZero==0){
                serviceOneLl.setVisibility(View.GONE);
            }else if(serviceZero==1){
                serviceLl.setVisibility(View.GONE);
                serviceOneLl.setVisibility(View.VISIBLE);
                if(!scPrice){
                    serviceOneName.setText("服务费");
                    serviceOneInfo.setText("(现金"+incomeDetailInfo.getScharge()+"%+能量"+incomeDetailInfo.getScharge()+"%)");
                    serviceOneNumber.setText("¥" + priceDecimal.add(energyDecimal).toString());
                }else if(!isBpower){
                    serviceOneName.setText("补贴能量支出");
                    serviceOneInfo.setText("能量 : 现金 = 1 : " + intercept);
                    serviceOneNumber.setText(incomeDetailInfo.getBpower() + "能量");
                }
            }else {
                serviceRl.setVisibility(View.GONE);
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
