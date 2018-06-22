package com.nenggou.slsm.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.HistoryIncomeItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/20.
 */

public class HMIncomeAdapter extends RecyclerView.Adapter<HMIncomeAdapter.HMIncomeView> {

    private LayoutInflater layoutInflater;
    private List<HistoryIncomeItem> historyIncomeItems;

    public void setData(List<HistoryIncomeItem> historyIncomeItems) {
        this.historyIncomeItems = historyIncomeItems;
        notifyDataSetChanged();
    }

    public void addMore(List<HistoryIncomeItem> moreList) {
        int pos = historyIncomeItems.size();
        historyIncomeItems.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public HMIncomeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_hm_income_item, parent, false);
        return new HMIncomeView(view);
    }

    @Override
    public void onBindViewHolder(HMIncomeView holder, int position) {
        final HistoryIncomeItem historyIncomeItem = historyIncomeItems.get(holder.getAdapterPosition());
        holder.bindData(historyIncomeItem);
        holder.incomeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.goIncomeList(historyIncomeItem.getDate());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyIncomeItems == null ? 0 : historyIncomeItems.size();
    }

    public class HMIncomeView extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.income_number_tv)
        TextView incomeNumberTv;
        @BindView(R.id.income_number)
        TextView incomeNumber;
        @BindView(R.id.cash_income)
        TextView cashIncome;
        @BindView(R.id.energy_income)
        TextView energyIncome;
        @BindView(R.id.detail_rl)
        RelativeLayout detailRl;
        @BindView(R.id.income_item)
        RelativeLayout incomeItem;

        public HMIncomeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(HistoryIncomeItem historyIncomeItem) {
            time.setText(historyIncomeItem.getDate());
            incomeNumber.setText(historyIncomeItem.getCountTotal());
            cashIncome.setText("现金:" + historyIncomeItem.getAllmoney() + "元");
            energyIncome.setText("能量" + historyIncomeItem.getAllpower() + "个");
        }
    }

    public interface ItemClickListener {
        void goIncomeList(String date);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
