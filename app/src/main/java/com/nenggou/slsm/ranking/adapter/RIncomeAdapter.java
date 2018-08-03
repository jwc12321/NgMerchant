package com.nenggou.slsm.ranking.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.data.entity.IRItemInfo;
import com.nenggou.slsm.data.entity.RIncomeItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/2.
 */

public class RIncomeAdapter extends RecyclerView.Adapter<RIncomeAdapter.RIncomeView> {

    private LayoutInflater layoutInflater;
    private List<RIncomeItemInfo> rIncomeItemInfos;

    public void setData(List<RIncomeItemInfo> rIncomeItemInfos) {
        this.rIncomeItemInfos = rIncomeItemInfos;
        notifyDataSetChanged();
    }

    public void addMore(List<RIncomeItemInfo> moreList) {
        int pos = rIncomeItemInfos.size();
        rIncomeItemInfos.addAll(moreList);
        notifyItemRangeInserted(pos, moreList.size());
    }

    @Override
    public RIncomeView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_r_income, parent, false);
        return new RIncomeView(view);
    }

    @Override
    public void onBindViewHolder(RIncomeView holder, int position) {
        final RIncomeItemInfo rIncomeItemInfo = rIncomeItemInfos.get(holder.getAdapterPosition());
        holder.bindData(rIncomeItemInfo);
        holder.recordItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.goShowMessage();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rIncomeItemInfos == null ? 0 : rIncomeItemInfos.size();
    }

    public class RIncomeView extends RecyclerView.ViewHolder {
        @BindView(R.id.business_name)
        TextView businessName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.rmb)
        TextView rmb;
        @BindView(R.id.rmb_number)
        TextView rmbNumber;
        @BindView(R.id.energy)
        TextView energy;
        @BindView(R.id.record_item)
        RelativeLayout recordItem;

        public RIncomeView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(RIncomeItemInfo rIncomeItemInfo) {
            String nikeName=rIncomeItemInfo.getNickname();
            if(!TextUtils.isEmpty(nikeName)) {
                if(nikeName.length()==1){
                    businessName.setText("*"+nikeName);
                }else if(nikeName.length()==11){
                    businessName.setText("*"+nikeName.substring(nikeName.length()-4,nikeName.length()));
                }else {
                    businessName.setText("*"+nikeName.substring(nikeName.length()-1,nikeName.length()));
                }
            }else {
                businessName.setText("*");
            }
            time.setText(FormatUtil.formatDateByLine(rIncomeItemInfo.getCreatedAt()));
            rmbNumber.setText(rIncomeItemInfo.getPrice());
            energy.setText(rIncomeItemInfo.getPower());
        }
    }

    public interface ItemClickListener {
        void goShowMessage();
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
