package com.nenggou.slsm.financing.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.data.entity.PayFcOrderInfo;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.presenter.PayFcOrderPresenter;
import com.nenggou.slsm.mainframe.ui.CommonDialogActivity;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/4.
 */

public class PayFinancingOrderActivity extends BaseActivity implements FinancingContract.PayFcOrderView {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.amount_et)
    EditText amountEt;
    @BindView(R.id.reduce_number)
    ImageView reduceNumber;
    @BindView(R.id.increase_number)
    ImageView increaseNumber;
    @BindView(R.id.initial_value)
    TextView initialValue;
    @BindView(R.id.income_statement)
    TextView incomeStatement;
    @BindView(R.id.input_ll)
    LinearLayout inputLl;
    @BindView(R.id.available_type)
    TextView availableType;
    @BindView(R.id.available_item)
    TextView availableItem;
    @BindView(R.id.available_total)
    TextView availableTotal;
    @BindView(R.id.confirm)
    Button confirm;

    private static final int REQUEST_LACK_MONEY = 101;
    private String financingId;
    private String financingType; //0:能量  1:现金
    private String interestRate;//利息
    private String financingCycle;//周期

    @Inject
    PayFcOrderPresenter payFcOrderPresenter;

    private BigDecimal walletBd;//钱包
    private BigDecimal balanceBd;//余额
    private BigDecimal totalBd;//总共
    private BigDecimal interestRateBd;//利息
    private BigDecimal financingCycleBd;//周期
    private BigDecimal percentageBd;//利息要除以100
    private BigDecimal profitBd;//收益金额
    private BigDecimal amountBd;//输入的钱
    private BigDecimal allYearBd;//整年
    private BigDecimal amountRateBd;//输入的加利息
    private BigDecimal restrictpriceBd;//最多能买多少
    private BigDecimal oneBd;//每次相加相减都是1


    boolean amountDouble = true;
    private int digits = 2;
    private static final int UPDATE_AMOUNT = 103;
    private MyHandler mHandler = new MyHandler(this);


    public static void start(Context context, String financingId, String financingType,String interestRate,String financingCycle) {
        Intent intent = new Intent(context, PayFinancingOrderActivity.class);
        intent.putExtra(StaticData.FINANCING_ID, financingId);
        intent.putExtra(StaticData.FINANCING_TYPE, financingType);
        intent.putExtra(StaticData.INTEREST_RATE,interestRate);
        intent.putExtra(StaticData.FINANCING_CYCLE,financingCycle);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_financing_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        financingId = getIntent().getStringExtra(StaticData.FINANCING_ID);
        financingType = getIntent().getStringExtra(StaticData.FINANCING_TYPE);
        interestRate=getIntent().getStringExtra(StaticData.INTEREST_RATE);
        financingCycle=getIntent().getStringExtra(StaticData.FINANCING_CYCLE);
        interestRateBd = new BigDecimal(interestRate).setScale(2, BigDecimal.ROUND_DOWN);
        financingCycleBd = new BigDecimal(financingCycle).setScale(2, BigDecimal.ROUND_DOWN);
        percentageBd = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
        allYearBd = new BigDecimal(365).setScale(2, BigDecimal.ROUND_DOWN);
        editListener();
        payFcOrderPresenter.getPayFcOrderInfo(financingId);
    }

    /**
     * edittext监听
     */
    private void editListener() {
        //监听edittext变化
        amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(amountEt.getText().toString())) {
                    amountDouble = true;
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(UPDATE_AMOUNT, 1000);
                    } else {
                        mHandler.sendEmptyMessageDelayed(UPDATE_AMOUNT, 1000);
                    }
                } else {
                    try {
                        Double db = Double.valueOf(amountEt.getText().toString());
                        amountDouble = true;
                    } catch (Exception e) {
                        amountDouble = false;
                        Toast.makeText(PayFinancingOrderActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                    }
                    if (amountDouble) {
                        limitedDecimal(amountEt.getText().toString(), amountEt);
                        if (mHandler != null) {
                            mHandler.removeCallbacksAndMessages(null);
                            mHandler.sendEmptyMessageDelayed(UPDATE_AMOUNT, 1000);
                        } else {
                            mHandler.sendEmptyMessageDelayed(UPDATE_AMOUNT, 1000);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void limitedDecimal(String s, EditText editText) {
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                s = (String) s.toString().subSequence(0, s.toString().indexOf(".") + digits + 1);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }

    class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case UPDATE_AMOUNT:
                        if (amountDouble) {
                            calculationNumber();
                        }
                        break;
                }
            }
        }
    }

    private void calculationNumber(){
        amountBd = new BigDecimal(amountEt.getText().toString()).setScale(2, BigDecimal.ROUND_DOWN);
        if(amountBd.compareTo(restrictpriceBd)>0){
            showMessage("超过最高出借"+restrictpriceBd.toString());
            amountEt.setText(restrictpriceBd.toString());
            return;
        }
        profitBd=amountBd.multiply(interestRateBd).divide(percentageBd).multiply(financingCycleBd).divide(allYearBd, 2, BigDecimal.ROUND_DOWN);
        amountRateBd=amountBd.add(profitBd);
        if (TextUtils.equals("0", financingType)) {
            incomeStatement.setText("预计本息合计"+amountRateBd.toString()+"个能量:支付能量"+amountBd.toString()+"个+收益能量"+profitBd.toString()+"个");
        }else {
            incomeStatement.setText("预计本息合计"+amountRateBd.toString()+"元现金:支付现金"+amountBd.toString()+"元+收益现金"+profitBd.toString()+"元");
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                Intent intent = new Intent(this, CommonDialogActivity.class);
                intent.putExtra(StaticData.TITLE_DATA, "提示");
                intent.putExtra(StaticData.CONTENT_DATA, "钱包能量不足");
                startActivityForResult(intent, REQUEST_LACK_MONEY);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_LACK_MONEY:
                    FinancingOrderDetailActivity.start(this);
                    break;
                default:
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerFinancingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .financingModule(new FinancingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(FinancingContract.PayFcOrderPresenter presenter) {

    }

    @Override
    public void renderPayFcOrderInfo(PayFcOrderInfo payFcOrderInfo) {
        if (payFcOrderInfo != null) {
            amountEt.setText(payFcOrderInfo.getPurchaseprice());
            walletBd = new BigDecimal(payFcOrderInfo.getQianbao()).setScale(2, BigDecimal.ROUND_DOWN);
            balanceBd = new BigDecimal(payFcOrderInfo.getYue()).setScale(2, BigDecimal.ROUND_DOWN);
            restrictpriceBd = new BigDecimal(payFcOrderInfo.getRestrictprice()).setScale(2, BigDecimal.ROUND_DOWN);
            totalBd = walletBd.add(balanceBd);
            availableTotal.setText(totalBd.toString());
            availableItem.setText("(钱包" + payFcOrderInfo.getQianbao() + "+余额" + payFcOrderInfo.getYue() + ")");
            if (TextUtils.equals("0", financingType)) {
                payType.setText("支付能量(个)");
                initialValue.setText(payFcOrderInfo.getPurchaseprice() + "个能量起出借");
                availableType.setText("可用能量(个)");
            } else {
                payType.setText("支付现金(元)");
                initialValue.setText(payFcOrderInfo.getPurchaseprice() + "元起出借");
                availableType.setText("可用现金(元)");
            }
        }
    }

    private void addNumber(){

    }
}
