package com.nenggou.slsm.ranking.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.data.entity.RankingInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/25.
 */

public class ConsumeRankingAdapter extends RecyclerView.Adapter<ConsumeRankingAdapter.ConsumeRankingView> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<RankingInfo> rankingInfos;

    public ConsumeRankingAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<RankingInfo> rankingInfos) {
        this.rankingInfos = rankingInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<RankingInfo> moreList) {
        int pos = rankingInfos.size();
        rankingInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public ConsumeRankingView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_consume_ranking, parent, false);
        return new ConsumeRankingView(view);
    }

    @Override
    public void onBindViewHolder(ConsumeRankingView holder, int position) {
        RankingInfo rankingInfo = rankingInfos.get(holder.getAdapterPosition());
        holder.bindData(holder.getAdapterPosition(), rankingInfo);
    }

    @Override
    public int getItemCount() {
        return rankingInfos == null ? 0 : rankingInfos.size();
    }

    public class ConsumeRankingView extends RecyclerView.ViewHolder {
        @BindView(R.id.medal)
        ImageView medal;
        @BindView(R.id.ranking)
        TextView ranking;
        @BindView(R.id.head_photo)
        RoundedImageView headPhoto;
        @BindView(R.id.rmb)
        TextView rmb;
        @BindView(R.id.rmb_number)
        TextView rmbNumber;
        @BindView(R.id.rank_coupon)
        ImageView rankCoupon;
        @BindView(R.id.energy)
        TextView energy;
        @BindView(R.id.name)
        TextView name;

        public ConsumeRankingView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position, RankingInfo rankingInfo) {
            if (position == 0) {
                medal.setVisibility(View.VISIBLE);
                ranking.setVisibility(View.GONE);
                medal.setBackgroundResource(R.mipmap.gold_medal_icon);
            } else if (position == 1) {
                medal.setVisibility(View.VISIBLE);
                ranking.setVisibility(View.GONE);
                medal.setBackgroundResource(R.mipmap.silver_medal_icon);
            } else if (position == 2) {
                medal.setVisibility(View.VISIBLE);
                ranking.setVisibility(View.GONE);
                medal.setBackgroundResource(R.mipmap.bronze_medal_icon);
            } else {
                medal.setVisibility(View.GONE);
                ranking.setVisibility(View.VISIBLE);
                ranking.setText(position);
            }
            GlideHelper.load((Activity) context, rankingInfo.getAvatar(), R.mipmap.app_icon, headPhoto);
            name.setText(rankingInfo.getNickname());
            rmbNumber.setText(rankingInfo.getPrice());
            energy.setText(rankingInfo.getPower());
        }
    }

    public interface ItemClickListener {
        void goConsumeDetail(String id);

        void goGiveCoupon();
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
