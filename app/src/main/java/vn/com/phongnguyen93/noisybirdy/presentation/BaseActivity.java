package vn.com.phongnguyen93.noisybirdy.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.ApplicationComponent;

/**
 * Created by phongnguyen on 3/4/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   * @return {@link ApplicationComponent}
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((NoisyBirdyApplication) getApplication()).getApplicationComponent();
  }
}
