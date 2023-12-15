package com.tangli.musicplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.hjq.toast.Toaster;
import com.hjq.toast.style.WhiteToastStyle;


public class TangliApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;



   public void onCreate(){
       super.onCreate();
       WhiteToastStyle whiteToastStyle=new WhiteToastStyle();
       Toaster.setStyle(whiteToastStyle);
       Toaster.init(this);
       context=getContext();
   }
   public void onConfigurationChanged(Configuration configuration) {
       super.onConfigurationChanged(configuration);
   }

    public static Context getContext() {
        return context;
    }
}
