package com.nenggou.slsm.financing.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.TurnOutRecordItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/9/6.
 */

public class TurnOutRecordAdapter extends RecyclerView.Adapter<TurnOutRecordAdapter.TurnOutRecordView> {
    private LayoutInflater layoutInflater;
    private List<TurnOutRecordItem> turnOutRecordItems;

    @Override
    public TurnOutRecordView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_turn_out_record, parent, false);
        return new TurnOutRecordView(view);
    }

    public void setData(List<TurnOutRecordItem> turnOutRecordItems) {
        this.turnOutRecordItems = turnOutRecordItems;
        notifyDataSetChanged();
    }

    public void addMore(List<TurnOutRecordItem> moreList) {
        int pos = turnOutRecordItems.size();
        turnOutRecordItems.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public void onBindViewHolder(TurnOutRecordView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return turnOutRecordItems==null?0:turnOutRecordItems.size();
    }

    public class TurnOutRecordView extends RecyclerView.ViewHolder {
        @BindView(R.id.turn_out_state)
        TextView turnOutState;
        @BindView(R.id.turn_out_explain)
        TextView turnOutExplain;
        @BindView(R.id.turn_out_number)
        TextView turnOutNumber;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.turn_out_where)
        TextView turnOutWhere;
        public TurnOutRecordView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(TurnOutRecordItem turnOutRecordItem) {

        }
    }
}
