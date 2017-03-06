/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package vn.com.phongnguyen93.noisybirdy.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.presentation.di.HasComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.DaggerUserComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.UserComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.di.modules.UserModule;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;
import vn.com.phongnguyen93.noisybirdy.presentation.view.fragment.TweetListFragment;

/**
 * Activity that shows a list of Users.
 */
public class TweetListActivity extends BaseActivity
    implements HasComponent<UserComponent>, TweetListFragment.UserListListener {
  @BindView(R.id.toolbar) Toolbar toolbar;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, TweetListActivity.class);
  }

  private UserComponent userComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      toolbar.setLogo(R.drawable.ic_twitter_logo_white_on_image);
      toolbar.setTitle(getString(R.string.app_name));
      toolbar.setTitleTextColor(ContextCompat.getColor(this,android.R.color.white));
    }

    this.initializeInjector();
    if (savedInstanceState == null) {
      addFragment(R.id.fragmentContainer, new TweetListFragment());
    }
  }

  private void initializeInjector() {
    this.userComponent = DaggerUserComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .userModule(new UserModule(20))
        .build();
  }

  @Override public UserComponent getComponent() {
    return userComponent;
  }

  @Override public void onUserClicked(TweetModel tweetModel) {

  }
}
