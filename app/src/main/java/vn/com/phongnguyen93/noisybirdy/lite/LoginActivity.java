package vn.com.phongnguyen93.noisybirdy.lite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import vn.com.phongnguyen93.noisybirdy.R;


public class LoginActivity extends AppCompatActivity {
  public static final String TAG = LoginActivity.class.getSimpleName();
  @BindView(R.id.btn_login) TwitterLoginButton btnLogin;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    btnLogin.setCallback(new Callback<TwitterSession>() {
      @Override public void success(Result<TwitterSession> result) {
        Log.d(TAG,result.data.getUserName());
        NoisyBirdyApplication.setUserId(result.data.getUserId());
        NoisyBirdyApplication.setUserScreenName(result.data.getUserName());
        setUpApiClient();
        Intent t =new Intent(LoginActivity.this,MainActivity.class);
        t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(t);
      }

      @Override public void failure(TwitterException exception) {
        Log.d(TAG,exception.getMessage());
      }
    });
  }

  private void setUpApiClient() {
    final TwitterSession activeSession = TwitterCore.getInstance()
        .getSessionManager().getActiveSession();

    // example of custom OkHttpClient with logging HttpLoggingInterceptor
    final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    final OkHttpClient customClient = new OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor).build();

    // pass custom OkHttpClient into TwitterApiClient and add to TwitterCore
    final TwitterClient customApiClient;
    if (activeSession != null) {
      TwitterClient.init(activeSession, customClient);
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    btnLogin.onActivityResult(requestCode,resultCode,data);
  }
}
