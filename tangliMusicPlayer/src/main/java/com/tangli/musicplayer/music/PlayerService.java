
package com.tangli.musicplayer.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service {

    private static final String TAG = PlayerService.class.getSimpleName();
    private static final int DURATION = 108;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private Worker mWorker;
    private int musicDuration=-1;



    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mWorker != null) {
            mWorker.interrupt();
        }
        return super.onUnbind(intent);
    }

    public void play(MediaPlayer mediaPlayer,int duration,boolean restart) {
        boolean playingFinished = getPosition() == getMusicDuration();  // Determine whether playback is complete
        if ((restart || playingFinished) && mWorker!=null){
            mWorker=null;
        }

        if (mWorker == null) {
            mWorker = new Worker();
            mWorker.start();
        } else {
            mWorker.doResume();
            if (getPosition()>0){
                mediaPlayer.seekTo(getPosition()*1000);
            }
            Log.e(TAG,"Last play progress:"+getPosition());
        }
        mWorker.setDuration(duration);
        mediaPlayer.start();
    }

    public boolean isPlaying() {
        return mWorker != null && mWorker.isPlaying();
    }

    public void pause(MediaPlayer mediaPlayer) {
        if (mWorker != null) {
            mWorker.doPause();
            if (mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }
    }
    public void rePlay(MediaPlayer mediaPlayer) {
        if (mWorker != null) {
            mWorker=null;
        }
        mWorker = new Worker();
        mWorker.start();
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
    }

    public int getPosition() {
        if (mWorker != null) {
            return mWorker.getPosition();
        }
        return 0;
    }



    public int getMusicDuration() {
        if (mWorker != null) {
            return mWorker.getDuration();
        }else {
            return -1;
        }
    }

    private static class Worker extends Thread {

        boolean paused = false;
        int position = 0;
        int musicDuration=-1;

        @Override
        public void run() {

            try {
                while (position < (musicDuration!=-1?musicDuration:DURATION)) {
                    sleep(1000);
                    if (!paused) {
                        position++;
                    }
                }
            } catch (InterruptedException e) {
                Log.d(TAG, "Player unbounded");
            }
        }

        void doResume() {
            paused = false;
        }

        void doPause() {
            paused = true;
        }

        boolean isPlaying() {
            return !paused;
        }

        int getPosition() {
            return position;
        }
        int getDuration() {
            return musicDuration;
        }
        void setDuration(int duration) {
            musicDuration=duration;
        }
    }

    /**
     * Class used for the client Binder. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {

        public PlayerService getService() {
            // Return this instance of PlayerService so clients can call public methods
            return PlayerService.this;
        }
    }
}
