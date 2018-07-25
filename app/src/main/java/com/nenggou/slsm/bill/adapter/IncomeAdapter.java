package com.nenggou.slsm.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.InComeInfo;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/20.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeView> {
    private LayoutInflater layoutInflater;
    private List<InComeInfo> inComeInfos;

    private BigDecimal offsetCashDecimal;//兑换现金
    private BigDecimal ptDecimal;//兑换比例
    private BigDecimal percentageDecimal;//能量兑换比是200，要除以100才行
    private BigDecimal energyDecimal;//总能量
    private BigDecimal cashDecimal;

    public IncomeAdapter() {
        percentageDecimal = new BigDecimal(100).setScale(2, BigDecimal.ROUND_DOWN);
    }

    public void setData(List<InComeInfo> inComeInfos) {
        this.inComeInfos = inComeInfos;
        notifyDataSetChanged();
    }

    public void setProportion(String proportion) {
        ptDecimal = new BigDecimal(proportion).setScale(2, BigDecimal.ROUND_DOWN);
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
        final InComeInfo inComeInfo = inComeInfos.get(holder.getAdapterPosition());
        holder.bindData(inComeInfo);
        holder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.goIncomeDetail(inComeInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return inComeInfos == null ? 0 : inComeInfos.size();
    }

    public class IncomeView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.total_price)
        TextView totalPrice;
        @BindView(R.id.energy_cash)
        TextView energyCash;
        @BindView(R.id.energy_cash_iv)
        ImageView energyCashIv;
        @BindView(R.id.cash)
        TextView cash;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public IncomeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(InComeInfo inComeInfo) {
            name.setText(inComeInfo.getNickname());
            time.setText(FormatUtil.formatDayTime(inComeInfo.getUpdatedAt()));
            cash.setText(inComeInfo.getPrice());
            cashDecimal=new BigDecimal(inComeInfo.getPrice()).setScale(2, BigDecimal.ROUND_DOWN);
            energyDecimal = new BigDecimal(inComeInfo.getPower()).setScale(2, BigDecimal.ROUND_DOWN);
            offsetCashDecimal = energyDecimal.multiply(ptDecimal).divide(percentageDecimal, 2, BigDecimal.ROUND_DOWN);
            energyCash.setText(offsetCashDecimal.toString());
            totalPrice.setText("合计:¥"+cashDecimal.add(offsetCashDecimal).toString());
        }
    }

    public interface ItemClickListener {
        void goIncomeDetail(String id);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
