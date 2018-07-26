package com.nenggou.slsm.bankcard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bankcard.BankCardContract;
import com.nenggou.slsm.bankcard.BankCardModule;
import com.nenggou.slsm.bankcard.DaggerBankCardComponent;
import com.nenggou.slsm.bankcard.presenter.PutForwardDetailPresenter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.PutForwardDetailInfo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/26.
 */

public class PutForwardDetailActivity extends BaseActivity implements BankCardContract.PutForwardDetailView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.current_state)
    TextView currentState;
    @BindView(R.id.putforward_type)
    TextView putforwardType;
    @BindView(R.id.putforward_number)
    TextView putforwardNumber;
    @BindView(R.id.apply_time)
    TextView applyTime;
    @BindView(R.id.reach_time)
    TextView reachTime;
    @BindView(R.id.purforward_bank)
    TextView purforwardBank;
    @BindView(R.id.putforward_orderno)
    TextView putforwardOrderno;

    private String id;

    @Inject
    PutForwardDetailPresenter putForwardDetailPresenter;

    public static void start(Context context,String id) {
        Intent intent = new Intent(context, PutForwardDetailActivity.class);
        intent.putExtra(StaticData.ID,id);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward_detail);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        id=getIntent().getStringExtra(StaticData.ID);
        putForwardDetailPresenter.getPutForwardDetail(id);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void setPresenter(BankCardContract.PutForwardDetailPresenter presenter) {

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
    public void renderPutForwardDetail(PutForwardDetailInfo putForwardDetailInfo) {
        if(putForwardDetailInfo!=null){
            totalPrice.setText(putForwardDetailInfo.getTotalAmount());
            if(TextUtils.equals("1",putForwardDetailInfo.getType())){
                type.setText("现金提现");
                putforwardType.setText("现金");
                putforwardNumber.setText(putForwardDetailInfo.getPrice());
            }else {
                type.setText("能量提现");
                putforwardType.setText("能量");
                putforwardNumber.setText(putForwardDetailInfo.getPower()+"个能量");
            }
            if(TextUtils.equals("0",putForwardDetailInfo.getStatus())){
                currentState.setText("申请中");
            }else {
                currentState.setText("已到账");
            }
            applyTime.setText(FormatUtil.formatDateByLine(putForwardDetailInfo.getCreatedAt()));
            reachTime.setText(FormatUtil.formatDateByLine(putForwardDetailInfo.getUpdatedAt()));
            purforwardBank.setText(putForwardDetailInfo.getCardbank());
            putforwardOrderno.setText(putForwardDetailInfo.getCardno());
        }
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
