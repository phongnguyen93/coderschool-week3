package vn.com.phongnguyen93.noisybirdy.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.domain.Tweet;
import vn.com.phongnguyen93.noisybirdy.presentation.di.PerActivity;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;

/**
 *
 * Mapper class used to transform {@link Tweet} (in the domain layer) to {@link TweetModel} in the
 * presentation layer.
 *
 * Created by phongnguyen on 3/6/17.
 */

@PerActivity
public class TweetModelDataMapper {

  @Inject
  public TweetModelDataMapper() {
  }

  /**
   * Transform a {@link Tweet} into a new {@link TweetModel}
   *
   * @param tweet Object to be transformed
   * @return new {@link TweetModel}
   */
  public TweetModel transform(Tweet tweet){
    if (tweet == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }else{
      TweetModel tweetModel = new TweetModel();
      tweetModel.setId(tweet.getId());
      tweetModel.setUrls(tweet.getUrls());
      tweetModel.setPlace(tweet.getPlace());
      tweetModel.setDate(tweet.getDate());
      tweetModel.setFavCount(tweet.getFavCount());
      tweetModel.setRetweetCount(tweet.getRetweetCount());
      tweetModel.setHashTags(tweet.getHashtags());
      tweetModel.setImageUrls(tweet.getImageUrls());
      tweetModel.setTweetUserName(tweet.getTweetUserName());
      tweetModel.setTweetUserImage(tweet.getTweetUserImage());
      return tweetModel;
    }
  }

  /**
   * Transform a list of {@link Tweet} into a new list of {@link TweetModel}
   *
   * @param tweets list of {@link Tweet} to be transformed
   * @return new list of {@link TweetModel}
   */
  public ArrayList<TweetModel> transformCollection(ArrayList<Tweet> tweets){
    ArrayList<TweetModel> tweetModelCollection;

    if (tweets != null && !tweets.isEmpty()) {
      tweetModelCollection = new ArrayList<>();
      for(int i =0;i<tweets.size();i++){
        tweetModelCollection.add(transform(tweets.get(i)));
      }
    } else {
      tweetModelCollection = null;
    }

    return tweetModelCollection;
  }
}
