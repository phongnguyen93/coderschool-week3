package vn.com.phongnguyen93.noisybirdy.data.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;
import vn.com.phongnguyen93.noisybirdy.data.Utility;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;
import vn.com.phongnguyen93.noisybirdy.data.entity.mapper.TweetEntityJsonMapper;
import vn.com.phongnguyen93.noisybirdy.data.exception.NetworkConnectionException;

/**
 * Implementation of {@link RestApi} for retrieving data from network
 */
public class RestApiImpl extends OAuthBaseClient implements RestApi {

  private static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
  private static final String REST_URL = "https://api.twitter.com/1.1";
  private static final String REST_CONSUMER_KEY = "hNGEP8vTJLVoJrZu5IhqXW8Jv";
  private static final String REST_CONSUMER_SECRET =
      "M8NiuAnEBeStC2sGaYheirIHgfeWdlm9iwgiUSBFFsnlVS6bsU";
  private static final String REST_CALLBACK_URL = "x-oauthflow-twitter://noisybirdy.com";
  private static final String HOME_TIMELINE_URL = "statuses/home_timeline.json";
  private static final String POST_TWEET_URL = "statuses/update.json";
  private static final int DEFAULT_TWEET_LIMIT = 200;
  private static final int STATUS_SUCCESS = 200;

  private Context context;
  private TweetEntityJsonMapper entityJsonMapper;

  @Inject public RestApiImpl(Context context) {
    super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET,
        REST_CALLBACK_URL);

    if (context == null) {
      throw new IllegalArgumentException(
          RestApiImpl.class.getSimpleName() + " constructor params can not be null");
    }

    this.context = context.getApplicationContext();
  }

  public RestApiImpl(Context context, TweetEntityJsonMapper entityJsonMapper) {
    super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET,
        REST_CALLBACK_URL);

    if (context == null || entityJsonMapper == null) {
      throw new IllegalArgumentException(
          RestApiImpl.class.getSimpleName() + " constructor params can not be null");
    }

    this.context = context.getApplicationContext();
    this.entityJsonMapper = entityJsonMapper;
  }

  /**
   * Make network request to get user home timeline
   *
   * @param count limit the number of tweets retrieve
   * @param handler {@link AsyncHttpResponseHandler}  handle response from network
   */
  public void getHomeTimeline(int count, AsyncHttpResponseHandler handler) {
    String apiUrl = getApiUrl(HOME_TIMELINE_URL);
    RequestParams params = new RequestParams();
    params.put("count", String.valueOf(count));
    params.put("include_entities",String.valueOf(true));
    params.put("exclude_replies",String.valueOf(false));
    getClient().get(apiUrl, params, handler);
  }

  /**
   * Post a tweet
   *
   * @param text tweet content
   * @param handler {@link AsyncHttpResponseHandler} handle response from network
   */
  public void postTweet(String text, AsyncHttpResponseHandler handler) {
    String postTweetApiUrl = getApiUrl(POST_TWEET_URL);
    RequestParams params = new RequestParams();
    params.put("status", text);
    client.post(postTweetApiUrl, params, handler);
  }

  @Override public Observable<List<TweetEntity>> tweetEntityList(final int limit) {
    return Observable.create(new ObservableOnSubscribe<List<TweetEntity>>() {
      @Override public void subscribe(final ObservableEmitter<List<TweetEntity>> subscriber)
          throws Exception {
        //check internet connection before make request
        if (Utility.isThereInternetConnection(context)) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
              getHomeTimeline(limit*2, new JsonHttpResponseHandler() {
                @Override public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                  super.onSuccess(statusCode, headers, response);
                  if(statusCode==STATUS_SUCCESS){
                    try{
                      subscriber.onNext(entityJsonMapper.transformJSONtoCollection(response));
                      subscriber.onComplete();
                    }catch (JSONException ex){
                      subscriber.onError(ex);
                    }
                  }
                }

                @Override public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                    JSONArray errorResponse) {
                  super.onFailure(statusCode, headers, throwable, errorResponse);
                  subscriber.onError(throwable);
                }
              });
            }
          });
        } else {
          subscriber.onError(new NetworkConnectionException());
        }
      }
    });
  }

      @Override public Observable<Boolean> postTweet(final String text) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
          @Override public void subscribe(final ObservableEmitter<Boolean> subscriber)
              throws Exception {
            if (Utility.isThereInternetConnection(context)) {
              new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override public void run() {

                }
              });
            } else {
              subscriber.onError(new NetworkConnectionException());
            }
          }
        });
      }
  }
