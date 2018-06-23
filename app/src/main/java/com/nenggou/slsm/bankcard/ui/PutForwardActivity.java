package com.nenggou.slsm.bankcard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.data.entity.BankCardInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/23.
 * 提现
 */

public class PutForwardActivity extends BaseActivity {
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
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.next_bt)
    Button nextBt;

    private String bankId;
    private BankCardInfo bankCardInfo;


    private static final int CHOICE_BANK = 11;

    public static void start(Context context) {
        Intent intent = new Intent(context, PutForwardActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.item_bank})
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
                        bankName.setText(bankCardInfo.getCardbank());
                    }
                    break;
                default:
            }
        }
    }
}
