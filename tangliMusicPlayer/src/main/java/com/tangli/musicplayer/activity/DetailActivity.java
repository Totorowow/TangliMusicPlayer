

package com.tangli.musicplayer.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.view.MusicCoverView;
import com.tangli.musicplayer.view.TransitionAdapter;

import androidx.appcompat.content.res.AppCompatResources;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;

    private int clickedItem=-1;
    private TextView musicName;
    private TextView musicAuthor;
    private ImageView switchPlayState;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);


        mCoverView = findViewById(R.id.cover);
        musicName=findViewById(R.id.music_name);
        musicAuthor=findViewById(R.id.music_author);
        switchPlayState=findViewById(R.id.switch_play_state);

        StatusBarUtil.setColor(this, getColor(R.color.colorPrimaryDark));
        StatusBarUtil.setLightMode(this);
        Bundle bundle=getIntent().getBundleExtra("snow_bundle");
        clickedItem=bundle.getInt("clicked_item");
        switchPlayState.setOnClickListener(v -> changePlayState());

        mCoverView.setCallbacks(new MusicCoverView.Callbacks() {
            @Override
            public void onMorphEnd(MusicCoverView coverView) {
                // Nothing to do

            }

            @Override
            public void onRotateEnd(MusicCoverView coverView) {
                pause();
            }
        });


        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTransitionEnd(Transition transition) {
                if (clickedItem!=-1) {
                    musicName.setText(MusicContent.ITEMS.get(clickedItem).getTitle());
                    musicAuthor.setText(MusicContent.ITEMS.get(clickedItem).getArtist());

                    Glide.with(DetailActivity.this).load(MusicContent.ITEMS.get(clickedItem).getCover()).into(mCoverView);
                }else {
                    Glide.with(DetailActivity.this).load(R.drawable.main_cover).into(mCoverView);
                }
                play(clickedItem);
                mCoverView.start();
            }
        });

    }

    private void changePlayState(){

        if (mCoverView.isRunning()) {
            switchPlayState.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.ic_play_arrow));
            mCoverView.stop();
        }else {
            switchPlayState.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.ic_pause_music));
            play(clickedItem);
            mCoverView.start();
        }
    }


    @Override
    public void onBackPressed() {
        //pause();
        mCoverView.stop();
        supportFinishAfterTransition();
    }



    @Override
    protected void onDestroy() {
        // Unbind from the service

        super.onDestroy();
    }

}
