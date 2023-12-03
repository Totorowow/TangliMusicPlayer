package com.tangli.musicplayer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.tangli.musicplayer.util.LanguageUtil;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageUtil.attachBaseContext(context));
        SharedPreferences preferences = getSharedPreferences("tangli_music_player", Context.MODE_PRIVATE);
        LanguageUtil.changeAppLanguage(preferences.getInt("app_language",0)==0? Locale.SIMPLIFIED_CHINESE :Locale.ENGLISH,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}