package com.nenggou.slsm.receipt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.ReceivablesInfo;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/20.
 */

public class RpCodeAdapter extends RecyclerView.Adapter<RpCodeAdapter.RpCodeView> {
    private LayoutInflater layoutInflater;
    private List<ReceivablesInfo> receivablesInfos;
    private Context context;

    public RpCodeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ReceivablesInfo> receivablesInfos) {
        this.receivablesInfos = receivablesInfos;
        notifyDataSetChanged();
    }

    @Override
    public RpCodeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_rp_code, parent, false);
        return new RpCodeView(view);
    }

    @Override
    public void onBindViewHolder(RpCodeView holder, int position) {
        ReceivablesInfo receivablesInfo = receivablesInfos.get(holder.getAdapterPosition());
        holder.bindData(receivablesInfo);
    }

    @Override
    public int getItemCount() {
        return receivablesInfos == null ? 0 : receivablesInfos.size();
    }

    public class RpCodeView extends RecyclerView.ViewHolder {

        public RpCodeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(ReceivablesInfo receivablesInfo) {

        }
    }
}
