package com.nenggou.slsm.address.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.data.entity.AppstoreInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/23.
 */

public class AddressTelAdapter extends RecyclerView.Adapter<AddressTelAdapter.AddressTelView> {
    private LayoutInflater layoutInflater;
    private List<AppstoreInfo> appstoreInfos;
    private Context context;

    public AddressTelAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AppstoreInfo> appstoreInfos) {
        this.appstoreInfos = appstoreInfos;
        notifyDataSetChanged();
    }

    @Override
    public AddressTelView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_address_tel, parent, false);
        return new AddressTelView(view);
    }

    @Override
    public void onBindViewHolder(AddressTelView holder, int position) {
        AppstoreInfo appstoreInfo=appstoreInfos.get(holder.getAdapterPosition());
        holder.bindData(appstoreInfo);
    }

    @Override
    public int getItemCount() {
        return appstoreInfos == null ? 0 : appstoreInfos.size();
    }

    public class AddressTelView extends RecyclerView.ViewHolder {
        @BindView(R.id.head_photo)
        RoundedImageView headPhoto;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.tel)
        TextView tel;
        @BindView(R.id.address)
        TextView address;
        public AddressTelView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(AppstoreInfo appstoreInfo) {
            GlideHelper.load((Activity) context, appstoreInfo.getzPics(), R.mipmap.app_icon, headPhoto);
            name.setText(appstoreInfo.getTitle());
            tel.setText(appstoreInfo.getTelephone());
            address.setText(appstoreInfo.getAddress());
        }
    }
}
