package com.tangli.musicplayer.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.tangli.musicplayer.R;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_about);
      final FrameLayout flHolder = findViewById(R.id.about);

      AboutBuilder builder = AboutBuilder.with(this)
              .setAppIcon(R.mipmap.ic_launcher)
              .setAppName(R.string.app_name)
              .setPhoto(R.drawable.avatar)
              .setCover(R.drawable.top_picture)
              .setLinksAnimated(true)
              .setDividerDashGap(13)
              .setName(getString(R.string.user_name))
              .setSubTitle(R.string.user_job)
              .setLinksColumnsCount(4)
              .setBrief(getString(R.string.personal_description))
              .addGitHubLink("Totorowow")
              .addFacebookLink("user")
              .addTwitterLink("user")
              .addInstagramLink("user")
              .addYoutubeChannelLink("user")
              .addEmailLink("88888@qq.com")
              .addSkypeLink("user")
              .addWebsiteLink("site")
              .addFiveStarsAction()
              .addMoreFromMeAction("Totorowow")
              .setVersionNameAsAppSubTitle()
              .addShareAction(R.string.app_name)
              .addUpdateAction()
              .setActionsColumnsCount(2)
              .addFeedbackAction("88888@qq.com")
              .addPrivacyPolicyAction("https://github.com/Totorowow/TangliMusicPlayer")
              //.addIntroduceAction((Intent) null)
              //.addHelpAction((Intent) null)
              //.addChangeLogAction((Intent) null)
              //.addRemoveAdsAction((Intent) null)
              //.addDonateAction((Intent) null)
              .setWrapScrollView(true)
              .setShowAsCard(true);
      AboutView view = builder.build();
      flHolder.addView(view);
   }
}
