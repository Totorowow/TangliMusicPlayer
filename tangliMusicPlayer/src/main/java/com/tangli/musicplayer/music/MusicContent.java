
package com.tangli.musicplayer.music;

import com.tangli.musicplayer.R;
import com.tangli.musicplayer.bean.Coconut;

import java.util.ArrayList;
import java.util.List;

public class MusicContent {

    public static final List<Coconut> ITEMS = new ArrayList<>();

    static {
        ITEMS.add(new Coconut(0L,R.drawable.main_cover, "Ukulele Fun Background", "PaulYudin", 164,R.raw.ukulele_fun_background,false));
        ITEMS.add(new Coconut(1L,R.drawable.camphor, "Again summer", "Music_Unlimited", 515,R.raw.again_summer_151646,false));
        ITEMS.add(new Coconut(2L,R.drawable.apartment_8318376_1920, "High In The Sky", "AlexiAction", 60,R.raw.high_in_the_sky_128400,false));
        ITEMS.add(new Coconut(3L,R.drawable.forest_party, "Indie Folk (Journey To The Sun)", "AlexGrohl", 215,R.raw.indie_folk_journey_to_the_sun_15044,false));
        ITEMS.add(new Coconut(4L,R.drawable.agriculture_8275498_1920, "Uplifting Corporate Pop Rock (It Is Time)", "MarkJuly", 242,R.raw.uplifting_corporate_pop_rock_it_is_time_113871,false));
    }



}
