package vn.com.phongnguyen93.noisybirdy.lite;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.services.AccountService;
import com.twitter.sdk.android.core.services.StatusesService;
import okhttp3.OkHttpClient;

/**
 * Created by phongnguyen on 3/13/17.
 */

public class TwitterClient extends TwitterApiClient {
  private static TwitterClient instance;
  private static  TwitterSession mSession;
  private static OkHttpClient mClient;

  public static void init(TwitterSession session, OkHttpClient client){
    mSession = session;
    mClient = client;
  }

  public synchronized static TwitterClient getInstance() {
    if(instance == null){
      instance = new TwitterClient(mSession,mClient);
    }
    return instance;
  }

  private TwitterClient(TwitterSession session, OkHttpClient client) {
    super(session, client);
  }

  public TwitterClient(TwitterSession session) {
    super(session);
  }

  private TwitterClient(OkHttpClient client) {
    super(client);
  }



  @Override public StatusesService getStatusesService() {
    return super.getStatusesService();
  }

  @Override public AccountService getAccountService() {
    return super.getAccountService();
  }

  public TwitterEndpoint getTwitterEndpoint(){
    return getService(TwitterEndpoint.class);
  }
}
