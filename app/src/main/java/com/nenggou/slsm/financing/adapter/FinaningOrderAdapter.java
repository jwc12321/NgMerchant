package com.nenggou.slsm.financing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.list.MoreLoadable;
import com.nenggou.slsm.common.widget.list.Refreshable;
import com.nenggou.slsm.data.entity.FinaningOrderItemInfo;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/9/4.
 */

public class FinaningOrderAdapter extends RecyclerView.Adapter<FinaningOrderAdapter.FinaningOrderView> implements Refreshable<FinaningOrderItemInfo>, MoreLoadable<FinaningOrderItemInfo> {
    private LayoutInflater layoutInflater;
    private List<FinaningOrderItemInfo> finaningOrderItemInfos;

    public void setData(List<FinaningOrderItemInfo> finaningOrderItemInfos) {
        this.finaningOrderItemInfos = finaningOrderItemInfos;
        notifyDataSetChanged();
    }

    @Override
    public FinaningOrderView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_finaning_order, parent, false);
        return new FinaningOrderView(view);
    }

    @Override
    public void onBindViewHolder(FinaningOrderView holder, int position) {
        FinaningOrderItemInfo finaningOrderItemInfo = finaningOrderItemInfos.get(holder.getAdapterPosition());
        holder.bindData(finaningOrderItemInfo);
    }

    @Override
    public int getItemCount() {
        return finaningOrderItemInfos == null ? 0 : finaningOrderItemInfos.size();
    }

    @Override
    public void refresh(List<FinaningOrderItemInfo> list) {
        this.finaningOrderItemInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<FinaningOrderItemInfo> list) {
        int pos = finaningOrderItemInfos.size();
        finaningOrderItemInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }

    public class FinaningOrderView extends RecyclerView.ViewHolder {

        public FinaningOrderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(FinaningOrderItemInfo finaningOrderItemInfo) {

        }
    }

    public interface ItemClickListener {
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
