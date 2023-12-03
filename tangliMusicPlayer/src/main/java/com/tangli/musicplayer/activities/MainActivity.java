
package com.tangli.musicplayer.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.navigation.NavigationView;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.databinding.ActivityMainBinding;
import com.tangli.musicplayer.databinding.TrackTitleBinding;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.adapter.RecyclerViewAdapter;
import com.tangli.musicplayer.util.LanguageUtil;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends PlayerActivity {


    private ActivityMainBinding mainBinding;
    private int selectItem=-1;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        preferences = getSharedPreferences("tangli_music_player", Context.MODE_PRIVATE);
        setContentView(mainBinding.getRoot());
        mainBinding.tracks.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.tracks.setAdapter(new RecyclerViewAdapter(MusicContent.ITEMS));
        mainBinding.toggleNavigation.setOnClickListener(v -> mainBinding.drawerLayout.openDrawer(Gravity.LEFT));
        mainBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.language_item:
                        setAppLanguage();
                        break;
                    default:
                        break;
                }
                mainBinding.drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    public void onFabClick(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(mainBinding.cover, ViewCompat.getTransitionName(mainBinding.cover)),
                new Pair<>(mainBinding.lotusTitle.getRoot(), ViewCompat.getTransitionName(mainBinding.lotusTitle.getRoot())),
                new Pair<>(mainBinding.time, ViewCompat.getTransitionName(mainBinding.time)),
                new Pair<>(mainBinding.duration, ViewCompat.getTransitionName(mainBinding.duration)),
                new Pair<>(mainBinding.progress, ViewCompat.getTransitionName(mainBinding.progress)),
                new Pair<>(mainBinding.fab, ViewCompat.getTransitionName(mainBinding.fab)));
        ActivityCompat.startActivity(this, new Intent(this, DetailActivity.class), options.toBundle());
    }

    private void setAppLanguage(){
        final String[] items = { getString(R.string.simplified_chinese),getString(R.string.english) };
        selectItem=preferences.getInt("app_language",0);
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle(getString(R.string.select_language));
        singleChoiceDialog.setSingleChoiceItems(items, selectItem,
                (dialog, which) -> selectItem = which);
        singleChoiceDialog.setPositiveButton(getString(R.string.confirm),
                (dialog, which) -> {
                    if (selectItem != -1) {

                        preferences.edit().putInt("app_language",selectItem).apply();
                        restartApp();
                    }
                });
        singleChoiceDialog.show();

    }

    private void restartApp() {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |   Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}
