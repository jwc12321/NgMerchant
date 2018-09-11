package com.nenggou.slsm.financing.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.widget.list.MoreLoadable;
import com.nenggou.slsm.common.widget.list.Refreshable;
import com.nenggou.slsm.data.entity.FinancingItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/31.
 */

public class FinancingTypeItemAdapter extends RecyclerView.Adapter<FinancingTypeItemAdapter.FinancingItemView> implements Refreshable<FinancingItemInfo>, MoreLoadable<FinancingItemInfo> {
    private LayoutInflater layoutInflater;
    private List<FinancingItemInfo> financingItemInfos;

    public void setData(List<FinancingItemInfo> financingItemInfos) {
        this.financingItemInfos = financingItemInfos;
        notifyDataSetChanged();
    }
    @Override
    public void refresh(List<FinancingItemInfo> list) {
        this.financingItemInfos = list;
        notifyDataSetChanged();
    }
    public void addMore(List<FinancingItemInfo> moreList) {
        int pos = financingItemInfos.size();
        financingItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public FinancingItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_financing_type_item, parent, false);
        return new FinancingItemView(view);
    }

    @Override
    public void onBindViewHolder(FinancingItemView holder, int position) {
        final FinancingItemInfo financingItemInfo = financingItemInfos.get(holder.getAdapterPosition());
        holder.bindData(financingItemInfo);
        holder.financingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.goNovice(financingItemInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return financingItemInfos == null ? 0 : financingItemInfos.size();
    }


    public class FinancingItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.interestRate)
        TextView interestRate;
        @BindView(R.id.additional)
        TextView additional;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.surplus_amount)
        TextView surplusAmount;
        @BindView(R.id.financing_item)
        RelativeLayout financingItem;


        public FinancingItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(FinancingItemInfo financingItemInfo) {
            title.setText(financingItemInfo.getTitle());
            interestRate.setText(financingItemInfo.getInterestRate() + "%");
            if (TextUtils.equals("0.00", financingItemInfo.getAdditional())) {
                additional.setText("");
            } else {
                additional.setText("+" + financingItemInfo.getAdditional() + "%(" + financingItemInfo.getAdditionaltype() + ")");
            }

            time.setText(financingItemInfo.getCycle() + "天");
            if (TextUtils.equals("0", financingItemInfo.getPricetype())) {
                surplusAmount.setText("剩余能量" + financingItemInfo.getSurplus() + "个");
            } else {
                surplusAmount.setText("剩余金额" + financingItemInfo.getSurplus() + "元");
            }
        }
    }

    public interface ItemClickListener {
        void goNovice(FinancingItemInfo financingItemInfo);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
