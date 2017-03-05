package vn.com.phongnguyen93.noisybirdy.data.entity.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;
import vn.com.phongnguyen93.noisybirdy.domain.Tweet;

/**
 * Class handle transform {@link TweetEntity} into {@link Tweet}
 * Created by phongnguyen on 3/5/17.
 */

@Singleton public class TweetEntityDataMapper {

  @Inject public TweetEntityDataMapper() {
  }

  /**
   * Transform a {@link TweetEntity} into a new {@link Tweet} object
   *
   * @param entity a valid {@link TweetEntity} to be transformed
   * @return {@link Tweet} if {@link TweetEntity} is valid otherwise null
   */
  public Tweet transformEntityToObject(TweetEntity entity) {
    Tweet tweet = null;
    if (entity == null) {
      return tweet;
    } else {
      tweet = new Tweet();
      tweet.setId(entity.getId());
      tweet.setText(entity.getText());
      tweet.setDate(entity.getDate());
      tweet.setPlace(entity.getPlace());
      tweet.setFavCount(entity.getFavCount());
      tweet.setRetweetCount(entity.getRetweetCount());
      tweet.setTweetUserImage(entity.getTweetUserImage());
      tweet.setTweetUserName(entity.getTweetUserName());
      tweet.setImageUrls(new ArrayList<String>(Arrays.asList(entity.getImageUrl().split(","))));
      tweet.setHashTags(new ArrayList<String>(Arrays.asList(entity.getHashtag().split(","))));
      tweet.setUrls(new ArrayList<String>(Arrays.asList(entity.getUrl().split(","))));
      return tweet;
    }
  }

  /**
   * Transform list of {@link TweetEntity} into a new list of {@link Tweet}
   *
   * @param entityList list of {@link TweetEntity} to be transformed
   * @return new list of {@link Tweet} if list of {@link TweetEntity} is valid otherwise null
   */
  public List<Tweet> transformEntityToCollection(List<TweetEntity> entityList) {
    if (entityList != null && entityList.size() > 0) {
      ArrayList<Tweet> tweets = new ArrayList<>();
      for (int i = 0; i < entityList.size(); i++) {
        tweets.add(transformEntityToObject(entityList.get(i)));
      }
      return tweets;
    } else {
      return null;
    }
  }
}
