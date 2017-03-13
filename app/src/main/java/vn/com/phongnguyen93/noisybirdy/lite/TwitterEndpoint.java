package vn.com.phongnguyen93.noisybirdy.lite;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import java.util.ArrayList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by phongnguyen on 3/13/17.
 */

public interface TwitterEndpoint {
  @POST("1.1/statuses/update.json")
  @FormUrlEncoded
  Call<ResponseBody> postTweet(@Field("status") String status);

  @GET("1.1/users/show.json")
  Call<User> getUser(@Query("user_id") long id , @Query("screen_name") String screenName);
}
