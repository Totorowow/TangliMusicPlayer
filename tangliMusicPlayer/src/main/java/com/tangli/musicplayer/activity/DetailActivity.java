

package com.tangli.musicplayer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.Transition;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leaf.library.StatusBarUtil;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.view.MusicCoverView;
import com.tangli.musicplayer.view.ScrollTextView;
import com.tangli.musicplayer.view.TransitionAdapter;

import androidx.appcompat.content.res.AppCompatResources;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;

    private int clickedItem=-1;

    private TextView musicAuthor;
    private ImageView switchPlayState;
    private ImageView playNext;
    private ImageView closePage;
    private boolean isClosePage;
    private DetailActivity detailActivity;
    private ScrollTextView musicName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        mCoverView = findViewById(R.id.cover);
        musicAuthor=findViewById(R.id.music_author);
        switchPlayState=findViewById(R.id.switch_play_state);
        playNext=findViewById(R.id.next);
        closePage=findViewById(R.id.close_page);
        musicName=findViewById(R.id.music_name);
        detailActivity=this;

        StatusBarUtil.setColor(this, getColor(R.color.colorPrimaryDark));
        StatusBarUtil.setLightMode(this);
        Bundle bundle=getIntent().getBundleExtra("snow_bundle");
        clickedItem=bundle.getInt("clicked_item");
        //musicName.setSpeed(4);
        //musicName.setText("Ukulele Fun Background");
        //musicName.setTextColor(Color.WHITE);
        //musicName.setScrollForever(true);
        //musicName.setTextSize(18);
        musicName.setPauseScroll(true);
        switchPlayState.setOnClickListener(v -> changePlayState());
        playNext.setOnClickListener(v -> playNextSong());
        closePage.setOnClickListener(v -> {
            endAnimation();
        });



        mCoverView.setCallbacks(new MusicCoverView.Callbacks() {
            @Override
            public void onMorphEnd(MusicCoverView coverView) {
                // Nothing to do

            }

            @Override
            public void onRotateEnd(MusicCoverView coverView) {
                pause();
                if (isClosePage) {
                    mCoverView.stop();
                    supportFinishAfterTransition();

                }

            }
        });


        getWindow().getSharedElementEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                if (clickedItem!=-1) {
                   updateCurrentSong(clickedItem);
                }else {

                    Glide.with(detailActivity).load(R.drawable.main_cover).into(mCoverView);
                }
                play(clickedItem);
                mCoverView.start();
                musicName.setPauseScroll(false);
            }
        });

    }

    private void changePlayState(){
        isClosePage=false;
        if (mCoverView.isRunning()) {
            switchPlayState.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.ic_play_arrow));
            pause();
            mCoverView.stop();
            musicName.setPauseScroll(true);
        }else {
            switchPlayState.setImageDrawable(AppCompatResources.getDrawable(this,R.drawable.ic_pause_music));
            play(clickedItem);
            mCoverView.start();
            musicName.setPauseScroll(false);
        }
    }

    private void playNextSong(){
        if (clickedItem!=-1 && clickedItem < MusicContent.ITEMS.size()-1){
            clickedItem++;
            updateCurrentSong(clickedItem);
        }else if (clickedItem!=-1 && clickedItem == MusicContent.ITEMS.size()-1){
            clickedItem=-1;
            Glide.with(detailActivity).load(R.drawable.main_cover).into(mCoverView);
        }else {
            clickedItem=0;
            Glide.with(detailActivity).load(MusicContent.ITEMS.get(clickedItem).getCover()).into(mCoverView);
        }
        play(clickedItem);
        mCoverView.start();
    }

    private void updateCurrentSong(int item){
        musicName.setText(MusicContent.ITEMS.get(item).getTitle());
        musicAuthor.setText(MusicContent.ITEMS.get(item).getArtist());
        Glide.with(detailActivity).load(MusicContent.ITEMS.get(item).getCover()).into(mCoverView);
    }


    @Override
    public void onBackPressed() {
        endAnimation();
    }

    private void endAnimation(){
        isClosePage=true;
        mCoverView.stop();
        if (!mCoverView.isRunning()) {
            supportFinishAfterTransition();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
