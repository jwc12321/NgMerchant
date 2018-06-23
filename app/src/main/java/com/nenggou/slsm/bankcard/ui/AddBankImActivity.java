package com.nenggou.slsm.bankcard.ui;

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
import com.nenggou.slsm.bankcard.presenter.AddbankcardPresenter;
import com.nenggou.slsm.common.StaticData;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by JWC on 2018/6/23.
 * 添加银行卡号和电话号码
 */

public class AddBankImActivity extends BaseActivity implements BankCardContract.AddbankcardView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.bank_where_et)
    EditText bankWhereEt;
    @BindView(R.id.tel_et)
    EditText telEt;
    @BindView(R.id.confirm_add)
    Button confirmAdd;

    @Inject
    AddbankcardPresenter addbankcardPresenter;

    private String bankName;
    private String bankWhere;
    private String bankTel;
    private String bankNumber;
    private String bankUser;

    public static void start(Context context, String bankName,String bankNumber,String bankUser) {
        Intent intent = new Intent(context, AddBankImActivity.class);
        intent.putExtra(StaticData.BANK_NAME, bankName);
        intent.putExtra(StaticData.BANK_NUMBER,bankNumber);
        intent.putExtra(StaticData.BANK_USER,bankUser);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_im);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        bankName = getIntent().getStringExtra(StaticData.BANK_NAME);
        bankNumber=getIntent().getStringExtra(StaticData.BANK_NUMBER);
        bankUser=getIntent().getStringExtra(StaticData.BANK_USER);
        if (!TextUtils.isEmpty(bankName)) {
            nameEt.setText(bankName);
        }
    }

    /**
     * 监听输入框
     */
    @OnTextChanged({R.id.name_et, R.id.bank_where_et,R.id.tel_et})
    public void checkLoginEnable() {
        bankName = nameEt.getText().toString().trim();
        bankWhere = bankWhereEt.getText().toString().trim();
        bankTel=telEt.getText().toString().trim();
        confirmAdd.setEnabled(!(TextUtils.isEmpty(bankName) || TextUtils.isEmpty(bankWhere)|| TextUtils.isEmpty(bankTel)));
    }


    @OnClick({R.id.back, R.id.confirm_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_add:
                addbankcardPresenter.addBankCard(bankUser,bankName,bankWhere,bankNumber,bankTel);
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BankCardContract.AddbankcardPresenter presenter) {

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
    public void addSuccess() {
        finish();
    }
}
