package vn.com.phongnguyen93.noisybirdy.data.repository;

import io.reactivex.Observable;
import java.util.List;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;

/**
 * Interface that represent a datastore where data is retrieved
 * Created by phongnguyen on 3/5/17.
 */

public interface DataStore {

  /**
   * Get an {@link Observable} which will emit a list of {@link TweetEntity}
   */
  Observable<List<TweetEntity>> tweetEntityList(int limit);

  /**
   * Get an {@link Observable} which will emit a result when post a tweet
   *
   * @param text tweet content
   */
  Observable<Boolean> postTweet(String text);
}
