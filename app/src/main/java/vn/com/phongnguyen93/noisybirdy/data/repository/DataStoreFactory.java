package vn.com.phongnguyen93.noisybirdy.data.repository;

import android.content.Context;
import javax.inject.Inject;
import javax.inject.Singleton;
import vn.com.phongnguyen93.noisybirdy.data.Utility;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApi;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApiImpl;
import vn.com.phongnguyen93.noisybirdy.data.cache.TweetCache;
import vn.com.phongnguyen93.noisybirdy.data.entity.mapper.TweetEntityJsonMapper;

/**
 * Factory that create different implementation of {@link DataStore}
 * Created by phongnguyen on 3/5/17.
 */

@Singleton
public class DataStoreFactory {

  private final Context context;
  private final TweetCache tweetCache;

  @Inject
  public DataStoreFactory(Context context, TweetCache tweetCache) {
    this.context = context;
    this.tweetCache = tweetCache;
  }

  /**
   * Create {@link DataStore} implementation depend on {@param iUtility.isThereInternetConnection(context)sInitialize}
   *
   * @return implementation of {@link DataStore}
   */
  public DataStore create(){
    DataStore dataStore;
    if(Utility.isThereInternetConnection(context)){
      dataStore = createApiDataStore();
    }else
      dataStore = new LocalDataStore(tweetCache);
    return dataStore;
  }

  public DataStore createApiDataStore() {
    TweetEntityJsonMapper entityJsonMapper = new TweetEntityJsonMapper();
    RestApi restApi = new RestApiImpl(context,entityJsonMapper);
    return new ApiDataStore(restApi,tweetCache);
  }
}
