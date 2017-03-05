package vn.com.phongnguyen93.noisybirdy.data.cache;

import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Inject;
import javax.inject.Singleton;
import vn.com.phongnguyen93.noisybirdy.R;

/**
 * Created by phongnguyen on 3/5/17.
 */
@Singleton
public class RealmHelper {

  @Inject
  public RealmHelper() {
  }

  public Realm getRealmInstance(Context context){
    RealmConfiguration config =
        new RealmConfiguration.Builder(context).name(context.getString(R.string.app_name))
            .deleteRealmIfMigrationNeeded()
            .build();
    return Realm.getInstance(config);
  }
}
