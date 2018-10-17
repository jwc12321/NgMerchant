package com.nenggou.slsm.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.google.gson.Gson;
import com.nenggou.slsm.common.unit.CommonAppPreferences;
import com.nenggou.slsm.common.unit.StaticHandler;
import com.nenggou.slsm.common.voiceunit.MainHandlerConstant;
import com.nenggou.slsm.common.voiceunit.control.InitConfig;
import com.nenggou.slsm.common.voiceunit.control.MySyntherizer;
import com.nenggou.slsm.common.voiceunit.control.NonBlockSyntherizer;
import com.nenggou.slsm.common.voiceunit.listener.UiMessageListener;
import com.nenggou.slsm.common.voiceunit.util.OfflineResource;
import com.nenggou.slsm.push.PushInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JWC on 2018/10/15.
 */

public class VoiceService extends Service implements MainHandlerConstant {

    // ================== 初始化参数设置开始 ==========================
    /**
     * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
     * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
     */
    protected String appId = "14440687";

    protected String appKey = "emK9tXMtOFMXPg0ywiwNkjT6";

    protected String secretKey = "yOKXoeopUw5LCbDcuML3qKeYNPqUhMaE";

    private CommonAppPreferences commonAppPreferences;
    private String pushInfoStr;
    private static final Gson gson = new Gson();
    private String whatStart;
    private PushInfo pushInfo;
    private String time;
    private String name;
    private int currVolume;

    private Handler mHandler = new MyHandler(this);

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;

    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_MALE;

    // ===============初始化参数设置完毕，更多合成参数请至getParams()方法中设置 =================

    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;


    @Override
    public void onCreate() {
        super.onCreate();
        commonAppPreferences = new CommonAppPreferences(this);
        initialTts();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        // 设置初始化参数
        // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类
        SpeechSynthesizerListener listener = new UiMessageListener(mHandler);
        Map<String, String> params = getParams();
        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        synthesizer = new NonBlockSyntherizer(this, initConfig, mHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());
        return params;
    }

    protected OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(this, voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
            Log.e("VioceService", "【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }

    /**
     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
     * 获取音频流的方式见SaveFileActivity及FileSaveListener
     * 需要合成的文本text的长度不能超过1024个GBK字节。
     */
    private void speak(String text) {
        int result = synthesizer.speak(text);
        checkResult(result, "speak");
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            Log.e("VioceService", "error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pushInfoStr = commonAppPreferences.getPushInfoStr();
        whatStart = commonAppPreferences.getWhatStart();
        if (TextUtils.equals("1", whatStart)) {
            if (!TextUtils.isEmpty(pushInfoStr)) {
                pushInfo = gson.fromJson(pushInfoStr, PushInfo.class);
                setTts();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    private void setTts() {
        if (pushInfo != null) {
            if (!TextUtils.equals("0", pushInfo.getNowprice()) && !TextUtils.equals("0.0", pushInfo.getNowprice()) && !TextUtils.equals("0.00", pushInfo.getNowprice())) {
                openSpeaker();
                if (!TextUtils.equals(time, pushInfo.getPaytime()) || !TextUtils.equals(name, pushInfo.getUsername())) {
                    String pcash = "能购收到" + pushInfo.getAprice() + "现金" + pushInfo.getApower() + "能量";
                    speak(pcash);
                    time = pushInfo.getPaytime();
                    name = pushInfo.getUsername();
                }
            }
        }
    }

    public static class MyHandler extends StaticHandler<VoiceService> {

        public MyHandler(VoiceService target) {
            super(target);
        }

        @Override
        public void handle(VoiceService target, Message msg) {
            switch (msg.what) {
                case PRINT:
                    target.goVoice(msg);
                    break;
            }
        }
    }

    //引擎启动成功
    private void goVoice(Message msg) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (synthesizer != null) {
            synthesizer.release();
        }
    }

    /**
     * 打开扬声器
     */
    private void openSpeaker() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
    }
}
