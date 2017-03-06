package vn.com.phongnguyen93.noisybirdy.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;

/**
 * Class use to transform JSONObject(JSONArray) to valid Entity object
 * Created by phongnguyen on 3/5/17.
 */

public class TweetEntityJsonMapper {

  //JSON object field keys
  private static final String FIELD_ID = "id";
  private static final String FIELD_DATE = "created_at";
  private static final String FIELD_TEXT = "text";
  private static final String FIELD_HASHTAGS = "hashtags";
  private static final String FIELD_HASHTAGS_TEXT = "text";
  private static final String FIELD_URLS = "urls";
  private static final String FIELD_URLS_URL = "url";
  private static final String FIELD_IMAGE_URLS = "extended_entities";
  private static final String FIELD_IMAGE_URLS_URL = "media_url";
  private static final String FIELD_RETWEET_COUNT = "retweet_count";
  private static final String FIELD_FAV_COUNT = "favorite_count";
  private static final String FIELD_TWEET_USER = "user";
  private static final String FIELD_TWEET_USER_NAME = "name";
  private static final String FIELD_TWEET_USER_IMAGE = "profile_image_url";
  private static final String FIELD_PLACE = "place";

  @Inject public TweetEntityJsonMapper() {
  }

  /**
   * Transform valid JSONObject to {@link TweetEntity}
   *
   * @param jsonObject JSONObject represent a tweet
   * @return {@link TweetEntity}
   * @throws JSONException if exceptions occur when convert json
   */
  public TweetEntity transformJSONtoEntity(JSONObject jsonObject) throws JSONException {
    if (jsonObject == null) throw new IllegalArgumentException("JSONObject can't be null");
    TweetEntity entity = new TweetEntity();
    entity.setId(jsonObject.getLong(FIELD_ID));
    entity.setDate(jsonObject.getString(FIELD_DATE));
    entity.setText(jsonObject.getString(FIELD_TEXT));
    entity.setRetweetCount(jsonObject.getInt(FIELD_RETWEET_COUNT));
    entity.setFavCount(jsonObject.getInt(FIELD_FAV_COUNT));
    entity.setPlace(jsonObject.getString(FIELD_PLACE));

    //get tweet's user name & image
    entity.setTweetUserName(
        jsonObject.getJSONObject(FIELD_TWEET_USER).getString(FIELD_TWEET_USER_NAME));
    entity.setTweetUserImage(
        jsonObject.getJSONObject(FIELD_TWEET_USER).getString(FIELD_TWEET_USER_IMAGE));

    //combine hashtag json array into 1 string to save in TweetEntity
    String hashtags = "";
    //JSONArray hashtagsArray = jsonObject.getJSONArray(FIELD_HASHTAGS);
    //if(hashtagsArray!=null && hashtagsArray.length()>0){
    //  for (int i = 0; i < hashtagsArray.length(); i++) {
    //    if (i == 0) {
    //      hashtags = hashtags.concat(hashtagsArray.getJSONObject(i).getString(FIELD_HASHTAGS_TEXT));
    //    } else {
    //      hashtags =
    //          hashtags.concat("," + hashtagsArray.getJSONObject(i).getString(FIELD_HASHTAGS_TEXT));
    //    }
    //  }
    //}
    entity.setHashtag(hashtags);


    //combine urls json array into 1 string to save in TweetEntity
    String urls = "";
    //JSONArray urlsArray = jsonObject.getJSONArray(FIELD_URLS);
    //if(urlsArray!=null && urlsArray.length()>0){
    //  for (int i = 0; i < urlsArray.length(); i++) {
    //    if (i == 0) {
    //      urls = urls.concat(urlsArray.getJSONObject(i).getString(FIELD_URLS_URL));
    //    } else {
    //      urls = urls.concat("," + urlsArray.getJSONObject(i).getString(FIELD_URLS_URL));
    //    }
    //  }
    //}
    entity.setUrl(urls);


    //combine image urls json array into 1 string to save in TweetEntity
    String imageUrls = "";
    //JSONArray imageUrlArray = jsonObject.getJSONArray(FIELD_IMAGE_URLS);
    //if(imageUrlArray!=null && imageUrlArray.length()>0){
    //  for (int i = 0; i < imageUrlArray.length(); i++) {
    //    if (i == 0) {
    //      imageUrls =
    //          imageUrls.concat(imageUrlArray.getJSONObject(i).getString(FIELD_IMAGE_URLS_URL));
    //    } else {
    //      imageUrls =
    //          imageUrls.concat("," + imageUrlArray.getJSONObject(i).getString(FIELD_IMAGE_URLS_URL));
    //    }
    //  }
    //}
    entity.setImageUrl(imageUrls);

    return entity;
  }

  /**
   * Transform valid JSONArray to List of {@link TweetEntity}
   *
   * @param jsonArray JSONArray represent list of tweets
   * @return List of valid {@link TweetEntity}
   */
  public List<TweetEntity> transformJSONtoCollection(JSONArray jsonArray) throws JSONException {
    if (jsonArray == null || jsonArray.length() == 0) {
      throw new IllegalArgumentException("Null or empty JSONArray");
    }
    ArrayList<TweetEntity> entityCollection = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
      TweetEntity entity = transformJSONtoEntity(jsonArray.getJSONObject(i));
      entityCollection.add(entity);
    }
    return entityCollection;
  }
}
