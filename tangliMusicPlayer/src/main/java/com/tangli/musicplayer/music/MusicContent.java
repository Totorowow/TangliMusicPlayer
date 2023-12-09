
package com.tangli.musicplayer.music;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import com.hjq.toast.Toaster;
import com.tangli.musicplayer.R;
import com.tangli.musicplayer.TangliApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new MusicItem(R.drawable.camphor, "Again summer", "Music_Unlimited", 515,R.raw.again_summer_151646));
        ITEMS.add(new MusicItem(R.drawable.apartment_8318376_1920, "High In The Sky", "AlexiAction", 60,R.raw.high_in_the_sky_128400));
        ITEMS.add(new MusicItem(R.drawable.forest_party, "Indie Folk (Journey To The Sun)", "AlexGrohl", 215,R.raw.indie_folk_journey_to_the_sun_15044));
        ITEMS.add(new MusicItem(R.drawable.agriculture_8275498_1920, "Uplifting Corporate Pop Rock (It Is Time)", "MarkJuly", 242,R.raw.ukulele_fun_background));
        ITEMS.add(new MusicItem(R.drawable.stones_8249322_1920, "Something good can work", "Two Door Cinema Club", 164,R.raw.ukulele_fun_background));
    }


    public static class MusicItem {

        private final int mCover;
        private final String mTitle;
        private final String mArtist;
        private final long mDuration;

        private final int mResId;

        public MusicItem(int cover, String title, String artist, long duration,int resId) {
            mCover = cover;
            mTitle = title;
            mArtist = artist;
            mDuration = duration;
            mResId=resId;
        }

        public int getCover() {
            return mCover;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getArtist() {
            return mArtist;
        }

        public long getDuration() {
            return mDuration;
        }

        public int getResId() {
            return mResId;
        }
    }
}
