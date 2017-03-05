package vn.com.phongnguyen93.noisybirdy.data.repository;

import io.reactivex.Observable;
import java.util.List;
import vn.com.phongnguyen93.noisybirdy.data.cache.TweetCache;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;

/**
 * A {@link DataStore} implementation to store data with Realm
 * Created by phongnguyen on 3/5/17.
 */

public class LocalDataStore implements DataStore {

  private final TweetCache tweetCache;

  /**
   * Construct a {@link LocalDataStore} to store data with Realm
   *
   * @param tweetCache a {@link TweetCache} to cache data retrieve from api
   */
  public LocalDataStore(TweetCache tweetCache) {
    this.tweetCache = tweetCache;
  }

  @Override public Observable<List<TweetEntity>> tweetEntityList(int limit) {
    return tweetCache.getList(limit);
  }

  @Override public Observable<Boolean> postTweet(String text) {
    throw new UnsupportedOperationException("Operation is not available!!!");
  }
}
