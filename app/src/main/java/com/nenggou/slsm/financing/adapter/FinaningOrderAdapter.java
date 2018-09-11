package com.nenggou.slsm.financing.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.unit.ProfitBdUtils;
import com.nenggou.slsm.data.entity.FinaningOrderItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/9/4.
 */

public class FinaningOrderAdapter extends RecyclerView.Adapter<FinaningOrderAdapter.FinaningOrderView> {
    private LayoutInflater layoutInflater;
    private List<FinaningOrderItemInfo> finaningOrderItemInfos;

    public void setData(List<FinaningOrderItemInfo> finaningOrderItemInfos) {
        this.finaningOrderItemInfos = finaningOrderItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<FinaningOrderItemInfo> list) {
        int pos = finaningOrderItemInfos.size();
        finaningOrderItemInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
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
        final FinaningOrderItemInfo finaningOrderItemInfo = finaningOrderItemInfos.get(holder.getAdapterPosition());
        holder.bindData(finaningOrderItemInfo);
        holder.orderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.returnFcId(finaningOrderItemInfo.getFinancingid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return finaningOrderItemInfos == null ? 0 : finaningOrderItemInfos.size();
    }


    public class FinaningOrderView extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.order_state)
        Button orderState;
        @BindView(R.id.create_time)
        TextView createTime;
        @BindView(R.id.lend_number)
        TextView lendNumber;
        @BindView(R.id.lend_type)
        TextView lendType;
        @BindView(R.id.profit_number)
        TextView profitNumber;
        @BindView(R.id.profit_type)
        TextView profitType;
        @BindView(R.id.due_date)
        TextView dueDate;
        @BindView(R.id.order_item)
        LinearLayout orderItem;

        public FinaningOrderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(FinaningOrderItemInfo finaningOrderItemInfo) {
            title.setText(finaningOrderItemInfo.getTitle());
            if (TextUtils.equals("0", finaningOrderItemInfo.getStatus())) {
                orderState.setText("售卖中");
                orderState.setSelected(true);
            } else if (TextUtils.equals("0", finaningOrderItemInfo.getStatus())) {
                orderState.setText("正在计息");
                orderState.setSelected(true);
            } else if (TextUtils.equals("0", finaningOrderItemInfo.getStatus())) {
                orderState.setText("返息中");
                orderState.setSelected(true);
            } else if (TextUtils.equals("0", finaningOrderItemInfo.getStatus())) {
                orderState.setText("已结束");
                orderState.setSelected(false);
            }
            createTime.setText(FormatUtil.formatDateByLine(finaningOrderItemInfo.getCreatedAt()));
            if (TextUtils.equals("0", finaningOrderItemInfo.getPricetype())) {
                lendType.setText("出借能量");
                lendNumber.setText(finaningOrderItemInfo.getPrice() + "个");
                if(TextUtils.equals("3",finaningOrderItemInfo.getStatus())) {
                    profitType.setText("总收益");
                    profitNumber.setText(finaningOrderItemInfo.getAccumulative()+"个能量");
                }else {
                    profitType.setText("预计收益");
                    profitNumber.setText(ProfitBdUtils.getProfitBd(finaningOrderItemInfo.getPrice(),finaningOrderItemInfo.getInterestRate(),finaningOrderItemInfo.getCycle(),finaningOrderItemInfo.getAdditional())+"个能量");
                }
            } else {
                lendType.setText("出借现金");
                lendNumber.setText(finaningOrderItemInfo.getPrice() + "元");
                if(TextUtils.equals("3",finaningOrderItemInfo.getStatus())) {
                    profitType.setText("总收益");
                    profitNumber.setText(finaningOrderItemInfo.getAccumulative()+"元现金");
                }else {
                    profitType.setText("预计收益");
                    profitNumber.setText(ProfitBdUtils.getProfitBd(finaningOrderItemInfo.getPrice(),finaningOrderItemInfo.getInterestRate(),finaningOrderItemInfo.getCycle(),finaningOrderItemInfo.getAdditional())+"元现金");
                }
            }
            dueDate.setText(FormatUtil.formatDateYear(finaningOrderItemInfo.getEndtime()));

        }
    }

    public interface ItemClickListener {
        void returnFcId(String financingId);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
