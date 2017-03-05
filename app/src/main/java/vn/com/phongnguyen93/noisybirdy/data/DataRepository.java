package vn.com.phongnguyen93.noisybirdy.data;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;
import vn.com.phongnguyen93.noisybirdy.data.entity.mapper.TweetEntityDataMapper;
import vn.com.phongnguyen93.noisybirdy.data.repository.DataStore;
import vn.com.phongnguyen93.noisybirdy.data.repository.DataStoreFactory;
import vn.com.phongnguyen93.noisybirdy.domain.Tweet;
import vn.com.phongnguyen93.noisybirdy.domain.repository.Repository;

/**
 * Created by phongnguyen on 3/5/17.
 */
@Singleton
public class DataRepository implements Repository {

  private final DataStoreFactory dataStoreFactory;
  private final TweetEntityDataMapper entityDataMapper;

  @Inject
  public DataRepository(DataStoreFactory dataStoreFactory, TweetEntityDataMapper entityDataMapper) {
    this.dataStoreFactory = dataStoreFactory;
    this.entityDataMapper = entityDataMapper;
  }

  @Override public Observable<List<Tweet>> getTimeline(int limit) {
    return dataStoreFactory.create().tweetEntityList(limit).map(new Function<List<TweetEntity>, List<Tweet>>() {
      @Override public List<Tweet> apply(List<TweetEntity> tweetEntityList) throws Exception {
        return entityDataMapper.transformEntityToCollection(tweetEntityList);
      }
    });
  }

  @Override public Observable<Boolean> postTweet(String text) {
    final DataStore dataStore = dataStoreFactory.createApiDataStore();
    return dataStore.postTweet(text);
  }
}
