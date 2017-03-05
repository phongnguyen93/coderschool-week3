package vn.com.phongnguyen93.noisybirdy.presentation;

import android.app.Application;
import android.content.Context;
import vn.com.phongnguyen93.noisybirdy.data.api.RestApiImpl;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.ApplicationComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.DaggerApplicationComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.di.modules.ApplicationModule;

/**
 * Created by phongnguyen on 3/4/17.
 */

public class NoisyBirdyApplication extends Application {
  private ApplicationComponent applicationComponent;

  public ApplicationComponent getApplicationComponent() {
    return this.applicationComponent;
  }

  @Override public void onCreate() {
    super.onCreate();
    this.initializeInjector();
  }

  public RestApiImpl getClient(Context context) {
    return (RestApiImpl) RestApiImpl.getInstance(RestApiImpl.class, context);
  }

  private void initializeInjector(){
    this.applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(new ApplicationModule(this))
        .build();
    }
}
