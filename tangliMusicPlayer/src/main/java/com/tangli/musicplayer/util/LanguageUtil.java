package com.tangli.musicplayer.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

import androidx.annotation.RequiresApi;

public class LanguageUtil {



    public static void changeAppLanguage(Locale locale,Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
    }



    public static Context attachBaseContext(Context context){
       return updateResources(context);
    }

    private static Context updateResources(Context context) {
        Locale locale = Locale.getDefault();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        return context.createConfigurationContext(configuration);
        }
}
