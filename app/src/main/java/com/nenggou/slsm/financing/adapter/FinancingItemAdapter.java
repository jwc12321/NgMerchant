package com.nenggou.slsm.financing.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.FinancingItemInfo;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/31.
 */

public class FinancingItemAdapter extends RecyclerView.Adapter<FinancingItemAdapter.FinancingItemView> {
    private LayoutInflater layoutInflater;
    private List<FinancingItemInfo> financingItemInfos;
    private BigDecimal deviationDecimal;//偏差率
    private BigDecimal interestRateDecimal;//年利率
    private BigDecimal addDecimal;//年利率+偏差率
    private BigDecimal reduceDecimal;//年利率-偏差率

    public void setData(List<FinancingItemInfo> financingItemInfos) {
        this.financingItemInfos = financingItemInfos;
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
        View view = layoutInflater.inflate(R.layout.adapter_financing_item, parent, false);
        return new FinancingItemView(view);
    }

    @Override
    public void onBindViewHolder(FinancingItemView holder, int position) {
        final FinancingItemInfo financingItemInfo = financingItemInfos.get(holder.getAdapterPosition());
        holder.bindData(financingItemInfo);
        holder.financingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
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
            if(TextUtils.equals("0.00",financingItemInfo.getDeviation())){
                interestRate.setText(financingItemInfo.getInterestRate()+"%");
            }else {
                interestRateDecimal = new BigDecimal(financingItemInfo.getInterestRate()).setScale(2, BigDecimal.ROUND_DOWN);
                deviationDecimal = new BigDecimal(financingItemInfo.getDeviation()).setScale(2, BigDecimal.ROUND_DOWN);
                addDecimal = interestRateDecimal.add(deviationDecimal);
                reduceDecimal =interestRateDecimal.subtract(deviationDecimal);
                interestRate.setText(reduceDecimal.toString()+"%~"+addDecimal.toString()+"%");
            }
            if(TextUtils.equals("0.00",financingItemInfo.getAdditional())){
                additional.setText("");
            }else {
                additional.setText("+"+financingItemInfo.getAdditional()+"%("+financingItemInfo.getAdditionaltype()+")");
            }
            time.setText(financingItemInfo.getCycle()+"天");
            surplusAmount.setText("剩余金额"+financingItemInfo.getSurplus()+"元");
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
