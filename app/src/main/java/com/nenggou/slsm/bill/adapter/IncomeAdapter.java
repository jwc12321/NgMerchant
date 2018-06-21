package com.nenggou.slsm.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.InComeInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/20.
 *
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeView> {
    private LayoutInflater layoutInflater;
    private List<InComeInfo> inComeInfos;

    public void setData(List<InComeInfo> inComeInfos) {
        this.inComeInfos=inComeInfos;
        notifyDataSetChanged();
    }
    public void addMore(List<InComeInfo> moreList) {
        int pos = inComeInfos.size();
        inComeInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }
    @Override
    public IncomeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_income, parent, false);
        return new IncomeView(view);
    }

    @Override
    public void onBindViewHolder(IncomeView holder, int position) {
        InComeInfo inComeInfo=inComeInfos.get(holder.getAdapterPosition());
        holder.bindData(inComeInfo);
    }

    @Override
    public int getItemCount() {
        return inComeInfos==null?0:inComeInfos.size();
    }

    public class IncomeView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.cash)
        TextView cash;
        @BindView(R.id.rmb)
        TextView rmb;
        @BindView(R.id.energy)
        TextView energy;

        public IncomeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(InComeInfo inComeInfo) {
            name.setText(inComeInfo.getNickname());
            time.setText(FormatUtil.formatDayTime(inComeInfo.getUpdatedAt()));
            cash.setText(inComeInfo.getPrice());
            energy.setText(inComeInfo.getPower());
        }
    }
}
