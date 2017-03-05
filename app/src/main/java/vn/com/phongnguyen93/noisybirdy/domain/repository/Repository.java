package vn.com.phongnguyen93.noisybirdy.domain.repository;

import io.reactivex.Observable;
import java.util.List;
import vn.com.phongnguyen93.noisybirdy.domain.Tweet;

/**
 * Interface represent a Repository for getting {@link Tweet} data
 *
 * Created by phongnguyen on 3/4/17.
 */

public interface Repository {

  /**
   * Get {@link Observable} which will emit a List of {@link Tweet} object
   */
  Observable<List<Tweet>> getTimeline(int limit);

  /**
   * Get {@link Observable} which will emit a result when post a tweet
   */
  Observable<Boolean> postTweet(String text);
}
