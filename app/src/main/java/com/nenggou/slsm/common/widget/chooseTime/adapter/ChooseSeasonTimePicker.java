package com.nenggou.slsm.common.widget.chooseTime.adapter;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.chooseTime.ActionSheet;
import com.nenggou.slsm.common.widget.chooseTime.OnWheelChangedListener;
import com.nenggou.slsm.common.widget.chooseTime.OnWheelScrollListener;
import com.nenggou.slsm.common.widget.chooseTime.WheelView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2017/1/13.
 */

public class ChooseSeasonTimePicker extends ActionSheet {


    private static final String TAG = "ChooseTimePicker";
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.year_wv)
    WheelView mYearWheelView;
    @BindView(R.id.month_wv)
    WheelView mMonthWheelView;
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;

    //变量
    private ArrayList<String> arry_year = new ArrayList<String>();
    private ArrayList<String> arry_month = new ArrayList<String>();

    private int nowYearId = 0;
    private int nowMonthId = 0;

    private int nowYearPosition = 0;

    private String mYearStr;
    private String mMonthStr;

    private int backYearSelect = 0;
    private int backMonthSelect = 0;
    //常量
    private final int MAX_TEXT_SIZE = 16;
    private final int MIN_TEXT_SIZE = 14;

    private boolean monthAll = false;
    private boolean yearAll = false;

    private View sheetView;
    private static final String KEY_YEAR_SELECT = "key_year_select";
    private static final String KEY_MONTH_SELECT = "key_month_select";


    public static ChooseSeasonTimePicker newInstance() {
        ChooseSeasonTimePicker chooseTimePicker = new ChooseSeasonTimePicker();
        return chooseTimePicker;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.action_sheet_choose_season_schedule;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init() {
        sheetView = getSheetView();
        ButterKnife.bind(this, sheetView);
        Bundle bundle = getArguments();
        nowYearId = bundle.getInt(KEY_YEAR_SELECT);
        nowMonthId = bundle.getInt(KEY_MONTH_SELECT);
        monthAll = false;
        yearAll = false;
        initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void initData() {
        initYear();
        initMonth();
        initListener();
        initNowData();
    }

    private void initNowData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mYearWheelView.setCurrentItem(nowYearId);
//                mMonthWheelView.setCurrentItem(nowMonthId);
            }
        }, 100);
    }

    public void setTime(int nowYearId, int nowMonthId) {
        this.nowYearId = nowYearId;
        this.nowMonthId = nowMonthId;
        initNowData();
    }

    /**
     * 初始化年
     */
    private void initYear() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        arry_year.clear();
        for (int i = 0; i <= 200; i++) {
            int year = nowYear - 100 + i;
            arry_year.add(year + "年");
            if (year == nowYear) {
                nowYearPosition = arry_year.size() - 1;
            }
        }
        mYearAdapter = new CalendarTextAdapter(getActivity(), arry_year, nowYearId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mYearWheelView.setVisibleItems(5);
        mYearWheelView.setViewAdapter(mYearAdapter);
        mYearWheelView.setCurrentItem(nowYearId);
        mYearStr = arry_year.get(nowYearId);
    }

    private int getYearMonth(String dateStr, String splitStr) {
        if (!TextUtils.equals("全部", dateStr)) {
            String[] dateTemp = null;
            dateTemp = dateStr.split(splitStr);
            return Integer.parseInt(dateTemp[0]);
        }
        return -1;
    }


    /**
     * 初始化月
     */

    private void initMonth() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
        arry_month.clear();
        for (int i = 1; i <5; i++) {
            arry_month.add("第"+i+"季度");
        }
        mMonthAdapter = new CalendarTextAdapter(getActivity(), arry_month, nowMonth, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMonthWheelView.setVisibleItems(4);
        mMonthWheelView.setViewAdapter(mMonthAdapter);
        mMonthWheelView.setCurrentItem(nowMonthId);
        mMonthStr = arry_month.get(nowMonthId);
    }

    /**
     * 初始化滚动监听事件
     */
    private void initListener() {
        //年份*****************************
        mYearWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                backYearSelect = wheel.getCurrentItem();
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
                mYearStr = arry_year.get(wheel.getCurrentItem()) + "";
                yearAll = false;
                monthAll = false;
                mMonthWheelView.setCanScroll(true);
            }
        });

        mYearWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
            }
        });

        //月********************
        mMonthWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                backMonthSelect = wheel.getCurrentItem();
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setMonthTextViewStyle(currentText, mMonthAdapter);
                mMonthStr = arry_month.get(wheel.getCurrentItem());
                monthAll = false;

            }
        });

        mMonthWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMonthAdapter);
            }
        });
    }


    /**
     * 滚轮的adapter
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        public void setList(ArrayList<String> list) {
            this.list = list;
            notifyDataChangedEvent();
        }

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, R.id.tempValue, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index) + "";
            return str;
        }
    }


    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(MAX_TEXT_SIZE);
                textvew.setTextColor(getActivity().getResources().getColor(R.color.appText5));
            } else {
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
            }
        }
    }


    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setMonthTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (yearAll) {
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
            } else {
                if (curriteItemText.equals(currentText)) {
                    textvew.setTextSize(MAX_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText5));
                } else {
                    textvew.setTextSize(MIN_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
                }
            }
        }
    }

    /**
     * 设置文字的大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setDayTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (monthAll) {
                textvew.setTextSize(MIN_TEXT_SIZE);
                textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
            } else {
                if (curriteItemText.equals(currentText)) {
                    textvew.setTextSize(MAX_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText5));
                } else {
                    textvew.setTextSize(MIN_TEXT_SIZE);
                    textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
                }
            }
        }
    }

    /**
     * 设置文字的不选中大小
     *
     * @param adapter
     */
    public void setTextViewEnableStyle(CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            textvew.setTextSize(MIN_TEXT_SIZE);
            textvew.setTextColor(getActivity().getResources().getColor(R.color.appText2));
        }
    }


    public static class Builder {
        OnTimeSelectListener listener;
        int yearSelect;
        int monthSelect;

        public Builder yearSelectGet(int yearSelect) {
            this.yearSelect = yearSelect;
            return this;
        }

        public Builder monthSelectGet(int monthSelect) {
            this.monthSelect = monthSelect;
            return this;
        }


        public ChooseSeasonTimePicker build() {
            Bundle args = new Bundle();
            args.putInt(KEY_YEAR_SELECT, yearSelect);
            args.putInt(KEY_MONTH_SELECT, monthSelect);
            ChooseSeasonTimePicker chooseTimePicker = ChooseSeasonTimePicker.newInstance();
            chooseTimePicker.setArguments(args);
            chooseTimePicker.setListener(listener);
            return chooseTimePicker;
        }

        public Builder cb(OnTimeSelectListener listener) {
            this.listener = listener;
            return this;
        }
    }


    public interface OnTimeSelectListener {
        void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect);
    }

    public void setListener(OnTimeSelectListener listener) {
        this.listener = listener;
    }

    public OnTimeSelectListener listener;


    public interface OnCancelListener {
        void onCancel();
    }

    public void setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (cancelListener != null) {
            cancelListener.onCancel();
        }
    }

    public OnCancelListener cancelListener;

    @OnClick({R.id.cancel, R.id.confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                if (cancelListener != null) {
                    cancelListener.onCancel();
                }
                dismiss();
                break;
            case R.id.confirm:
                if (listener != null) {
                    Log.d(TAG, "mYearStr:" + mYearStr + "mMonthStr:" + mMonthStr);
                    listener.onConfirmServiceTime(strTimeToDateFormat(mYearStr, backMonthSelect), backYearSelect, backMonthSelect);
                    dismiss();
                }
                break;
            default:
                break;
        }
    }

    private String strTimeToDateFormat(String yearStr, int monthSelect) {
        if(monthSelect==0){
            return yearStr.replace("年", "-") +"01";
        }else if(monthSelect==1){
            return yearStr.replace("年", "-") +"04";
        }else if(monthSelect==2){
            return yearStr.replace("年", "-") +"07";
        }else {
            return yearStr.replace("年", "-") +"10";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
