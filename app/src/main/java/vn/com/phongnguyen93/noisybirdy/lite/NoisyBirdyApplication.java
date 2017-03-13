package vn.com.phongnguyen93.noisybirdy.lite;

import android.app.Application;
import android.content.Context;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import vn.com.phongnguyen93.noisybirdy.R;


/**
 * Created by phongnguyen on 3/4/17.
 */

public class NoisyBirdyApplication extends Application {
  private static String userScreenName;
  private static long userId;

  public static String getUserScreenName() {
    return userScreenName;
  }

  public static void setUserScreenName(String userScreenName) {
    NoisyBirdyApplication.userScreenName = userScreenName;
  }

  public static long getUserId() {
    return userId;
  }

  public static void setUserId(long userId) {
    NoisyBirdyApplication.userId = userId;
  }


  @Override public void onCreate() {
    super.onCreate();
    this.initTwitterKit();
  }

  private void initTwitterKit() {
    TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitter_api_key),
        getString(R.string.twitter_api_secret));
    Fabric.with(this, new Twitter(authConfig));
  }


}
