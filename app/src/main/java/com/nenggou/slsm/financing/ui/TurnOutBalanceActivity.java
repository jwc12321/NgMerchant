package com.nenggou.slsm.financing.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.paypw.PayPwdEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/9/4.
 * 转出到余额
 */

public class TurnOutBalanceActivity extends BaseActivity {

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

    public static void start(Context context) {
        Intent intent = new Intent(context, TurnOutBalanceActivity.class);
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
        initEt();
        editListener();
    }


    private void initEt(){
        pwdEt.initStyle(R.drawable.password_num_bg, 6, 0.33f, R.color.backGround19, R.color.appText5, 20);
        pwdEt.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password=str;
            }
        });

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

    @OnClick({R.id.back, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:
                break;
            default:
        }
    }
}
