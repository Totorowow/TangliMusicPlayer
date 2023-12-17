package com.tangli.musicplayer.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tangli.musicplayer.music.MusicContent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="song_list";
    public static final String TABLE_NAME="song_list";
    public static final String CREATE_SONG_LIST = "create table song_list ("
            + "id integer primary key autoincrement, "
            + "cover integer, "
            + "name text, "
            + "artist text, "
            + "duration integer, "
            + "resId integer, "
            + "isFavourite integer)";
    public List<MusicContent.MusicItem> songList;
    public MusicContent.MusicItem musicItem;
    private Context mContext;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SONG_LIST);
        intSongList(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void intSongList(SQLiteDatabase db){
        for (int i=0;i<MusicContent.ITEMS.size();i++){
            ContentValues values = new ContentValues();
            values.put("id", MusicContent.ITEMS.get(i).getSongId());
            values.put("cover", MusicContent.ITEMS.get(i).getCover());
            values.put("name", MusicContent.ITEMS.get(i).getTitle());
            values.put("artist", MusicContent.ITEMS.get(i).getArtist());
            values.put("duration", MusicContent.ITEMS.get(i).getDuration());
            values.put("resId", MusicContent.ITEMS.get(i).getResId());
            values.put("isFavourite", MusicContent.ITEMS.get(i).isFavourite() ? 0 : 1);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public List<MusicContent.MusicItem> getSongList(SQLiteDatabase db){
        songList=new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int cover = cursor.getInt(cursor.getColumnIndex("cover"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex("artist"));
                @SuppressLint("Range") int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                @SuppressLint("Range") int resID = cursor.getInt(cursor.getColumnIndex("resId"));
                @SuppressLint("Range") int isFavourite = cursor.getInt(cursor.getColumnIndex("isFavourite"));
                musicItem=new MusicContent.MusicItem(id,cover,name,artist,duration,resID, isFavourite == 0);
                songList.add(musicItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return songList;
    }
}
