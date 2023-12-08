
package com.tangli.musicplayer.music;

import com.tangli.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new MusicItem(R.drawable.camphor, "Again summer", "Music_Unlimited", 515,R.raw.again_summer_151646));
        ITEMS.add(new MusicItem(R.drawable.apartment_8318376_1920, "High In The Sky", "AlexiAction", 591,R.raw.high_in_the_sky_128400));
        ITEMS.add(new MusicItem(R.drawable.flowers_7911970_1920, "Indie Folk (Journey To The Sun)", "AlexGrohl", 215,R.raw.indie_folk_journey_to_the_sun_15044));
        ITEMS.add(new MusicItem(R.drawable.album_cover_soad, "Uplifting Corporate Pop Rock (It Is Time)", "MarkJuly", 242,R.raw.ukulele_fun_background));
        ITEMS.add(new MusicItem(R.drawable.album_cover_two_door, "Something good can work", "Two Door Cinema Club", 164,R.raw.ukulele_fun_background));
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
