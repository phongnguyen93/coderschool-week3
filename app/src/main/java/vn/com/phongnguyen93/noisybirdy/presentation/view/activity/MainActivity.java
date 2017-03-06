package vn.com.phongnguyen93.noisybirdy.presentation.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApiImpl;
import vn.com.phongnguyen93.noisybirdy.presentation.NoisyBirdyApplication;

public class MainActivity extends OAuthLoginActionBarActivity<RestApiImpl> {
  public static final String TAG = MainActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ((NoisyBirdyApplication)getApplication()).getClient(this).connect();
  }

  @Override public void onLoginSuccess() {
    Intent t =new Intent(this,TweetListActivity.class);
    t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(t);
    this.finish();
  }

  @Override public void onLoginFailure(Exception e) {
    Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
  }


}
