package vn.com.phongnguyen93.noisybirdy.data.cache;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.data.entity.TweetEntity;
import vn.com.phongnguyen93.noisybirdy.domain.exception.EmptyTweetListException;

/**
 * {@link TweetCache} implementation
 * Created by phongnguyen on 3/5/17.
 */

public class TweetCacheImpl implements TweetCache {

  private Realm realm;

  /**
   * Constructor of the class {@link TweetCacheImpl}
   *
   * @param context A
   * @param realmHelper {@link RealmHelper} provide an instance to access Realm
   */
  @Inject public TweetCacheImpl(Context context, RealmHelper realmHelper) {
    if (context == null || realmHelper == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.realm = realmHelper.getRealmInstance(context);
  }

  @Override public Observable<List<TweetEntity>> getList(final int limit) {
    return Observable.create(new ObservableOnSubscribe<List<TweetEntity>>() {
      @Override public void subscribe(final ObservableEmitter<List<TweetEntity>> subscriber)
          throws Exception {
        realm.executeTransactionAsync(new Realm.Transaction() {
          @Override public void execute(Realm realm) {
            RealmResults<TweetEntity> results = realm.where(TweetEntity.class).findAll();
            if (results != null && results.size() > 0) {
              ArrayList<TweetEntity> entityCollection = new ArrayList<TweetEntity>();
              for (int i = 0; i < results.size(); i++) {
                if (i < limit) entityCollection.add(results.get(i));
              }

              subscriber.onNext(results);
              subscriber.onComplete();
            } else {
              subscriber.onError(new EmptyTweetListException());
            }
          }
        }, new Realm.Transaction.OnError() {
          @Override public void onError(Throwable error) {
            subscriber.onError(error);
          }
        });
      }
    });
  }

  @Override public void putList(final List<TweetEntity> tweetEntityList) {
    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        realm.copyToRealm(tweetEntityList);
      }
    });
  }

  @Override public void put(final TweetEntity entity) {
    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override public void execute(Realm realm) {
        realm.copyToRealm(entity);
      }
    });
  }

  @Override public void evictAll() {
    realm.deleteAll();
  }
}
