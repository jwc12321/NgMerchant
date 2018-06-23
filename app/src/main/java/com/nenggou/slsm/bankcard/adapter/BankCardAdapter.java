package com.nenggou.slsm.bankcard.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.data.entity.BankCardInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/23.
 */

public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankCardView> {
    private LayoutInflater layoutInflater;
    private List<BankCardInfo> bankCardInfos;
    private String bankId;

    public void setData(List<BankCardInfo> bankCardInfos) {
        this.bankCardInfos = bankCardInfos;
        notifyDataSetChanged();
    }

    public BankCardAdapter(String bankId) {
        this.bankId = bankId;
    }

    @Override
    public BankCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = layoutInflater.inflate(R.layout.adapter_bank_card, parent, false);
        return new BankCardView(view);
    }

    @Override
    public void onBindViewHolder(BankCardView holder, int position) {
        final BankCardInfo bankCardInfo = bankCardInfos.get(holder.getAdapterPosition());
        holder.bindData(bankCardInfo);
        holder.itemBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.backBankId(bankCardInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankCardInfos == null ? 0 : bankCardInfos.size();
    }

    public class BankCardView extends RecyclerView.ViewHolder {
        @BindView(R.id.bank_select)
        ImageView bankSelect;
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.item_bank)
        RelativeLayout itemBank;

        public BankCardView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(BankCardInfo bankCardInfo) {
            String bankFour="";
            if (!TextUtils.isEmpty(bankCardInfo.getCardno())) {
                String cardNo = bankCardInfo.getCardno();
                bankFour=cardNo.substring(cardNo.length() - 4, cardNo.length());
            }
            if(TextUtils.isEmpty(bankFour)){
                bankName.setText(bankCardInfo.getCardbank());
            }else {
                bankName.setText(bankCardInfo.getCardbank()+"("+bankFour+")");
            }
            if (!TextUtils.isEmpty(bankId) && TextUtils.equals(bankId, bankCardInfo.getId())) {
                bankSelect.setSelected(true);
            } else {
                bankSelect.setSelected(false);
            }
        }
    }

    public interface ItemClickListener {
        void backBankId(BankCardInfo bankCardInfo);
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
