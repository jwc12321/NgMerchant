package com.nenggou.slsm.cash.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nenggou.slsm.R;
import com.nenggou.slsm.common.GlideHelper;
import com.nenggou.slsm.common.unit.FormatUtil;
import com.nenggou.slsm.push.PushInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/7/25.
 */

public class ReceivablesAdapter extends RecyclerView.Adapter<ReceivablesAdapter.ReceivablesView> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<PushInfo> pushInfos;

    public ReceivablesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PushInfo> pushInfos) {
        this.pushInfos = pushInfos;
        notifyDataSetChanged();
    }


    @Override
    public ReceivablesView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_receivables, parent, false);
        return new ReceivablesView(view);
    }

    @Override
    public void onBindViewHolder(ReceivablesView holder, int position) {
        holder.bindData(pushInfos.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return pushInfos==null?0:pushInfos.size();
    }

    public class ReceivablesView extends RecyclerView.ViewHolder {
        @BindView(R.id.head_photo)
        RoundedImageView headPhoto;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.bus_name)
        TextView busName;
        @BindView(R.id.in_payment)
        TextView inPayment;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.total_price)
        TextView totalPrice;
        @BindView(R.id.few_times)
        TextView fewTimes;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.after_payment_ll)
        LinearLayout afterPaymentLl;
        public ReceivablesView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(PushInfo pushInfo) {
            GlideHelper.load((Activity) context, pushInfo.getUseravatar(), R.mipmap.default_head_image_icon, headPhoto);
            userName.setText(pushInfo.getUsername());
            busName.setText("向"+pushInfo.getStorename()+"付款");
            if(TextUtils.equals("0",pushInfo.getNowprice())||TextUtils.equals("0.0",pushInfo.getNowprice())||TextUtils.equals("0.00",pushInfo.getNowprice())){
                inPayment.setVisibility(View.VISIBLE);
                afterPaymentLl.setVisibility(View.GONE);
            }else {
                inPayment.setVisibility(View.GONE);
                afterPaymentLl.setVisibility(View.VISIBLE);
                price.setText(pushInfo.getNowprice());
                totalPrice.setText("消费总额¥"+pushInfo.getPrice());
                fewTimes.setText("第"+pushInfo.getTotal()+"次消费");
                time.setText(FormatUtil.formatDateByLine(pushInfo.getPaytime()));
            }

        }
    }
}
