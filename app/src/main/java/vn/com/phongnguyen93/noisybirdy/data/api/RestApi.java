package vn.com.phongnguyen93.noisybirdy.data.api;

import io.reactivex.Observable;
import java.util.List;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;

/**
 * Interface for retrieving data from Twitter
 * Created by phongnguyen on 3/5/17.
 */

public interface RestApi {

  /**
   * Retrieve an {@link io.reactivex.Observable} which will emit list of {@link TweetEntity}
   *
   * @return List of {@link TweetEntity}
   */
  Observable<List<TweetEntity>> tweetEntityList(int limit);

  /**
   * Post a tweet to Twitter
   *
   * @param text content
   */
  Observable<Boolean> postTweet(String text);
}
