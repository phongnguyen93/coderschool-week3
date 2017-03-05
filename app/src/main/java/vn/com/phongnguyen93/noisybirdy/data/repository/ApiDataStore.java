package vn.com.phongnguyen93.noisybirdy.data.repository;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.List;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApi;
import vn.com.phongnguyen93.noisybirdy.data.cache.TweetCache;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;

/**
 * A {@link DataStore} implementation to retrieve data from api
 * Created by phongnguyen on 3/5/17.
 */

public class ApiDataStore implements DataStore {

  private final RestApi restApi;
  private final TweetCache tweetCache;
  private final Consumer<List<TweetEntity>> saveToCache =
      new Consumer<List<TweetEntity>>() {
        @Override public void accept(List<TweetEntity> tweetEntityList) throws Exception {
          if(tweetEntityList!=null && tweetEntityList.size()>0){
            ApiDataStore.this.tweetCache.evictAll();
            ApiDataStore.this.tweetCache.putList(tweetEntityList);
          }
        }
      };

  /**
   * Construct {@link ApiDataStore} based on connection to Twitter API to retrieve data
   *
   * @param restApi {@link RestApi} implement to use
   * @param tweetCache {@link TweetCache} to cache data after retrieve from api
   */
  public ApiDataStore(RestApi restApi, TweetCache tweetCache) {
    this.tweetCache = tweetCache;
    this.restApi = restApi;
  }

  @Override public Observable<List<TweetEntity>> tweetEntityList(int limit) {
    return restApi.tweetEntityList(limit).doOnNext(saveToCache);
  }

  @Override public Observable<Boolean> postTweet(String text) {
    return restApi.postTweet(text);
  }
}
