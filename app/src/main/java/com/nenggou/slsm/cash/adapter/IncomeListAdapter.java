package com.nenggou.slsm.cash.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.widget.list.MoreLoadable;
import com.nenggou.slsm.common.widget.list.Refreshable;
import com.nenggou.slsm.data.entity.CashDetailInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/3.
 */

public class IncomeListAdapter extends RecyclerView.Adapter<IncomeListAdapter.IncomeListView> implements Refreshable<CashDetailInfo>, MoreLoadable<CashDetailInfo> {
    private LayoutInflater layoutInflater;
    private List<CashDetailInfo> cashDetailInfos;
    private String type;

    public IncomeListAdapter(String type) {
        this.type = type;
    }

    public void setData(List<CashDetailInfo> cashDetailInfos) {
        this.cashDetailInfos = cashDetailInfos;
        notifyDataSetChanged();
    }

    @Override
    public IncomeListView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_income_detail, parent, false);
        return new IncomeListView(view);
    }

    @Override
    public void onBindViewHolder(IncomeListView holder, int position) {
        CashDetailInfo cashDetailInfo = cashDetailInfos.get(holder.getAdapterPosition());
        holder.bindData(cashDetailInfo);
    }

    @Override
    public int getItemCount() {
        return cashDetailInfos == null ? 0 : cashDetailInfos.size();
    }

    @Override
    public void refresh(List<CashDetailInfo> list) {
        this.cashDetailInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<CashDetailInfo> list) {
        int pos = cashDetailInfos.size();
        cashDetailInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }

    public class IncomeListView extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.price)
        TextView price;

        public IncomeListView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CashDetailInfo cashDetailInfo) {
            name.setText(cashDetailInfo.getNickname());
            time.setText(FormatUtil.formatDateByLine(cashDetailInfo.getCreatedAt()));
            if(TextUtils.equals("0",type)){
                price.setText("+" + cashDetailInfo.getXianjin());
            }else {
                price.setText("-" + cashDetailInfo.getXianjin());
            }
        }
    }
}
