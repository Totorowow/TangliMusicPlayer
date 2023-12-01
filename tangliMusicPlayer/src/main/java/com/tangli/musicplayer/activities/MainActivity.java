
package com.tangli.musicplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.tangli.musicplayer.R;
import com.tangli.musicplayer.databinding.ActivityMainBinding;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.view.RecyclerViewAdapter;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends PlayerActivity {


    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.tracks);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(MusicContent.ITEMS));
    }

    public void onFabClick(View view) {
        //noinspection unchecked
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(mainBinding.cover, ViewCompat.getTransitionName(mainBinding.cover)),
                new Pair<>(mainBinding.lotusTitle.getRoot(), ViewCompat.getTransitionName(mainBinding.lotusTitle.getRoot())),
                new Pair<>(mainBinding.time, ViewCompat.getTransitionName(mainBinding.time)),
                new Pair<>(mainBinding.duration, ViewCompat.getTransitionName(mainBinding.duration)),
                new Pair<>(mainBinding.progress, ViewCompat.getTransitionName(mainBinding.progress)),
                new Pair<>(mainBinding.fab, ViewCompat.getTransitionName(mainBinding.fab)));
        ActivityCompat.startActivity(this, new Intent(this, DetailActivity.class), options.toBundle());
    }

}
