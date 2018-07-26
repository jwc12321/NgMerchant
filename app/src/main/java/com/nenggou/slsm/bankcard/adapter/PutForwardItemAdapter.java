package com.nenggou.slsm.bankcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.PutForwardItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/26.
 */

public class PutForwardItemAdapter extends RecyclerView.Adapter<PutForwardItemAdapter.PutForwardItemView> {
    private LayoutInflater layoutInflater;
    private List<PutForwardItem> putForwardItems;

    public void setData(List<PutForwardItem> putForwardItems) {
        this.putForwardItems = putForwardItems;
        notifyDataSetChanged();
    }

    @Override
    public PutForwardItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_put_forward_item, parent, false);
        return new PutForwardItemView(view);
    }

    @Override
    public void onBindViewHolder(PutForwardItemView holder, int position) {
        final PutForwardItem putForwardItem = putForwardItems.get(holder.getAdapterPosition());
        holder.bindData(putForwardItem);
        holder.putforwardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.returnId(putForwardItem.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return putForwardItems == null ? 0 : putForwardItems.size();
    }

    public class PutForwardItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.putforward_item)
        RelativeLayout putforwardItem;

        public PutForwardItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PutForwardItem putForwardItem) {
            if (TextUtils.equals("0.00", putForwardItem.getPrice())) {
                name.setText("能量提现");
                price.setText(putForwardItem.getPower() + "个能量");
            } else {
                name.setText("现金提现");
                price.setText("¥" + putForwardItem.getPrice());
            }
            time.setText(FormatUtil.formatMonthByLine(putForwardItem.getCreatedAt()));
            bankName.setText(putForwardItem.getCardbank());
        }
    }

    public interface ItemClickListener {
        void returnId(String id);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
