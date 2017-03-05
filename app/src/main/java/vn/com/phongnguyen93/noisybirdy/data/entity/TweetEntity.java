package vn.com.phongnguyen93.noisybirdy.data.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class present for TweetModel object in data layer
 *
 * Created by phongnguyen on 3/5/17.
 */

public class TweetEntity extends RealmObject {

  @PrimaryKey
  private long id;
  private String date;
  private String text;
  private String hashtag;
  private String url;
  private String image_url;
  private int fav_count;
  private int retweet_count;
  private String tweet_user_name;
  private String tweet_user_image;
  private String place;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getHashtag() {
    return hashtag;
  }

  public void setHashtag(String hashtag) {
    this.hashtag = hashtag;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getImageUrl() {
    return image_url;
  }

  public void setImageUrl(String image_url) {
    this.image_url = image_url;
  }

  public int getFavCount() {
    return fav_count;
  }

  public void setFavCount(int fav_count) {
    this.fav_count = fav_count;
  }

  public int getRetweetCount() {
    return retweet_count;
  }

  public void setRetweetCount(int retweet_count) {
    this.retweet_count = retweet_count;
  }

  public String getTweetUserName() {
    return tweet_user_name;
  }

  public void setTweetUserName(String tweet_user_name) {
    this.tweet_user_name = tweet_user_name;
  }

  public String getTweetUserImage() {
    return tweet_user_image;
  }

  public void setTweetUserImage(String tweet_user_image) {
    this.tweet_user_image = tweet_user_image;
  }

  public String getPlace() {
    return place;
  }

  public void setPlace(String place) {
    this.place = place;
  }
}
