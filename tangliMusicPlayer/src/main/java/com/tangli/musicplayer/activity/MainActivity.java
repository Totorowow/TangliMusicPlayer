
package com.tangli.musicplayer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.leaf.library.StatusBarUtil;
import com.tangli.musicplayer.adapter.RecyclerViewAdapter;
import com.tangli.musicplayer.bean.Coconut;
import com.tangli.musicplayer.databinding.ActivityMainBinding;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.util.GreenDaoUtil;
import com.tangli.musicplayer.util.TinyDB;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.tangli.musicplayer.R.color;
import static com.tangli.musicplayer.R.id.about_item;
import static com.tangli.musicplayer.R.id.language_item;
import static com.tangli.musicplayer.R.string;


public class MainActivity extends PlayerActivity {


    private ActivityMainBinding mainBinding;
    private int selectItem=-1;
    private SharedPreferences preferences;
    private RecyclerViewAdapter recyclerViewAdapter;
    public List<Coconut> songList;
    private TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        preferences = getSharedPreferences("tangli_music_player", Context.MODE_PRIVATE);
        setContentView(mainBinding.getRoot());
        StatusBarUtil.setColor(this, getColor(color.colorBlack));
        StatusBarUtil.setLightMode(this);
        tinyDB=new TinyDB(this);
        initSongList();
        initSongAdapter();
        mainBinding.toggleNavigation.setOnClickListener(v -> mainBinding.drawerLayout.openDrawer(GravityCompat.START));
        mainBinding.fab.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        initNavigationView();
    }



    public void onFabClick(View view) {
        playSong(-1);
        //dbHelper.intSongList(dbHelper.getWritableDatabase());
        //recyclerViewAdapter.notifyDataSetChanged();
    }

    private void initSongList(){
        boolean notFirstInstall = tinyDB.getBoolean("not_first_install");
        if (!notFirstInstall) {
            GreenDaoUtil.insertInTxSong(MusicContent.ITEMS);
            tinyDB.putBoolean("not_first_install",true);
        }
        songList= GreenDaoUtil.getAllSongs();
    }

    private void initSongAdapter(){
        mainBinding.tracks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter=new RecyclerViewAdapter(songList);

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                playSong(position);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
        mainBinding.tracks.setAdapter(recyclerViewAdapter);

    }

    private void initNavigationView(){
        mainBinding.navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case language_item:
                    setAppLanguage();
                    break;
                case about_item:
                    startActivity(new Intent(this,AboutActivity.class));
                    break;
                default:
                    break;
            }
            mainBinding.drawerLayout.closeDrawers();
            return true;
        });
    }

    private void playSong(int position){
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(mainBinding.cover, ViewCompat.getTransitionName(mainBinding.cover)),
                new Pair<>(mainBinding.lotusTitle.getRoot(), ViewCompat.getTransitionName(mainBinding.lotusTitle.getRoot())),
                new Pair<>(mainBinding.time, ViewCompat.getTransitionName(mainBinding.time)),
                new Pair<>(mainBinding.duration, ViewCompat.getTransitionName(mainBinding.duration)),
                new Pair<>(mainBinding.musicProgress, ViewCompat.getTransitionName(mainBinding.musicProgress)),
                new Pair<>(mainBinding.fab, ViewCompat.getTransitionName(mainBinding.fab)));
        Bundle bundle=options.toBundle();
        Intent intent=new Intent(this, DetailActivity.class);
        Objects.requireNonNull(bundle).putInt("clicked_item",position);
        intent.putExtra("snow_bundle",bundle);
        ActivityCompat.startActivity(this, intent, bundle);
    }

    private void setAppLanguage(){
        final String[] items = { getString(string.simplified_chinese),getString(string.english),getString(string.japanese),getString(string.korean) };
        selectItem=preferences.getInt("app_language",0);
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle(getString(string.select_language));
        singleChoiceDialog.setSingleChoiceItems(items, selectItem,
                (dialog, which) -> selectItem = which);
        singleChoiceDialog.setPositiveButton(getString(string.confirm),
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
