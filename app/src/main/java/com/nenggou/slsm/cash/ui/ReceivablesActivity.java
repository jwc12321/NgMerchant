package com.nenggou.slsm.cash.ui;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nenggou.slsm.BaseActivity;
import com.nenggou.slsm.R;
import com.nenggou.slsm.cash.adapter.ReceivablesAdapter;
import com.nenggou.slsm.common.unit.CommonAppPreferences;
import com.nenggou.slsm.push.PushInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JWC on 2018/7/25.
 */

public class ReceivablesActivity extends BaseActivity implements TextToSpeech.OnInitListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.receivables_rv)
    RecyclerView receivablesRv;

    private TextToSpeech tts;

    private List<PushInfo> pushInfos = new ArrayList<>();
    private PushInfo pushInfo;

    private ReceivablesAdapter receivablesAdapter;
    private CommonAppPreferences commonAppPreferences;
    private String pushInfoStr;
    private static final Gson gson = new Gson();

    private String time;
    private String name;


    public void setPushInfo(PushInfo addPushInfo) {
        boolean flag = false;
        for (int i = 0; i < pushInfos.size(); i++) {
            PushInfo pushInfo = pushInfos.get(i);
            if (TextUtils.equals(pushInfo.getUsername(), addPushInfo.getUsername()) && TextUtils.equals(pushInfo.getUserid(), addPushInfo.getUserid())) {
                flag = true;
                pushInfos.remove(pushInfo);
                pushInfos.add(addPushInfo);
            }
        }
        if (!flag) {
            pushInfos.add(addPushInfo);
        }
        receivablesAdapter.setData(pushInfos);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        //初始化TTS
        tts = new TextToSpeech(this, this);
        commonAppPreferences = new CommonAppPreferences(this);
        receivablesAdapter = new ReceivablesAdapter(this);
        receivablesRv.setAdapter(receivablesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pushInfoStr = commonAppPreferences.getPushInfoStr();
        if (!TextUtils.isEmpty(pushInfoStr)) {
            pushInfo = gson.fromJson(pushInfoStr, PushInfo.class);
            setPushInfo(pushInfo);
            if (!TextUtils.equals("0", pushInfo.getNowprice())) {
                if (!TextUtils.equals(time, pushInfo.getPaytime()) || !TextUtils.equals(name, pushInfo.getUsername())) {
                    tts.speak("能购收到" + pushInfo.getNowprice() + "元", TextToSpeech.QUEUE_ADD, null);
                    time = pushInfo.getPaytime();
                    name = pushInfo.getUsername();
                }
            }
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
        if (tts != null) {
            tts.shutdown();
        }
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

    @Override
    public void onInit(int status) {
        // 判断是否转化成功
        if (status == TextToSpeech.SUCCESS) {
            //默认设定语言为中文，原生的android貌似不支持中文。
            int result = tts.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                //不支持中文就将语言设置为英文
                tts.setLanguage(Locale.US);
            }
        }
    }

}
