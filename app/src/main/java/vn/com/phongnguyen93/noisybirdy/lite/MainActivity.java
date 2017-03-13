package vn.com.phongnguyen93.noisybirdy.lite;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.lite.component.SlidingUpPanelLayout;
import vn.com.phongnguyen93.noisybirdy.lite.fragments.ProfileFragment;
import vn.com.phongnguyen93.noisybirdy.lite.fragments.TimelineFragment;


import static vn.com.phongnguyen93.noisybirdy.R.styleable.SlidingUpPanelLayout;

/**
 * Created by phongnguyen on 3/13/17.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = MainActivity.class.getSimpleName();

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.btn_post) FloatingActionButton fabPost;
  @BindView(R.id.sliding_layout) vn.com.phongnguyen93.noisybirdy.lite.component.SlidingUpPanelLayout slidingUpPanelLayout;
  @BindView(R.id.tv_char_count) TextView tvCharCount;
  @BindView(R.id.edt_compose) EditText edtCompose;
  @BindView(R.id.btn_submit) TextView btnSubmit;
  @BindView(R.id.compose_layout) LinearLayout composeLayout;

  private ResideMenu resideMenu;

  /**
   * Change the null parameter in {@code setContentView()}
   * to a layout resource {@code R.layout.example}
   */
  @Override protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate: hit");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
    toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu_white_24dp));
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
      }
    });
    initSideMenu();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.holder, TimelineFragment.newInstance())
        .commit();
  }

  private void initSideMenu() {
    // attach to current activity;
    resideMenu = new ResideMenu(this);
    resideMenu.setBackground(R.drawable.bg_2);
    resideMenu.attachToActivity(this);
    resideMenu.setScaleValue(0.7f);
    //resideMenu.addIgnoredView(findViewById(R.id.main_content));

    // create menu items;
    String titles[] = { "Home", "Profile" };
    int icon[] = { R.drawable.ic_home_white_24dp, R.drawable.ic_account_circle_white_24dp };

    for (int i = 0; i < titles.length; i++) {
      ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
      item.setOnClickListener(this);
      item.setTag(titles[i]);
      resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
    }
  }

  @Override public void onClick(View view) {
    if (view.getTag().equals("Home")) {
      resideMenu.closeMenu();
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.holder, TimelineFragment.newInstance())
          .commit();
      fabPost.show();
    } else if (view.getTag().equals("Profile")) {
      resideMenu.closeMenu();
      fabPost.hide();
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.holder,
              ProfileFragment.newInstance(NoisyBirdyApplication.getUserScreenName(),
                  NoisyBirdyApplication.getUserId()))
          .commit();
    }
  }

  @Override protected void onResume() {

    super.onResume();
  }

  private void showComposeLayout() {
    fabPost.hide();
    composeLayout.setVisibility(View.VISIBLE);
    slidingUpPanelLayout.setPanelState(
        vn.com.phongnguyen93.noisybirdy.lite.component.SlidingUpPanelLayout.PanelState.EXPANDED);

    edtCompose.requestFocus();
    displayInput();
  }

  private void hideComposeLayout() {
    hideInput(edtCompose);
    edtCompose.clearFocus();
    slidingUpPanelLayout.setPanelState(
        vn.com.phongnguyen93.noisybirdy.lite.component.SlidingUpPanelLayout.PanelState.COLLAPSED);
    composeLayout.setVisibility(View.GONE);
    fabPost.show();
  }

  @OnClick(R.id.btn_post) public void postTweet() {
    showComposeLayout();
  }

  @OnClick(R.id.btn_submit) public void submitTweet() {
    hideComposeLayout();
    TwitterClient.getInstance()
        .getTwitterEndpoint()
        .postTweet(edtCompose.getText().toString())
        .enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
              EventBus.getDefault().post(new TriggerRefreshEvent(true));
            }
          }

          @Override public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
        });
  }

  public void hideInput(View view) {
    try {
      if (view != null) {
        InputMethodManager imm =
            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
    } catch (NullPointerException ex) {

    }
  }

  @Override public void onBackPressed() {
    if (slidingUpPanelLayout.getPanelState() == vn.com.phongnguyen93.noisybirdy.lite.component.SlidingUpPanelLayout.PanelState.EXPANDED) {
      hideComposeLayout();
    } else {
      super.onBackPressed();
    }
  }

  public void displayInput() {
    InputMethodManager imgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    imgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
  }

  @Override protected void onPause() {
    Log.d(TAG, "onPause: hit");
    super.onPause();
  }

  @Override protected void onDestroy() {
    Log.d(TAG, "onDestroy: hit");
    super.onDestroy();
  }
}