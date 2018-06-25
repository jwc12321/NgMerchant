package com.nenggou.slsm.common.unit;

import android.content.Context;
import android.content.SharedPreferences;

import com.nenggou.slsm.common.StaticData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JWC on 2018/4/25.
 */

public class PersionAppPreferences {
    SharedPreferences sharedPreferences;

    public PersionAppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(StaticData.PERSION_SPF_M_NAME, MODE_PRIVATE);
    }
    //个人信息
    public void setPersionInfo(String persionInfoStr) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.M_PERSION_INFO, persionInfoStr);
        editor.commit();
    }

    public String getPersionInfo() {
        return sharedPreferences.getString(StaticData.M_PERSION_INFO, "");
    }

}
