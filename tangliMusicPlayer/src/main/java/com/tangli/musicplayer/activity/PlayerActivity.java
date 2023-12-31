
package com.tangli.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.tangli.musicplayer.R;
import com.tangli.musicplayer.music.MusicContent;
import com.tangli.musicplayer.music.PlayerService;
import com.tangli.musicplayer.util.TinyDB;

import java.util.Random;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

public abstract class PlayerActivity extends BaseActivity {

    private PlayerService mService;
    private boolean mBound = false;
    private TextView mTimeView;
    private TextView mDurationView;
    private AppCompatSeekBar mProgressView;

    private MediaPlayer mediaPlayer;
    private int duration=108;
    private int resId;
    private TinyDB tinydb;


    @SuppressLint("HandlerLeak")
    private final Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int position = mService.getPosition();
            onUpdateProgress(position, duration);
            sendEmptyMessageDelayed(0, DateUtils.SECOND_IN_MILLIS);
        }
    };
    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            onBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName classname) {
            mBound = false;
            onUnbind();
        }
    };

    private void onUpdateProgress(int position, int duration) {
        if (mTimeView != null) {
            mTimeView.setText(DateUtils.formatElapsedTime(position));
        }
        if (mDurationView != null) {
            mDurationView.setText(DateUtils.formatElapsedTime(duration));
        }
        if (mProgressView != null) {
            mProgressView.setProgress(position);
            mProgressView.setMax(duration);


        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinydb= new TinyDB(this);

        // Bind to PlayerService
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mediaPlayer=new MediaPlayer();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mTimeView = findViewById(R.id.fence_time);
        mDurationView = findViewById(R.id.duration);
        mProgressView = findViewById(R.id.progress);
    }

    @Override
    protected void onDestroy() {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        releasePlayer();

        super.onDestroy();
    }

    private void onBind() {
        mUpdateProgressHandler.sendEmptyMessage(0);
    }

    private void onUnbind() {
        mUpdateProgressHandler.removeMessages(0);
    }

    public void play(int position) {
        if (tinydb.getBoolean("first_click")){
            tinydb.putBoolean("first_click",false);
            tinydb.putInt("last_position",position);
        }else {
            tinydb.putBoolean("first_click",true);
            tinydb.putInt("next_position",position);
        }
        if (position==-1){
            resId=R.raw.ukulele_fun_background;
        }else {
            resId=MusicContent.ITEMS.get(position).getResId();
        }
        releasePlayer();
        mediaPlayer=MediaPlayer.create(this,resId);

        duration=mediaPlayer.getDuration()/1000;
        mService.play(mediaPlayer, duration, tinydb.getInt("last_position") != tinydb.getInt("next_position"));
        mediaPlayer.setOnCompletionListener(mp -> {
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(mp);
            }else {
                replay(mp);
            }


        });


    }

    public void replay(MediaPlayer mediaPlayer){
        mService.rePlay(mediaPlayer);
    }



    public void pause() {
        mService.pause(mediaPlayer);
    }


    public void releasePlayer(){
        if (mediaPlayer!=null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    private MediaPlayer.OnCompletionListener mOnCompletionListener;

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        mOnCompletionListener = onCompletionListener;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;

    }

    public enum LoopMode{
        SINGLE,LIST,RANDOM
    }


}
