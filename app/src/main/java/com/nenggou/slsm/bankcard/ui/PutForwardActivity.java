package com.nenggou.slsm.bankcard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.bankcard.BankCardModule;
import com.nenggou.slsm.bankcard.DaggerBankCardComponent;
import com.nenggou.slsm.bankcard.presenter.PutForwardPresenter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.data.entity.BankCardInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/23.
 * 提现
 */

public class PutForwardActivity extends BaseActivity implements BankCardContract.PutForwardView{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.item_bank)
    RelativeLayout itemBank;
    @BindView(R.id.amount_et)
    EditText amountEt;
    @BindView(R.id.max_amount_price)
    TextView maxAmountPrice;
    @BindView(R.id.confirm_bt)
    Button confirmBt;
    private String bankId;
    private BankCardInfo bankCardInfo;

    @Inject
    PutForwardPresenter putForwardPresenter;


    private static final int CHOICE_BANK = 11;

    private String cashType;
    private String amountCash;
    private String inAmount;

    BigDecimal inAmountBd;
    BigDecimal amountCashBd;

    public static void start(Context context, String cashType, String amountCash) {
        Intent intent = new Intent(context, PutForwardActivity.class);
        intent.putExtra(StaticData.CASH_TYPE, cashType);
        intent.putExtra(StaticData.AMOUNT_CASH, amountCash);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        cashType = getIntent().getStringExtra(StaticData.CASH_TYPE);
        amountCash = getIntent().getStringExtra(StaticData.AMOUNT_CASH);
        maxAmountPrice.setText("可提现余额为"+amountCash+"元");
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.item_bank,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.item_bank:
                Intent intent = new Intent(this, BankCardListActivity.class);
                intent.putExtra(StaticData.BANK_ID, bankId);
                startActivityForResult(intent, CHOICE_BANK);
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case CHOICE_BANK:
                    bankCardInfo = (BankCardInfo) data.getSerializableExtra(StaticData.BANK_INFO);
                    if (bankCardInfo != null) {
                        bankId = bankCardInfo.getId();
                        String bankFour="";
                        if (!TextUtils.isEmpty(bankCardInfo.getCardno())&&bankCardInfo.getCardno().length()>6) {
                            String cardNo = bankCardInfo.getCardno();
                            bankFour=cardNo.substring(cardNo.length() - 4, cardNo.length());
                        }
                        if(TextUtils.isEmpty(bankFour)){
                            bankName.setText(bankCardInfo.getCardbank());
                        }else {
                            bankName.setText(bankCardInfo.getCardbank()+"("+bankFour+")");
                        }
                    }
                    break;
                default:
            }
        }
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.amount_et})
    public void checkLoginEnable() {
        inAmount= amountEt.getText().toString().trim();
        confirmBt.setEnabled(!(TextUtils.isEmpty(inAmount)));
    }

    private void confirm(){
        if(TextUtils.isEmpty(inAmount)){
            showMessage("请输入提现金额");
            return;
        }
        if(TextUtils.isEmpty(amountCash)){
            showMessage("能提现余额显示错误，请返回上一层");
            return;
        }
        if(TextUtils.isEmpty(bankId)){
            showMessage("请选择银行卡");
            return;
        }
        inAmountBd = new BigDecimal(inAmount).setScale(2, BigDecimal.ROUND_DOWN);
        amountCashBd=new BigDecimal(amountCash).setScale(2, BigDecimal.ROUND_DOWN);
        if(inAmountBd.compareTo(amountCashBd)>0){
            showMessage("输入价格大于可提现金额，请重新输入");
            amountEt.setText("");
            return;
        }
        putForwardPresenter.putForward(inAmount,cashType,bankId);
    }

    @Override
    public void setPresenter(BankCardContract.PutForwardPresenter presenter) {

    }

    @Override
    protected void initializeInjector() {
        DaggerBankCardComponent.builder()
                .applicationComponent(getApplicationComponent())
                .bankCardModule(new BankCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void purForwardSuccess() {
        showMessage("提交提现成功");
        finish();
    }
}
