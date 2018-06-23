package com.nenggou.slsm.bankcard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.cash.ui.CashDetailActivity;
import com.nenggou.slsm.common.unit.BankInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/23.
 * 输入姓名和卡号
 */

public class BankNameActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.card_number_et)
    EditText cardNumberEt;
    @BindView(R.id.next_bt)
    Button nextBt;

    private String name;
    private String cardNumber;

    public static void start(Context context) {
        Intent intent = new Intent(context, BankNameActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_name);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.name_et, R.id.card_number_et})
    public void checkLoginEnable() {
        name = nameEt.getText().toString().trim();
        cardNumber = cardNumberEt.getText().toString().trim();
        nextBt.setEnabled(!(TextUtils.isEmpty(name) || TextUtils.isEmpty(cardNumber)));
    }


    @OnClick({R.id.back, R.id.next_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next_bt:
                next();
                break;
            default:
        }
    }

    private void next(){
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(cardNumber)){
            char[] card = cardNumber.toCharArray();
            String cardName = BankInfo.getNameOfBank(card, 0);
            AddBankImActivity.start(this,cardName,cardNumber,name);
            finish();
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
