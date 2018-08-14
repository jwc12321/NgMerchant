package com.nenggou.slsm.referee.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.RdListInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/8/14.
 */

public class RdItemAdapter extends RecyclerView.Adapter<RdItemAdapter.RdItemView> {
    private LayoutInflater layoutInflater;
    private List<RdListInfo> rdListInfos;

    @Override
    public RdItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_rd_item, parent, false);
        return new RdItemView(view);
    }

    public void setData(List<RdListInfo> rdListInfos) {
        this.rdListInfos = rdListInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<RdListInfo> moreList) {
        int pos = rdListInfos.size();
        rdListInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public void onBindViewHolder(RdItemView holder, int position) {
        final RdListInfo rdListInfo = rdListInfos.get(holder.getAdapterPosition());
        holder.bindData(rdListInfo);
        holder.rdItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.goRRecord(rdListInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rdListInfos == null ? 0 : rdListInfos.size();
    }

    public class RdItemView extends RecyclerView.ViewHolder {

        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.rmb)
        TextView rmb;
        @BindView(R.id.rmb_number)
        TextView rmbNumber;
        @BindView(R.id.energy)
        TextView energy;
        @BindView(R.id.rd_item)
        RelativeLayout rdItem;
        public RdItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RdListInfo rdListInfo) {
            time.setText(FormatUtil.formatDateByLine(rdListInfo.getCreatedAt()));
            String nikeName=rdListInfo.getNickname();
            if(!TextUtils.isEmpty(nikeName)) {
                if(nikeName.length()==1){
                    name.setText("*"+nikeName);
                }else if(nikeName.length()==11){
                    name.setText("*"+nikeName.substring(nikeName.length()-4,nikeName.length()));
                }else {
                    name.setText("*"+nikeName.substring(nikeName.length()-1,nikeName.length()));
                }
            }else {
                name.setText("*");
            }
            rmbNumber.setText(rdListInfo.getPrice());
            energy.setText(rdListInfo.getPower());
        }
    }


    public interface ItemClickListener {
        void goRRecord(String id);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
