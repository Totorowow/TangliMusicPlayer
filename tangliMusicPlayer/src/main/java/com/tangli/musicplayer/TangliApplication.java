package com.tangli.musicplayer;

import android.app.Application;
import android.content.res.Configuration;


public class TangliApplication extends Application {

   public void onCreate(){
       super.onCreate();
   }
   public void onConfigurationChanged(Configuration configuration) {
       super.onConfigurationChanged(configuration);
   }
}
