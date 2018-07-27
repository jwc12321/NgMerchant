package com.nenggou.slsm.ranking.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.ui.IntercourseRecordActivity;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.widget.GradationScrollView;
import com.nenggou.slsm.common.widget.chooseTime.ChooseTimePicker;
import com.nenggou.slsm.common.widget.chooseTime.adapter.ChooseSeasonTimePicker;
import com.nenggou.slsm.data.RemoteDataException;
import com.nenggou.slsm.data.entity.RankingInfo;
import com.nenggou.slsm.data.entity.RankingListInfo;
import com.nenggou.slsm.ranking.DaggerRankingComponent;
import com.nenggou.slsm.ranking.RankingContract;
import com.nenggou.slsm.ranking.RankingModule;
import com.nenggou.slsm.ranking.adapter.ConsumeRankingAdapter;
import com.nenggou.slsm.ranking.presenter.CRankingPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/24.
 * 消费排行
 */

public class ConsumeRankingFragment extends BaseFragment implements RankingContract.CRankingView,ConsumeRankingAdapter.ItemClickListener {

    @BindView(R.id.year_ranking)
    TextView yearRanking;
    @BindView(R.id.year_ranking_iv)
    ImageView yearRankingIv;
    @BindView(R.id.year_ranking_ll)
    LinearLayout yearRankingLl;
    @BindView(R.id.season_ranking)
    TextView seasonRanking;
    @BindView(R.id.season_ranking_iv)
    ImageView seasonRankingIv;
    @BindView(R.id.season_ranking_ll)
    LinearLayout seasonRankingLl;
    @BindView(R.id.month_ranking)
    TextView monthRanking;
    @BindView(R.id.month_ranking_iv)
    ImageView monthRankingIv;
    @BindView(R.id.month_ranking_ll)
    LinearLayout monthRankingLl;
    @BindView(R.id.choice_year_ll)
    LinearLayout choiceYearLl;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.energy_num)
    TextView energyNum;
    @BindView(R.id.cash_num)
    TextView cashNum;
    @BindView(R.id.consume_info)
    RelativeLayout consumeInfo;
    @BindView(R.id.consume_rv)
    RecyclerView consumeRv;
    @BindView(R.id.scrollview)
    GradationScrollView scrollview;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    private boolean isFirstLoad = true;
    private String isFirstIn = "0";
    private ConsumeRankingAdapter consumeRankingAdapter;

    private String choiceType = "0"; //0:全部 1：月 2：季度 3：年
    private String starttime;
    private ChooseTimePicker yearTimePicker;
    private int yearStartYearSelect = 0;
    private int yearStartMonthSelect = 0;
    private int yearStartDaySelect = 0;

    private ChooseTimePicker monthTimePicker;
    private int monthStartYearSelect = 0;
    private int monthStartMonthSelect = 0;
    private int monthStartDaySelect = 0;


    private ChooseSeasonTimePicker seasonTimePicker;
    private int seasonStartYearSelect = 0;
    private int seasonStartMonthSelect = 0;

    @Inject
    CRankingPresenter cRankingPresenter;

    private RankingInfo rankingInfo;

    public static ConsumeRankingFragment newInstance() {
        ConsumeRankingFragment consumeRankingFragment = new ConsumeRankingFragment();
        return consumeRankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_consume_ranking, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
        addconsumeAdapter();
        yearStartYearSelect = 100;
        yearStartMonthSelect = FormatUtil.nowMonth();
        monthStartYearSelect = 100;
        monthStartMonthSelect = FormatUtil.nowMonth();
        seasonStartYearSelect = 100;
    }

    private void addconsumeAdapter() {
        consumeRankingAdapter = new ConsumeRankingAdapter(getActivity());
        consumeRankingAdapter.setItemClickListener(this);
        consumeRv.setAdapter(consumeRankingAdapter);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
                if (cRankingPresenter != null) {
                    cRankingPresenter.getCRankingList("0", choiceType, starttime);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", isFirstIn)) {
            isFirstIn = "1";
            if (cRankingPresenter != null) {
                cRankingPresenter.getCRankingList("1", choiceType, starttime);
            }
        }
    }

    @Override
    public void showError(Throwable e) {
        if (e != null && e instanceof RemoteDataException) {
            if (((RemoteDataException) e).isAuthFailed()) {
                isFirstIn = "0";
            }
        }
        super.showError(e);
    }


    HeaderViewLayout.OnRefreshListener mOnRefreshListener = new HeaderViewLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            cRankingPresenter.getCRankingList("0", choiceType, starttime);
        }

        @Override
        public void onLoadMore() {
            cRankingPresenter.getMoreCRankingList(choiceType, starttime);
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @OnClick({R.id.year_ranking_ll, R.id.season_ranking_ll, R.id.month_ranking_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.year_ranking_ll:
                choiceType = "3";
                setYearTimePicker();
                break;
            case R.id.season_ranking_ll:
                choiceType = "2";
                setSeasonTimePicker();
                break;
            case R.id.month_ranking_ll:
                choiceType = "1";
                setMonthTimePicker();
                break;
            default:
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerRankingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .rankingModule(new RankingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setPresenter(RankingContract.CRankingPresenter presenter) {

    }

    @Override
    public void renderCRankingList(RankingListInfo rankingListInfo) {
        refreshLayout.stopRefresh();
        if (rankingListInfo != null) {
            if (rankingListInfo.getRankingInfos() != null && rankingListInfo.getRankingInfos().size() > 0) {
                rankingInfo = rankingListInfo.getRankingInfos().get(0);
                energyNum.setText(rankingInfo.getPower());
                cashNum.setText(rankingInfo.getPrice());
                userName.setText(rankingInfo.getNickname());
                GlideHelper.load(this, rankingInfo.getAvatar(), R.mipmap.app_icon, headPhoto);
                refreshLayout.setCanLoadMore(true);
            } else {
                energyNum.setText("");
                cashNum.setText("");
                userName.setText("");
                GlideHelper.load(this, "", R.mipmap.app_icon, headPhoto);
                refreshLayout.setCanLoadMore(false);
            }
            consumeRankingAdapter.setData(rankingListInfo.getRankingInfos());
        }else {
            energyNum.setText("");
            cashNum.setText("");
            userName.setText("");
            GlideHelper.load(this, "", R.mipmap.app_icon, headPhoto);
            refreshLayout.setCanLoadMore(false);
        }
    }

        @Override
        public void renderMoreCRankingList (RankingListInfo rankingListInfo){
            refreshLayout.stopRefresh();
            if (rankingListInfo != null && rankingListInfo.getRankingInfos() != null && rankingListInfo.getRankingInfos().size() > 0) {
                consumeRankingAdapter.addMore(rankingListInfo.getRankingInfos());
                refreshLayout.setCanLoadMore(true);
            } else {
                refreshLayout.setCanLoadMore(false);
            }
        }

        //字体加粗
        private void textBold(String type){
            TextPaint yearPaint = yearRanking.getPaint();
            TextPaint seasonPaint = seasonRanking.getPaint();
            TextPaint monthPaint = monthRanking.getPaint();
            yearPaint.setFakeBoldText(TextUtils.equals("3",type)?true:false);
            seasonPaint.setFakeBoldText(TextUtils.equals("2",type)?true:false);
            monthPaint.setFakeBoldText(TextUtils.equals("1",type)?true:false);
            yearRankingIv.setVisibility(TextUtils.equals("3",type)?View.VISIBLE:View.GONE);
            seasonRankingIv.setVisibility(TextUtils.equals("2",type)?View.VISIBLE:View.GONE);
            monthRankingIv.setVisibility(TextUtils.equals("1",type)?View.VISIBLE:View.GONE);
        }

        /**
         * 年选择器
         */

    private void setYearTimePicker() {
        yearTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("1")
                .yearSelectGet(yearStartYearSelect)
                .monthSelectGet(yearStartMonthSelect)
                .daySelectGet(yearStartDaySelect)
                .build();
        yearTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                yearStartYearSelect = backYearSelect;
                yearStartMonthSelect = backMonthSelect;
                yearStartDaySelect = backDaySelect;
                starttime = time;
                cRankingPresenter.getCRankingList("1", choiceType, starttime);
                yearTimePicker = null;
                textBold(choiceType);
            }
        });
        yearTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {
                yearTimePicker = null;
            }
        });
        yearTimePicker.show(getActivity());
    }

    /**
     * 年月选择器
     */
    private void setMonthTimePicker() {
        monthTimePicker = new ChooseTimePicker.Builder()
                .chooseTypeGet("2")
                .yearSelectGet(monthStartYearSelect)
                .monthSelectGet(monthStartMonthSelect)
                .daySelectGet(monthStartDaySelect)
                .build();
        monthTimePicker.setListener(new ChooseTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect, int backDaySelect) {
                monthStartYearSelect = backYearSelect;
                monthStartMonthSelect = backMonthSelect;
                monthStartDaySelect = backDaySelect;
                starttime = time;
                cRankingPresenter.getCRankingList("1", choiceType, starttime);
                monthTimePicker = null;
                textBold(choiceType);
            }
        });
        monthTimePicker.setCancelListener(new ChooseTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {
                monthTimePicker = null;
            }
        });
        monthTimePicker.show(getActivity());
    }


    /**
     * 季度选择器
     */
    private void setSeasonTimePicker() {
        seasonTimePicker = new ChooseSeasonTimePicker.Builder()
                .yearSelectGet(seasonStartYearSelect)
                .monthSelectGet(seasonStartMonthSelect)
                .build();
        seasonTimePicker.setListener(new ChooseSeasonTimePicker.OnTimeSelectListener() {
            @Override
            public void onConfirmServiceTime(String time, int backYearSelect, int backMonthSelect) {
                seasonStartYearSelect = backYearSelect;
                seasonStartMonthSelect=backMonthSelect;
                starttime = time;
                cRankingPresenter.getCRankingList("1", choiceType, starttime);
                seasonTimePicker = null;
                textBold(choiceType);
            }
        });
        seasonTimePicker.setCancelListener(new ChooseSeasonTimePicker.OnCancelListener() {
            @Override
            public void onCancel() {
                seasonTimePicker = null;
            }
        });
        seasonTimePicker.show(getActivity());
    }

    @Override
    public void goIntercourseRecord(String uid, String nickName) {
        IntercourseRecordActivity.start(getActivity(),uid,nickName,choiceType,starttime);
    }
}
