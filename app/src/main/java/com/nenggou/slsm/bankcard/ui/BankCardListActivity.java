package com.nenggou.slsm.bankcard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.bankcard.BankCardModule;
import com.nenggou.slsm.bankcard.DaggerBankCardComponent;
import com.nenggou.slsm.bankcard.adapter.BankCardAdapter;
import com.nenggou.slsm.bankcard.presenter.BankCardListPresenter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.entity.BankCardInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/23.
 */

public class BankCardListActivity extends BaseActivity implements BankCardContract.BankCardListView, BankCardAdapter.ItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bank_rv)
    RecyclerView bankRv;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.add_bank)
    LinearLayout addBank;

    private String bankId;
    private BankCardAdapter bankCardAdapter;
    @Inject
    BankCardListPresenter bankCardListPresenter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_list);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        bankId = getIntent().getStringExtra(StaticData.BANK_ID);
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        bankCardAdapter = new BankCardAdapter(bankId);
        bankCardAdapter.setItemClickListener(this);
        bankRv.setAdapter(bankCardAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankCardListPresenter.getBankCardList("1");
    }

    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            bankCardListPresenter.getBankCardList("0");
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void backBankId(BankCardInfo bankCardInfo) {
        Intent intent = new Intent();
        intent.putExtra(StaticData.BANK_INFO, bankCardInfo);
        setResult(Activity.RESULT_OK, intent);
        finish();
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
    public void setPresenter(Object presenter) {

    }

    @Override
    public void renderBankCardList(List<BankCardInfo> bankCardInfos) {
        bankCardAdapter.setData(bankCardInfos);
    }

    @OnClick({R.id.back, R.id.add_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_bank:
                BankNameActivity.start(this);
                break;
            default:
        }
    }
}
