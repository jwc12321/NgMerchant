package com.nenggou.slsm.common.unit;

import android.content.Context;
import android.content.SharedPreferences;

import com.nenggou.slsm.common.StaticData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JWC on 2018/6/25.
 */

public class CommonAppPreferences {
    SharedPreferences sharedPreferences;

    public CommonAppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(StaticData.PERSION_NENG_M, MODE_PRIVATE);
    }


    public void setToUpdate(String update){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.TO_UPDATE,update);
        editor.commit();
    }

    public String getToUpdate(){
        return sharedPreferences.getString(StaticData.TO_UPDATE,"");
    }
}
