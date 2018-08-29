package com.nenggou.slsm.push;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.nenggou.slsm.common.unit.CommonAppPreferences;

import java.util.Locale;

/**
 * Created by JWC on 2018/8/23.
 */

public class TtsService extends Service implements TextToSpeech.OnInitListener{

    private TextToSpeech tts;
    private PushInfo pushInfo;
    private String time;
    private String name;
    private CommonAppPreferences commonAppPreferences;
    private String pushInfoStr;
    private static final Gson gson = new Gson();
    private int currVolume;
    private String whatStart;


    private void setTts(){
        if(pushInfo!=null) {
            if (!TextUtils.equals("0", pushInfo.getNowprice()) && !TextUtils.equals("0.0", pushInfo.getNowprice()) && !TextUtils.equals("0.00", pushInfo.getNowprice())) {
                if (!TextUtils.equals(time, pushInfo.getPaytime()) || !TextUtils.equals(name, pushInfo.getUsername())) {
                    openSpeaker();
                    String pcash = "能购收到" + pushInfo.getAprice() + "现金" + pushInfo.getApower() + "能量";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(pcash, TextToSpeech.QUEUE_ADD, null,"back");
                    }else {
                        tts.speak(pcash, TextToSpeech.QUEUE_ADD, null);
                    }
                    time = pushInfo.getPaytime();
                    name = pushInfo.getUsername();
                }
            }
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tts = new TextToSpeech(this, this);
        commonAppPreferences = new CommonAppPreferences(this);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                backCurrSpeaker();
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pushInfoStr = commonAppPreferences.getPushInfoStr();
        whatStart=commonAppPreferences.getWhatStart();
        if(TextUtils.equals("1",whatStart)) {
            if (!TextUtils.isEmpty(pushInfoStr)) {
                pushInfo = gson.fromJson(pushInfoStr, PushInfo.class);
                setTts();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        if(commonAppPreferences!=null){
            commonAppPreferences.setPushInfoStr("","0");
        }
        Intent intent = new Intent(getApplicationContext(),TtsService.class);
        startService(intent);
    }

    @Override
    public void onInit(int status) {
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

    /**
     * 打开扬声器
     */
    private void openSpeaker() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,maxVolume , 0);
    }

    private void backCurrSpeaker(){
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,currVolume , 0);
    }
}
