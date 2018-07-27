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

    public void setPushInfoStr(String pushInfoStr){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.PUSH_INFO_STR,pushInfoStr);
        editor.commit();
    }
    public String getPushInfoStr(){
        return sharedPreferences.getString(StaticData.PUSH_INFO_STR,"");
    }

    public void setExplainCash(String eCash){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.EXPLAIN_CASH,eCash);
        editor.commit();
    }

    public String getExplainCash(){
        return sharedPreferences.getString(StaticData.EXPLAIN_CASH,"");
    }

}
