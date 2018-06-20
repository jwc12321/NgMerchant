package com.nenggou.slsm.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenggou.slsm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/20.
 */

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeView> {
    private LayoutInflater layoutInflater;

    public void setData() {
        notifyDataSetChanged();
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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class IncomeView extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.cash)
        TextView cash;
        @BindView(R.id.rmb)
        TextView rmb;
        @BindView(R.id.energy)
        TextView energy;

        public IncomeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData() {

        }
    }
}
