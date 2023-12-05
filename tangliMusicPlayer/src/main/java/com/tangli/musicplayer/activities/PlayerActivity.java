
package com.tangli.musicplayer.activities;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.tangli.musicplayer.R;
import com.tangli.musicplayer.music.PlayerService;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

public abstract class PlayerActivity extends BaseActivity {

    private PlayerService mService;
    private boolean mBound = false;
    private TextView mTimeView;
    private TextView mDurationView;
    private AppCompatSeekBar mProgressView;

    @SuppressLint("HandlerLeak")
    private final Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            final int position = mService.getPosition();
            final int duration = mService.getDuration();
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
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Bind to PlayerService
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mTimeView = findViewById(R.id.time);
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
        super.onDestroy();
    }

    private void onBind() {
        mUpdateProgressHandler.sendEmptyMessage(0);
    }

    private void onUnbind() {
        mUpdateProgressHandler.removeMessages(0);
    }

    public void play() {
        mService.play();
    }

    public void pause() {
        mService.pause();
    }

}
