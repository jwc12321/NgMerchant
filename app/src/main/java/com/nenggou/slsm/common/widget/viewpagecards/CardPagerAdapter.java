package com.nenggou.slsm.common.widget.viewpagecards;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nenggou.slsm.R;
import com.nenggou.slsm.common.unit.QrCodeUtil;
import com.nenggou.slsm.data.entity.AppstoreInfo;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<AppstoreInfo> appstoreInfos;
    private float mBaseElevation;

    public CardPagerAdapter(List<AppstoreInfo> appstoreInfos) {
        this.appstoreInfos = appstoreInfos;
        mViews = new ArrayList<>();
        for (int i = 0; i < appstoreInfos.size(); i++) {
            mViews.add(null);
        }
    }

    public void addCardItem(AppstoreInfo AppstoreInfo) {
        mViews.add(null);
        appstoreInfos.add(AppstoreInfo);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return appstoreInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter_rp_code, container, false);
        container.addView(view);
        bind(appstoreInfos.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    TextView shopName;
    Button switchBt;
    ImageView qrCodeIv;
    RelativeLayout itemIncome;
    TextView cashIncome;
    TextView energyIncome;
    RelativeLayout itemEvaluate;

    private void bind(AppstoreInfo appstoreInfo, View view) {
        shopName = view.findViewById(R.id.shop_name);
        switchBt = view.findViewById(R.id.switch_bt);
        qrCodeIv = view.findViewById(R.id.qr_code);
        itemIncome = view.findViewById(R.id.item_income);
        cashIncome = view.findViewById(R.id.cash_income);
        energyIncome=view.findViewById(R.id.energy_income);
        itemEvaluate = view.findViewById(R.id.item_evaluate);
        shopName.setText(appstoreInfo.getTitle());
        if(appstoreInfo.getShouru()!=null) {
            cashIncome.setText("¥ " + appstoreInfo.getShouru().getAllmoney());
            String allPower=appstoreInfo.getShouru().getAllpower();
            if(TextUtils.isEmpty(allPower)||TextUtils.equals("0",allPower)
                    ||TextUtils.equals("0.00",allPower)){
                energyIncome.setVisibility(View.GONE);
            }else {
                energyIncome.setVisibility(View.VISIBLE);
                energyIncome.setText(allPower);
            }
        }else {
            cashIncome.setText("¥ 0");
            energyIncome.setVisibility(View.GONE);
        }
        String qrCodeUrl= "ngapp::"+appstoreInfo.getId()+"&&"+appstoreInfo.getTitle()+"&&"+appstoreInfo.getzPics();
        Bitmap bitmap = QrCodeUtil.createQRCode(qrCodeUrl, 200, 200);
        qrCodeIv.setImageBitmap(bitmap);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface ItemClickListener {
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
