package com.nenggou.slsm.ranking.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.refreshview.HeaderViewLayout;
import com.nenggou.slsm.data.RemoteDataException;
import com.nenggou.slsm.ranking.adapter.ConsumeRankingAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/24.
 * 消费排行
 */

public class ConsumeRankingFragment extends BaseFragment {
    @BindView(R.id.year_ranking)
    TextView yearRanking;
    @BindView(R.id.season_ranking)
    TextView seasonRanking;
    @BindView(R.id.season_ranking_ll)
    LinearLayout seasonRankingLl;
    @BindView(R.id.month_ranking)
    TextView monthRanking;
    @BindView(R.id.choice_year_ll)
    LinearLayout choiceYearLl;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.energy_num)
    TextView energyNum;
    @BindView(R.id.consume_num)
    TextView consumeNum;
    @BindView(R.id.consume_info)
    RelativeLayout consumeInfo;
    @BindView(R.id.consume_rv)
    RecyclerView consumeRv;
    @BindView(R.id.refreshLayout)
    HeaderViewLayout refreshLayout;
    @BindView(R.id.season_rv)
    RecyclerView seasonRv;
    private boolean isFirstLoad = true;
    private String isFirstIn = "0";
    private ConsumeRankingAdapter consumeRankingAdapter;

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

    private void initView(){
        refreshLayout.setOnRefreshListener(mOnRefreshListener);
        refreshLayout.setCanLoadMore(false);
    }

    private void addconsumeAdapter() {
        consumeRankingAdapter=new ConsumeRankingAdapter(getActivity());
        consumeRv.setAdapter(consumeRankingAdapter);
    }

    private void addSeasonAdapter(){

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoad && getUserVisibleHint() && TextUtils.equals("0", isFirstIn)) {
            isFirstIn = "1";
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
        }

        @Override
        public void onLoadMore() {
        }

        @Override
        public void onModeChanged(@HeaderViewLayout.Mode int mode) {
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
