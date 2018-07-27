package com.nenggou.slsm.bankcard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.nenggou.slsm.bankcard.adapter.PutForwardAdapter;
import com.nenggou.slsm.bankcard.presenter.PutForwardInfosPresenter;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.widget.chooseTime.ChooseTimePicker;
import com.nenggou.slsm.data.entity.PutForwardInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/26.
 * 提现列表
 */

public class PutForwardListActivity extends BaseActivity implements BankCardContract.PutForwardInfosView, PutForwardAdapter.MuneItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.screen_date)
    ImageView screenDate;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.present_record_rv)
    RecyclerView presentRecordRv;
    @Inject
    PutForwardInfosPresenter putForwardInfosPresenter;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.month_ll)
    LinearLayout monthLl;
    private PutForwardAdapter putForwardAdapter;

    private ChooseTimePicker chooseStartTimePicker;
    private int startYearSelect = 0;
    private int startMonthSelect = 0;
    private int startDaySelect = 0;
    private String startTime;

    private List<PutForwardInfo> putForwardInfos;

    public static void start(Context context) {
        Intent intent = new Intent(context, PutForwardListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_forward_list);
        ButterKnife.bind(this);
        setHeight(back, title, screenDate);
        initView();
    }

    private void initView() {
        putForwardInfos = new ArrayList<>();
        putForwardAdapter = new PutForwardAdapter();
        putForwardAdapter.setItemClickListener(this);
        presentRecordRv.setAdapter(putForwardAdapter);
        putForwardInfosPresenter.getPutForwardInfos("");
        startYearSelect = 100;
        startMonthSelect = FormatUtil.nowMonth();
        presentRecordRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                if (putForwardInfos != null && position < putForwardInfos.size()) {
                    monthLl.setVisibility(View.VISIBLE);
                    time.setText(putForwardInfos.get(position).getDate());
                    totalPrice.setText("提现¥"+putForwardInfos.get(position).getAllmoney());
                }
            }
        });
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
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
    public void renderPutForwardInfos(List<PutForwardInfo> putForwardInfos) {
        this.putForwardInfos = putForwardInfos;
        if (putForwardInfos != null && putForwardInfos.size() > 0) {
            monthLl.setVisibility(View.VISIBLE);
            time.setText(putForwardInfos.get(0).getDate());
            totalPrice.setText("提现¥"+putForwardInfos.get(0).getAllmoney());
        }else {
            monthLl.setVisibility(View.GONE);
        }
        putForwardAdapter.setData(putForwardInfos);
    }

    @Override
    public void setPresenter(BankCardContract.PutForwardInfosPresenter presenter) {

    }

    @OnClick({R.id.back, R.id.screen_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.screen_date:
                setStartTimePicker();
                break;
            default:
        }
    }

    @Override
    public void returnId(String id) {
        PutForwardDetailActivity.start(this, id);
    }

    /**
     * 开启起始时间的时间选择器
     */
    private void setStartTimePicker() {
        chooseStartTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("2")
                .yearSelectGet(startYearSelect)
                .monthSelectGet(startMonthSelect)
                .daySelectGet(startDaySelect)
                .build();
        chooseStartTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                startYearSelect = backYearSelect;
                startMonthSelect = backMonthSelect;
                startDaySelect = backDaySelect;
                startTime = time;
                putForwardInfosPresenter.getPutForwardInfos(startTime);
            }
        });
        chooseStartTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        chooseStartTimePicker.show(this);
    }
}

