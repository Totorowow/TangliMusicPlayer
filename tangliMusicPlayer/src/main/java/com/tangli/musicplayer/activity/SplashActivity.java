package com.tangli.musicplayer.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.tangli.musicplayer.databinding.ActivitySplashBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding splashBinding;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(500);
                    runOnUiThread(() -> animateImage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void animateImage() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(splashBinding.splashImage, "scaleX", 1f,
                1.5f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(splashBinding.splashImage, "scaleY", 1f,
                1.5f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1500).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}