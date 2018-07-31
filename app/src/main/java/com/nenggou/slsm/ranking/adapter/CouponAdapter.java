package com.nenggou.slsm.ranking.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.CouponInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/31.
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponView> {
    private LayoutInflater layoutInflater;
    private List<CouponInfo> couponInfos;

    public void setData(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
        notifyDataSetChanged();
    }

    @Override
    public CouponView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_coupon, parent, false);
        return new CouponView(view);
    }

    @Override
    public void onBindViewHolder(CouponView holder, int position) {
        final CouponInfo couponInfo=couponInfos.get(holder.getAdapterPosition());
        holder.bindData(couponInfo);
        holder.couponYLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.sendOutCoupon(couponInfo.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return couponInfos==null?0:couponInfos.size();
    }

    public class CouponView extends RecyclerView.ViewHolder {
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.explain)
        TextView explain;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.coupon_y_ll)
        RelativeLayout couponYLl;
        public CouponView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CouponInfo couponInfo){
            price.setText(couponInfo.getPrice());
            name.setText(couponInfo.getTitle());
            explain.setText("满"+couponInfo.getLeastCost()+"减"+couponInfo.getPrice());
            time.setText(couponInfo.getStarttime()+"-"+couponInfo.getEndtime());
        }
    }


    public interface ItemClickListener {
        void sendOutCoupon(String quanid);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
