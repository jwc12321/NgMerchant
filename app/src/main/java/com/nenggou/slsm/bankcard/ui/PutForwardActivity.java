package com.nenggou.slsm.bankcard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.bankcard.BankCardModule;
import com.nenggou.slsm.bankcard.DaggerBankCardComponent;
import com.nenggou.slsm.bankcard.presenter.PutForwardPresenter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.dialog.CommonDialog;
import com.nenggou.slsm.data.entity.BankCardInfo;
import com.nenggou.slsm.paypassword.ui.InputPayPwActivity;
import com.nenggou.slsm.paypassword.ui.RdSPpwActivity;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/23.
 * 提现
 */

public class PutForwardActivity extends BaseActivity implements BankCardContract.PutForwardView {

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
    @BindView(R.id.present_record)
    TextView presentRecord;
    @BindView(R.id.full_presentation)
    TextView fullPresentation;
    private String bankId;
    private BankCardInfo bankCardInfo;

    @Inject
    PutForwardPresenter putForwardPresenter;


    private static final int CHOICE_BANK = 11;

    private String cashType;
    private String amountCash;
    private String inAmount;
    private String proportion;

    private BigDecimal inAmountBd;
    private BigDecimal amountCashBd;
    private BigDecimal proportionBd;//兑换比例
    private BigDecimal percentageBd;//能量兑换比是200，要除以100才行
    private BigDecimal offsetEnergyBd;
    private CommonDialog putForwardDialog;
    private String content;
    boolean moneyDouble = true;
    private int digits = 2;
    private static final int REQUEST_PAY_PW = 23;
    private String payPassword;

    public static void start(Context context, String cashType, String amountCash, String proportion) {
        Intent intent = new Intent(context, PutForwardActivity.class);
        intent.putExtra(StaticData.CASH_TYPE, cashType);
        intent.putExtra(StaticData.AMOUNT_CASH, amountCash);
        intent.putExtra(StaticData.PROPORTION, proportion);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward);
        ButterKnife.bind(this);
        setHeight(back, title, presentRecord);
        initView();
    }

    private void initView() {
        cashType = getIntent().getStringExtra(StaticData.CASH_TYPE);
        amountCash = getIntent().getStringExtra(StaticData.AMOUNT_CASH);
        proportion = getIntent().getStringExtra(StaticData.PROPORTION);
        maxAmountPrice.setText("可提现余额为" + amountCash + "元");
        editListener();
    }


    /**
     * edittext监听
     */
    private void editListener() {
        amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(amountEt.getText().toString())) {
                    moneyDouble = true;
                } else {
                    try {
                        Double db = Double.valueOf(amountEt.getText().toString());
                        moneyDouble = true;
                    } catch (Exception e) {
                        moneyDouble = false;
                        Toast.makeText(PutForwardActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
                    }
                    if (moneyDouble) {
                        if (!TextUtils.isEmpty(amountEt.getText().toString())) {
                            limitedDecimal(amountEt.getText().toString(), amountEt);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.item_bank, R.id.confirm_bt, R.id.present_record,R.id.full_presentation})
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
            case R.id.present_record:
                PutForwardListActivity.start(this);
                break;
            case R.id.full_presentation:
                amountEt.setText(amountCash);
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
                        String bankFour = "";
                        if (!TextUtils.isEmpty(bankCardInfo.getCardno()) && bankCardInfo.getCardno().length() > 6) {
                            String cardNo = bankCardInfo.getCardno();
                            bankFour = cardNo.substring(cardNo.length() - 4, cardNo.length());
                        }
                        if (TextUtils.isEmpty(bankFour)) {
                            bankName.setText(bankCardInfo.getCardbank());
                        } else {
                            bankName.setText(bankCardInfo.getCardbank() + "(" + bankFour + ")");
                        }
                    }
                    break;
                case REQUEST_PAY_PW:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        payPassword = (String) bundle.getSerializable(StaticData.PAY_PASSWORD);
                        if (TextUtils.equals("1", cashType)) {
                            putForwardPresenter.putForward(inAmount, cashType, bankId, payPassword);
                        } else {
                            goPutFroward();
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
        inAmount = amountEt.getText().toString().trim();
        confirmBt.setEnabled(!(TextUtils.isEmpty(inAmount)));
    }

    private void confirm() {
        inAmount = amountEt.getText().toString().trim();
        if (TextUtils.isEmpty(inAmount)) {
            showMessage("请输入提现金额");
            return;
        }
        if (TextUtils.isEmpty(amountCash)) {
            showMessage("能提现余额显示错误，请返回上一层");
            return;
        }
        if (TextUtils.isEmpty(bankId)) {
            showMessage("请选择银行卡");
            return;
        }
        inAmountBd = new BigDecimal(inAmount).setScale(2, BigDecimal.ROUND_DOWN);
        amountCashBd = new BigDecimal(amountCash).setScale(2, BigDecimal.ROUND_DOWN);
        proportionBd = new BigDecimal(TextUtils.isEmpty(proportion) ? "0" : proportion).setScale(2, BigDecimal.ROUND_DOWN);
        percentageBd = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
        if (inAmountBd.compareTo(amountCashBd) > 0) {
            showMessage("输入价格大于可提现金额，请重新输入");
            amountEt.setText("");
            return;
        }
        putForwardPresenter.isSetUpPayPw();
    }

    private void goPutFroward() {
        offsetEnergyBd = inAmountBd.multiply(percentageBd).divide(proportionBd, 2, BigDecimal.ROUND_DOWN);
        content = "提¥" + inAmount + "需要消耗" + offsetEnergyBd.toString() + "个能量,请确认";
        putForwardDialog = new CommonDialog.Builder()
                .showTitle(false)
                .setContent(content)
                .setContentGravity(Gravity.CENTER)
                .setCancelButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        putForwardDialog.dismiss();
                    }
                })
                .setConfirmButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        putForwardDialog.dismiss();
                        putForwardPresenter.putForward(offsetEnergyBd.toString(), cashType, bankId, payPassword);
                    }
                }).create();
        putForwardDialog.show(getSupportFragmentManager(), "");
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

    @Override
    public void renderIsSetUpPayPw(String what) {
        if (TextUtils.equals("true", what)) {
            Intent intent = new Intent(this, InputPayPwActivity.class);
            startActivityForResult(intent, REQUEST_PAY_PW);
        } else {
            RdSPpwActivity.start(this);
        }
    }
}
