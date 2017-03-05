package vn.com.phongnguyen93.noisybirdy.presentation;

import android.os.Bundle;
import android.widget.Toast;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApiImpl;

public class MainActivity extends OAuthLoginActionBarActivity<RestApiImpl> {
  public static final String TAG = MainActivity.class.getSimpleName();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ((NoisyBirdyApplication)getApplication()).getClient(this).connect();

  }

  @Override public void onLoginSuccess() {
    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
  }

  @Override public void onLoginFailure(Exception e) {
    Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
  }


}
