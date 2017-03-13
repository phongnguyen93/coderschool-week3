package vn.com.phongnguyen93.noisybirdy.lite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.lite.fragments.ProfileFragment;

public class ProfileActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    if (getIntent() != null) {
      String screenName = getIntent().getStringExtra("screenName");
      long id = getIntent().getLongExtra("id", 0);
      if (id != 0 && screenName != null) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.holder, ProfileFragment.newInstance(screenName, id))
            .commit();
      }
    }
  }
}
