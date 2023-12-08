

package com.tangli.musicplayer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hjq.toast.Toaster;
import com.leaf.library.StatusBarUtil;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.databinding.ContentDetailBinding;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.view.MusicCoverView;
import com.tangli.musicplayer.view.TransitionAdapter;

import androidx.core.content.res.ResourcesCompat;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;
    private FloatingActionButton fab;
    private int clickedItem=-1;
    private TextView musicName;
    private TextView musicAuthor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_detail);


        mCoverView = findViewById(R.id.cover);
        fab=findViewById(R.id.fab);
        musicName=findViewById(R.id.music_name);
        musicAuthor=findViewById(R.id.music_author);

        StatusBarUtil.setColor(this, getColor(R.color.colorPrimaryDark));
        StatusBarUtil.setLightMode(this);
        fab.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Bundle bundle=getIntent().getBundleExtra("snow_bundle");
        clickedItem=bundle.getInt("clicked_item");
        mCoverView.setCallbacks(new MusicCoverView.Callbacks() {
            @Override
            public void onMorphEnd(MusicCoverView coverView) {
                // Nothing to do
            }

            @Override
            public void onRotateEnd(MusicCoverView coverView) {
                supportFinishAfterTransition();
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                if (clickedItem!=-1) {
                    musicName.setText(MusicContent.ITEMS.get(clickedItem).getTitle());
                    musicAuthor.setText(MusicContent.ITEMS.get(clickedItem).getArtist());
                    Glide.with(DetailActivity.this).load(MusicContent.ITEMS.get(clickedItem).getCover()).into(mCoverView);
                }else {
                    Glide.with(DetailActivity.this).load(R.drawable.main__cover).into(mCoverView);
                }
                play(clickedItem);
                mCoverView.start();
            }
        });

    }

    @Override
    public void onBackPressed() {
        onFabClick(null);
    }

    public void onFabClick(View view) {
        pause();
        mCoverView.stop();
    }



    @Override
    protected void onDestroy() {
        // Unbind from the service

        super.onDestroy();
    }

}
