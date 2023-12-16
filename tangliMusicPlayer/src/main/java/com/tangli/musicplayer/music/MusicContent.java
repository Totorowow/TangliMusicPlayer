
package com.tangli.musicplayer.music;

import com.tangli.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<MusicItem> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new MusicItem(R.drawable.main_cover, "Ukulele Fun Background", "PaulYudin", 164,R.raw.ukulele_fun_background,false));
        ITEMS.add(new MusicItem(R.drawable.camphor, "Again summer", "Music_Unlimited", 515,R.raw.again_summer_151646,false));
        ITEMS.add(new MusicItem(R.drawable.apartment_8318376_1920, "High In The Sky", "AlexiAction", 60,R.raw.high_in_the_sky_128400,false));
        ITEMS.add(new MusicItem(R.drawable.forest_party, "Indie Folk (Journey To The Sun)", "AlexGrohl", 215,R.raw.indie_folk_journey_to_the_sun_15044,false));
        ITEMS.add(new MusicItem(R.drawable.agriculture_8275498_1920, "Uplifting Corporate Pop Rock (It Is Time)", "MarkJuly", 242,R.raw.uplifting_corporate_pop_rock_it_is_time_113871,false));
    }


    public static class MusicItem {

        private final int mCover;
        private final String mTitle;
        private final String mArtist;
        private final long mDuration;

        private final int mResId;
        private boolean isFavourite;

        public MusicItem(int cover, String title, String artist, long duration,int resId,boolean favourite) {
            mCover = cover;
            mTitle = title;
            mArtist = artist;
            mDuration = duration;
            mResId=resId;
            isFavourite=favourite;
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

        public boolean isFavourite() {
            return isFavourite;
        }
        public void addFavourite(boolean favourite) {
            this.isFavourite=favourite;
        }
    }
}
