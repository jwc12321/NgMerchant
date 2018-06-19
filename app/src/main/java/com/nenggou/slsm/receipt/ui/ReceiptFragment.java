package com.nenggou.slsm.receipt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenggou.slsm.BaseFragment;
import com.nenggou.slsm.R;

import butterknife.ButterKnife;

/**
 * Created by JWC on 2018/6/19.
 */

public class ReceiptFragment extends BaseFragment {
    public ReceiptFragment() {
    }
    public static ReceiptFragment newInstance(){
        ReceiptFragment receiptFragment=new ReceiptFragment();
        return receiptFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_receipt,container,false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    private boolean isFirstLoad = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstLoad) {
            if (getUserVisibleHint()) {
                isFirstLoad = false;
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}