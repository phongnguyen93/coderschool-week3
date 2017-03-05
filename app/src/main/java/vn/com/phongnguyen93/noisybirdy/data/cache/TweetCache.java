package vn.com.phongnguyen93.noisybirdy.data.cache;

import io.reactivex.Observable;
import java.util.List;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;

/**
 * An interface represent tweet entity cache
 * Created by phongnguyen on 3/5/17.
 */

public interface TweetCache {
  /**
   *Gets an {@link io.reactivex.Observable} which will emit list of {@link TweetEntity}.
   * @param limit limit number of entity retrieved
   * @return
   */
  Observable<List<TweetEntity>> getList(int limit);

  /**
   * Put list of element into local database
   * @param tweetEntityList list of tweet entity element to insert
   */
  void putList(List<TweetEntity> tweetEntityList);

  /**
   * Put an tweet entity into local database
   * @param entity Element to insert
   */
  void put(TweetEntity entity);


  /**
   * Evict all elements in local database
   */
  void evictAll();
}
