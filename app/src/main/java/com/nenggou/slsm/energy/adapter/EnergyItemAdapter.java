package com.nenggou.slsm.energy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.common.widget.list.MoreLoadable;
import com.nenggou.slsm.common.widget.list.Refreshable;
import com.nenggou.slsm.data.entity.EnergyDetailInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/5/3.
 */

public class EnergyItemAdapter extends RecyclerView.Adapter<EnergyItemAdapter.EnergyItemView> implements Refreshable<EnergyDetailInfo>, MoreLoadable<EnergyDetailInfo> {
    private LayoutInflater layoutInflater;
    private List<EnergyDetailInfo> energyDetailInfos;
    private String type;
    private Context context;

    public EnergyItemAdapter(Context context,String type) {
        this.context=context;
        this.type = type;
    }

    public void setData(List<EnergyDetailInfo> energyDetailInfos) {
        this.energyDetailInfos = energyDetailInfos;
        notifyDataSetChanged();
    }

    @Override
    public EnergyItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_energy_item, parent, false);
        return new EnergyItemView(view);
    }

    @Override
    public void onBindViewHolder(EnergyItemView holder, int position) {
        final EnergyDetailInfo energyDetailInfo = energyDetailInfos.get(holder.getAdapterPosition());
        holder.bindData(energyDetailInfo);
    }

    @Override
    public int getItemCount() {
        return energyDetailInfos == null ? 0 : energyDetailInfos.size();
    }

    @Override
    public void refresh(List<EnergyDetailInfo> list) {
        this.energyDetailInfos = list;
        notifyDataSetChanged();
    }

    @Override
    public void addMore(List<EnergyDetailInfo> list) {
        int pos = energyDetailInfos.size();
        energyDetailInfos.addAll(list);
        notifyItemRangeInserted(pos, list.size());
    }

    public class EnergyItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.head_photo)
        RoundedImageView headPhoto;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.price)
        TextView price;

        public EnergyItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(EnergyDetailInfo energyDetailInfo) {
            GlideHelper.load((Activity) context, energyDetailInfo.getAvatar(), R.mipmap.app_icon, headPhoto);
            name.setText(energyDetailInfo.getNickname());
            time.setText(FormatUtil.formatDateByLine(energyDetailInfo.getCreated_at()));
            if(TextUtils.equals("0",type)){
                price.setText("+"+energyDetailInfo.getPower());
            }else {
                price.setText("-"+energyDetailInfo.getPower());
            }
        }
    }
}
