package com.nenggou.slsm.bankcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.PutForwardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/26.
 */

public class PutForwardAdapter extends RecyclerView.Adapter<PutForwardAdapter.PutForwardView> {

    private LayoutInflater layoutInflater;
    private List<PutForwardInfo> putForwardInfos;

    public void setData(List<PutForwardInfo> putForwardInfos){
        this.putForwardInfos=putForwardInfos;
        notifyDataSetChanged();
    }

    @Override
    public PutForwardView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_put_forward, parent, false);
        return new PutForwardView(view);
    }

    @Override
    public void onBindViewHolder(PutForwardView holder, int position) {
        PutForwardInfo putForwardInfo = putForwardInfos.get(holder.getAdapterPosition());
        holder.bindData(putForwardInfo);
    }

    @Override
    public int getItemCount() {
        return putForwardInfos == null ? 0 : putForwardInfos.size();
    }

    public class PutForwardView extends RecyclerView.ViewHolder implements PutForwardItemAdapter.ItemClickListener{
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.total_price)
        TextView totalPrice;
        @BindView(R.id.put_forward_rv)
        RecyclerView putForwardRv;

        PutForwardItemAdapter putForwardItemAdapter;
        public PutForwardView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            putForwardRv.setFocusableInTouchMode(false);
            putForwardRv.requestFocus();
            putForwardItemAdapter=new PutForwardItemAdapter();
            putForwardItemAdapter.setItemClickListener(this);
            putForwardRv.setAdapter(putForwardItemAdapter);
        }

        public void bindData(PutForwardInfo putForwardInfo) {
            time.setText(putForwardInfo.getDate());
            totalPrice.setText("提现¥"+putForwardInfo.getAllmoney());
            putForwardItemAdapter.setData(putForwardInfo.getPutForwardItems());
        }

        @Override
        public void returnId(String id) {
            if(muneItemClickListener!=null){
                muneItemClickListener.returnId(id);
            }
        }
    }

    public interface MuneItemClickListener {
        void returnId(String id);
    }

    private MuneItemClickListener muneItemClickListener;

    public void setItemClickListener(MuneItemClickListener muneItemClickListener) {
        this.muneItemClickListener = muneItemClickListener;
    }
}
