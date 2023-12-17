

package com.tangli.musicplayer.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.toast.Toaster;
import com.leaf.library.StatusBarUtil;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.view.MusicCoverView;
import com.tangli.musicplayer.view.ScrollTextView;
import com.tangli.musicplayer.view.TransitionAdapter;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.FileProvider;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;

    private int clickedItem=-1;

    private TextView musicAuthor;
    private ImageView switchPlayState;
    private ImageView playNext;
    private ImageView closePage;
    private ImageView shareMusic;
    private boolean isClosePage;
    private DetailActivity detailActivity;
    private ScrollTextView musicName;
    private ImageView playLast;
    private ImageView repeat;
    private ImageView favourite;
    private LoopMode loopMode=LoopMode.SINGLE;
    private MusicContent.MusicItem musicItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
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
        initView();
        musicName.setPauseScroll(true);
        if (clickedItem==-1){
            musicItem=MusicContent.ITEMS.get(0);
        }else {
            musicItem=MusicContent.ITEMS.get(clickedItem);
        }
        if (musicItem.isFavourite()){
            favourite.setColorFilter(getColor(R.color.lightPurple));
        }else {
            favourite.setColorFilter(getColor(R.color.colorWhite));
        }
        switchPlayState.setOnClickListener(v -> changePlayState());
        playNext.setOnClickListener(v -> playNextSong());
        playLast.setOnClickListener(v -> playLastSong());
        closePage.setOnClickListener(v -> endAnimation());
        shareMusic.setOnClickListener(v -> shareSong());
        repeat.setOnClickListener(v -> modifyLoopMode());
        favourite.setOnClickListener(v -> addToFavourite());



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

    private void initView(){
        mCoverView = findViewById(R.id.cover);
        musicAuthor=findViewById(R.id.music_author);
        switchPlayState=findViewById(R.id.switch_play_state);
        playNext=findViewById(R.id.next);
        playLast=findViewById(R.id.previous);
        closePage=findViewById(R.id.close_page);
        shareMusic=findViewById(R.id.share_music);
        musicName=findViewById(R.id.music_name);
        repeat=findViewById(R.id.repeat);
        favourite=findViewById(R.id.favourite);
    }

    private void modifyLoopMode(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        Toaster.setGravity(Gravity.TOP,0, (int) (screenHeight*0.1));
        if (loopMode==LoopMode.SINGLE){
            loopMode=LoopMode.LIST;
            repeat.setImageDrawable(AppCompatResources.getDrawable(detailActivity,R.drawable.ic_repeat_white_24dp));
            Toaster.show(getString(R.string.list_loop));
        }else if (loopMode==LoopMode.LIST){
            loopMode=LoopMode.RANDOM;
            repeat.setImageDrawable(AppCompatResources.getDrawable(detailActivity,R.drawable.ic_shuffle_white_24dp));
            Toaster.show(getString(R.string.random_loop));

        }else {
            loopMode=LoopMode.SINGLE;
            repeat.setImageDrawable(AppCompatResources.getDrawable(detailActivity,R.drawable.ic_repeat_one));
            Toaster.show(getString(R.string.single_loop));
        }
        setOnCompletionListener(mp -> {
            if (loopMode== LoopMode.SINGLE){
                updateCurrentSong(clickedItem);
                play(clickedItem);
            }else if (loopMode==LoopMode.RANDOM){
                clickedItem=randInt(0,4);
                updateCurrentSong(clickedItem);
                play(clickedItem);
            }else {
                playNextSong();
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
            clickedItem=0;
            Glide.with(detailActivity).load(R.drawable.main_cover).into(mCoverView);
        }else {
            clickedItem=1;
            Glide.with(detailActivity).load(MusicContent.ITEMS.get(clickedItem).getCover()).into(mCoverView);
        }
        play(clickedItem);
        mCoverView.start();
    }

    private void playLastSong(){
        if (clickedItem!=-1 && clickedItem >1){
            clickedItem--;
            updateCurrentSong(clickedItem);
        }else if (clickedItem==1){
            clickedItem=0;
            Glide.with(detailActivity).load(R.drawable.main_cover).into(mCoverView);
        }else {
            clickedItem=MusicContent.ITEMS.size()-1;
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

    private void shareSong(){
        pause();
        mCoverView.stop();
        File file=new File(this.getCacheDir(),"rainbow.mp3");
        InputStream inputStream= getResources().openRawResource(musicItem.getResId());
        try
        {
            OutputStream out = Files.newOutputStream(file.toPath());
            byte[] buf = new byte[1024];
            int len;
            while ( (len = inputStream.read(buf, 0, buf.length)) != -1){
                out.write(buf, 0, len);
            }
            inputStream.close();
            out.close();
        }catch (Exception ignored) {}
        final Uri uri = FileProvider.getUriForFile(this, getPackageName()+".fileProvider", file);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, getString(R.string.share_to)));
    }

    private void addToFavourite(){
        if (musicItem.isFavourite()){
            musicItem.addFavourite(false);
            favourite.setColorFilter(getColor(R.color.colorWhite));
        }else {
            musicItem.addFavourite(true);
            favourite.setColorFilter(getColor(R.color.lightPurple));
        }

    }



    @Override
    public void onBackPressed() {
        endAnimation();
    }

    private void endAnimation(){
        isClosePage=true;
        mCoverView.stop();
        musicName.setPauseScroll(true);
        if (!mCoverView.isRunning()) {
            supportFinishAfterTransition();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
