package com.tangli.musicplayer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.tangli.musicplayer.util.LanguageUtil;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity{

    private Locale locale;
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageUtil.attachBaseContext(context));
        SharedPreferences preferences = getSharedPreferences("tangli_music_player", Context.MODE_PRIVATE);
        if (preferences.getInt("app_language",0)==0){
            locale=Locale.SIMPLIFIED_CHINESE;
        }else if (preferences.getInt("app_language",0)==2){
            locale=Locale.JAPANESE;
        }else if (preferences.getInt("app_language",0)==3){
            locale=Locale.KOREAN;
        }else{
            locale=Locale.ENGLISH;
        }
        LanguageUtil.changeAppLanguage(locale,this);
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