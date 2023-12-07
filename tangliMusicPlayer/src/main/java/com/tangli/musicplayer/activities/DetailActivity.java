

package com.tangli.musicplayer.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leaf.library.StatusBarUtil;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.databinding.ContentDetailBinding;
import com.tangli.musicplayer.view.MusicCoverView;
import com.tangli.musicplayer.view.TransitionAdapter;

public class DetailActivity extends PlayerActivity {

    private MusicCoverView mCoverView;
    private FloatingActionButton fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_detail);


        mCoverView = findViewById(R.id.cover);
        fab=findViewById(R.id.fab);

        StatusBarUtil.setColor(this, getColor(R.color.colorPrimaryDark));
        StatusBarUtil.setLightMode(this);
        fab.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);


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
                play();
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
