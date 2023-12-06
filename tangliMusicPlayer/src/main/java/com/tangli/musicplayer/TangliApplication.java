package com.tangli.musicplayer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.hjq.toast.Toaster;


public class TangliApplication extends Application {



   public void onCreate(){
       super.onCreate();
       Toaster.init(this);
   }
   public void onConfigurationChanged(Configuration configuration) {
       super.onConfigurationChanged(configuration);
   }
}
