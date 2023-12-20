package com.tangli.musicplayer.util;

import android.content.Context;

import com.tangli.musicplayer.bean.Coconut;
import com.tangli.musicplayer.greenDao.db.CoconutDao;


import java.util.List;

public class GreenDaoUtil {

    private Context mContext;
    private static CoconutDao baseSongDao ;

    public GreenDaoUtil(Context context) {
        this.mContext = context;
        //baseSongDao = TangliApplication.getDaoSession().getBaseSongDao();
    }

    public static void init(CoconutDao songDao){
        if (baseSongDao != null) {
            return;
        }
        baseSongDao = songDao;

    }


    public static List<Coconut> getAllSongs() {
        return baseSongDao.loadAll();
    }

    public static void insertSong(Coconut song) {
        baseSongDao.insert(song);
    }
    public static void insertInTxSong(List<Coconut> songList) {
        baseSongDao.insertInTx(songList);

    }

    public static void updateSong(Coconut song) {
        baseSongDao.update(song);
    }

    public static void deleteSong(Coconut song) {
        baseSongDao.deleteByKey(song.getId());
    }
}
 
