package vn.com.phongnguyen93.noisybirdy.domain;

import java.util.ArrayList;

/**
 * Class present a tweet object in domain layer
 *
 * Created by phongnguyen on 3/5/17.
 */

public class Tweet {

  private long id;
  private String date;
  private String text;
  private int fav_count;
  private int retweet_count;
  private String tweet_user_name;
  private String tweet_user_image;
  private String place;
  private ArrayList<String> urls;
  private ArrayList<String> hashtags;
  private ArrayList<String> image_urls;

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

  public ArrayList<String> getUrls() {
    return urls;
  }

  public void setUrls(ArrayList<String> urls) {
    this.urls = urls;
  }

  public ArrayList<String> getHashtags() {
    return hashtags;
  }

  public void setHashTags(ArrayList<String> hashtags) {
    this.hashtags = hashtags;
  }

  public ArrayList<String> getImageUrls() {
    return image_urls;
  }

  public void setImageUrls(ArrayList<String> image_urls) {
    this.image_urls = image_urls;
  }
}
