package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.widget.paypw.PayPwdEditText;
import com.nenggou.slsm.data.request.TokenRequest;
import com.nenggou.slsm.financing.DaggerFinancingComponent;
import com.nenggou.slsm.financing.FinancingContract;
import com.nenggou.slsm.financing.FinancingModule;
import com.nenggou.slsm.financing.presenter.TurnOutBalancePresenter;

import java.math.BigDecimal;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/4.
 * 转出到余额
 */

public class TurnOutBalanceActivity extends BaseActivity implements FinancingContract.TurnOutBalanceView{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.amount_et)
    EditText amountEt;
    @BindView(R.id.have_number)
    TextView haveNumber;
    @BindView(R.id.all_turn_out)
    TextView allTurnOut;
    @BindView(R.id.pwd_et)
    PayPwdEditText pwdEt;
    @BindView(R.id.confirm)
    Button confirm;

    private String password;
    boolean moneyDouble = true;
    private int digits = 2;
    private String financingType;
    private String eAcNumber;
    private BigDecimal eAcNumberBd;
    private BigDecimal amountBd;

    @Inject
    TurnOutBalancePresenter turnOutBalancePresenter;

    public static void start(Context context,String financingType,String eAcNumber) {
        Intent intent = new Intent(context, TurnOutBalanceActivity.class);
        intent.putExtra(StaticData.FINANCING_TYPE,financingType);
        intent.putExtra(StaticData.E_A_C_NUMBER,eAcNumber);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_out_balance);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        financingType=getIntent().getStringExtra(StaticData.FINANCING_TYPE);
        eAcNumber=getIntent().getStringExtra(StaticData.E_A_C_NUMBER);
        eAcNumberBd= new BigDecimal(eAcNumber).setScale(2, BigDecimal.ROUND_DOWN);
        initEt();
        editListener();
        if(TextUtils.equals("0",financingType)){
            haveNumber.setText("可用"+eAcNumber+"个能量");
        }else {
            haveNumber.setText("可用"+eAcNumber+"元现金");
        }
    }


    private void initEt(){
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.appText5, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password=str;
                hideKeyboard();
            }
        });

    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(pwdEt.getWindowToken(), 0);
    }


    /**
     * edittext监听
     */
    private void editListener() {
        //只是为了显示光标
        amountEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountEt.setCursorVisible(true);
            }
        });
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
                        Toast.makeText(TurnOutBalanceActivity.this, "请正确填写", Toast.LENGTH_SHORT).show();
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

    @OnClick({R.id.back, R.id.confirm,R.id.all_turn_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                confirm();
                break;
            case R.id.all_turn_out:
                amountEt.setText(eAcNumber);
                amountEt.setCursorVisible(false);
                break;
            default:
        }
    }

    private void confirm(){
        amountBd=new BigDecimal(amountEt.getText().toString()).setScale(2, BigDecimal.ROUND_DOWN);
        if(amountBd.compareTo(eAcNumberBd)>0){
            showMessage("转出金额最大只能"+eAcNumber);
            amountEt.setText(eAcNumber);
            return;
        }
        if(TextUtils.isEmpty(password)){
            showMessage("请输入支付密码");
            return;
        }
        turnOutBalancePresenter.verifyPayPassword(password);
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
    public void setPresenter(FinancingContract.TurnOutBalancePresenter presenter) {

    }

    @Override
    public void verifySuccess() {
        turnOutBalancePresenter.turnOutBalance(amountEt.getText().toString(),financingType,password);
    }

    @Override
    public void turnOutSuccess() {
        showMessage("转出成功");
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
