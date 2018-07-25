package com.nenggou.slsm.cash.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.bill.ui.IntercourseRecordActivity;
import com.nenggou.slsm.cash.adapter.ReceivablesAdapter;
import com.nenggou.slsm.common.StaticData;
import com.nenggou.slsm.common.unit.CommonAppPreferences;
import com.nenggou.slsm.push.PushInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/25.
 */

public class ReceivablesActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.receivables_rv)
    RecyclerView receivablesRv;

    private List<PushInfo> pushInfos=new ArrayList<>();
    private PushInfo pushInfo;

    private ReceivablesAdapter receivablesAdapter;
    private CommonAppPreferences commonAppPreferences;
    private String pushInfoStr;
    private static final Gson gson = new Gson();


    public void setPushInfo(PushInfo addPushInfo) {
        boolean flag=false;
        for(int i=0;i<pushInfos.size();i++){
            PushInfo pushInfo=pushInfos.get(i);
            if(TextUtils.equals(pushInfo.getUsername(),addPushInfo.getUsername())&&TextUtils.equals(pushInfo.getUserid(),addPushInfo.getUserid())){
                flag=true;
                pushInfos.remove(pushInfo);
                pushInfos.add(addPushInfo);
            }
        }
        if(!flag){
            pushInfos.add(addPushInfo);
        }
        receivablesAdapter.setData(pushInfos);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        commonAppPreferences=new CommonAppPreferences(this);
        receivablesAdapter=new ReceivablesAdapter(this);
        receivablesRv.setAdapter(receivablesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pushInfoStr=commonAppPreferences.getPushInfoStr();
        if(!TextUtils.isEmpty(pushInfoStr)){
            pushInfo=gson.fromJson(pushInfoStr, PushInfo.class);
            setPushInfo(pushInfo);
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
