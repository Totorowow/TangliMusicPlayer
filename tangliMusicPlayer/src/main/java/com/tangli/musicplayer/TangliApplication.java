package com.tangli.musicplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import com.hjq.toast.Toaster;
import com.hjq.toast.style.WhiteToastStyle;
import com.tangli.musicplayer.greenDao.db.CoconutDao;
import com.tangli.musicplayer.greenDao.db.DaoMaster;
import com.tangli.musicplayer.greenDao.db.DaoSession;
import com.tangli.musicplayer.util.GreenDaoUtil;


public class TangliApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static DaoSession daoSession;



   public void onCreate(){
       super.onCreate();
       WhiteToastStyle whiteToastStyle=new WhiteToastStyle();
       Toaster.setStyle(whiteToastStyle);
       Toaster.init(this);
       initDatabase();
       GreenDaoUtil.init(getSongDao());
       context=getContext();
   }
   public void onConfigurationChanged(Configuration configuration) {
       super.onConfigurationChanged(configuration);
   }

    public static Context getContext() {
        return context;
    }


    public void initDatabase() {


        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "song_list.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static CoconutDao getSongDao() {
        return getDaoSession().getCoconutDao();
    }

}
