package com.nenggou.slsm.bill.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.widget.chooseTime.ChooseTimePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/6/22.
 */

public class ChoiceTimeActivity extends BaseActivity {

    @BindView(R.id.choice_shop_rl)
    RelativeLayout choiceShopRl;
    @BindView(R.id.query_all_iv)
    ImageView queryAllIv;
    @BindView(R.id.query_all)
    LinearLayout queryAll;
    @BindView(R.id.query_monthly_iv)
    ImageView queryMonthlyIv;
    @BindView(R.id.query_monthly)
    LinearLayout queryMonthly;
    @BindView(R.id.query_stage_iv)
    ImageView queryStageIv;
    @BindView(R.id.query_stage)
    LinearLayout queryStage;
    @BindView(R.id.start_iv)
    ImageView startIv;
    @BindView(R.id.start_st)
    View startSt;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.start_time_ll)
    LinearLayout startTimeLl;
    @BindView(R.id.end_iv)
    ImageView endIv;
    @BindView(R.id.end_st)
    View endSt;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    @BindView(R.id.end_time_ll)
    LinearLayout endTimeLl;
    @BindView(R.id.confirm_bt)
    Button confirmBt;
    @BindView(R.id.view_rl)
    RelativeLayout viewRl;
    @BindView(R.id.choice_time_ll)
    LinearLayout choiceTimeLl;

    private ChooseTimePicker chooseStartTimePicker;
    private int startYearSelect = 0;
    private int startMonthSelect = 0;
    private int startDaySelect = 0;
    private String startTime;

    private ChooseTimePicker chooseEndTimePicker;
    private int endYearSelect = 0;
    private int endMonthSelect = 0;
    private int endDaySelect = 0;
    private String endTime;

    private String choiceModule; //1：全部查询 2：月度查询 3：阶段查询


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_time);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        clickAll();
        startYearSelect = 100;
        startMonthSelect = FormatUtil.nowMonth();
        endYearSelect = 100;
        endMonthSelect = FormatUtil.nowMonth();
    }

    //点击查询全部
    private void clickAll() {
        choiceModule = "1";
        queryAllIv.setSelected(true);
        queryMonthlyIv.setSelected(false);
        queryStageIv.setSelected(false);
        choiceTimeLl.setVisibility(View.GONE);
    }

    //点击月度查询
    private void clickMonthlyl() {
        choiceModule = "2";
        queryAllIv.setSelected(false);
        queryMonthlyIv.setSelected(true);
        queryStageIv.setSelected(false);
        choiceTimeLl.setVisibility(View.VISIBLE);
        endTimeLl.setVisibility(View.GONE);

    }

    //点击阶段查询
    private void clickStage() {
        choiceModule = "3";
        queryAllIv.setSelected(false);
        queryMonthlyIv.setSelected(false);
        queryStageIv.setSelected(true);
        choiceTimeLl.setVisibility(View.VISIBLE);
        endTimeLl.setVisibility(View.VISIBLE);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.view_rl, R.id.query_all, R.id.query_monthly, R.id.query_stage, R.id.start_time_ll, R.id.end_time_ll, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_rl:
                finish();
                break;
            case R.id.query_all://全部查询
                clickAll();
                break;
            case R.id.query_monthly://月度查询
                clickMonthlyl();
                break;
            case R.id.query_stage://阶段查询
                clickStage();
                break;
            case R.id.start_time_ll://选择开始时间
                setStartTimePicker();
                break;
            case R.id.end_time_ll://选择结束时间
                setEndTimePicker();
                break;
            case R.id.confirm_bt://确认
                confirm();
                break;
            default:
        }
    }

    /**
     * 开启起始时间的时间选择器
     */
    private void setStartTimePicker() {
        chooseStartTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("1")
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
                startTimeTv.setText(time);
            }
        });
        chooseStartTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        chooseStartTimePicker.show(this);
    }

    /**
     * 开启起始时间的时间选择器
     */
    private void setEndTimePicker() {
        chooseEndTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("1")
                .yearSelectGet(endYearSelect)
                .monthSelectGet(endMonthSelect)
                .daySelectGet(endDaySelect)
                .build();
        chooseEndTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                endDaySelect = backYearSelect;
                endMonthSelect = backMonthSelect;
                endDaySelect = backDaySelect;
                endTime = time;
                endTimeTv.setText(time);
            }
        });
        chooseEndTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        chooseEndTimePicker.show(this);
    }

    private void confirm() {
        if (TextUtils.equals("1", choiceModule)) {
            backData("", "", "");
        } else if (TextUtils.equals("2", choiceModule)) {
            if (TextUtils.isEmpty(startTime)) {
                showError("请选择时间");
            } else {
                backData("", startTime, "");
            }
        } else {
            if (TextUtils.isEmpty(startTime)) {
                showError("请选择开始时间");
            } else if (TextUtils.isEmpty(endTime)) {
                showError("请选择结束时间");
            } else {
                if (endYearSelect < startYearSelect || endMonthSelect < startMonthSelect) {
                    showError("选择结束时间不能小于开始时间");
                } else {
                    if (endYearSelect == startYearSelect && endMonthSelect == startMonthSelect) {
                        backData("", startTime, "");
                    } else {
                        backData("", startTime, endTime);
                    }
                }
            }
        }
    }

    private void backData(String storeId, String startTime, String endTime) {
        Intent intent = new Intent();
        intent.putExtra(StaticData.SCREEN_STOREID, storeId);
        intent.putExtra(StaticData.SCREEN_START_TIME, startTime);
        intent.putExtra(StaticData.SCREEN_END_TIME, endTime);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
