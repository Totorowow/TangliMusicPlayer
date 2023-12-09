package com.tangli.musicplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.hjq.toast.Toaster;


public class TangliApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;



   public void onCreate(){
       super.onCreate();
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
