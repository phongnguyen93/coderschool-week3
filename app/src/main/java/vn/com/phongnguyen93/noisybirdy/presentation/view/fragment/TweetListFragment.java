/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package vn.com.phongnguyen93.noisybirdy.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.presentation.NoisyBirdyApplication;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.UserComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;
import vn.com.phongnguyen93.noisybirdy.presentation.presenter.TweetListPresenter;
import vn.com.phongnguyen93.noisybirdy.presentation.view.TweetListView;
import vn.com.phongnguyen93.noisybirdy.presentation.view.adapter.TweetAdapter;
import vn.com.phongnguyen93.noisybirdy.presentation.view.component.SlidingUpPanelLayout;

/**
 * Fragment that shows a list of Users.
 */
public class TweetListFragment extends BaseFragment
    implements TweetListView, SwipeRefreshLayout.OnRefreshListener {

  private static final int DEFAULT_TWEET_LENGTH = 140;

  @Override public void onRefresh() {
    loadTweetList();
  }

  /**
   * Interface for listening user list events.
   */
  public interface UserListListener {
    void onUserClicked(final TweetModel userModel);
  }

  @Inject TweetListPresenter tweetListPresenter;
  TweetAdapter tweetAdapter;

  @BindView(R.id.rv_tweet) RecyclerView rvTweetList;
  @BindView(R.id.rl_progress) RelativeLayout rl_progress;
  @BindView(R.id.rl_retry) RelativeLayout rl_retry;
  @BindView(R.id.bt_retry) Button bt_retry;
  @BindView(R.id.btn_post) FloatingActionButton fabPost;
  @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingUpPanelLayout;
  @BindView(R.id.tv_char_count) TextView tvCharCount;
  @BindView(R.id.edt_compose) EditText edtCompose;
  @BindView(R.id.btn_submit) TextView btnSubmit;
  @BindView(R.id.swipeLayout) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.compose_layout) LinearLayout composeLayout;

  private ArrayList<TweetModel> data;
  private UserListListener userListListener;

  @Inject public TweetListFragment() {
    setRetainInstance(true);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof UserListListener) {
      this.userListListener = (UserListListener) activity;
    }
  }

  private void showComposeLayout() {
    fabPost.hide();
    composeLayout.setVisibility(View.VISIBLE);
    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

    edtCompose.requestFocus();
    displayInput();
  }

  private void hideComposeLayout() {
    hideInput(edtCompose);
    edtCompose.clearFocus();
    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    composeLayout.setVisibility(View.GONE);
    fabPost.show();
  }

  @OnClick(R.id.btn_post) public void postTweet() {
    showComposeLayout();
  }

  @OnClick(R.id.btn_submit) public void submitTweet() {
    hideComposeLayout();
    tweetListPresenter.postTweet(((NoisyBirdyApplication) getActivity().getApplication()),
        edtCompose.getText().toString());
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_tweet_list, container, false);
    ButterKnife.bind(this, fragmentView);

    refreshLayout.setOnRefreshListener(this);

    edtCompose.setFilters(new InputFilter[] { new InputFilter.LengthFilter(DEFAULT_TWEET_LENGTH) });
    edtCompose.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        tvCharCount.setText(
            String.format(getString(R.string.base_char_count), edtCompose.getText().length()));
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });

    setupSlidingPanel();
    setupRecyclerView();
    return fragmentView;
  }

  private void setupSlidingPanel() {
    slidingUpPanelLayout.setTouchEnabled(false);
    slidingUpPanelLayout.setAnchorPoint(0.3f);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.tweetListPresenter.setView(this);
  }

  @Override public void onResume() {
    super.onResume();
    this.tweetListPresenter.resume();
    loadTweetList();
  }

  @Override public void onPause() {
    super.onPause();
    this.tweetListPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    rvTweetList.setAdapter(null);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    this.tweetListPresenter.destroy();
  }

  @Override public void onDetach() {
    super.onDetach();
    this.userListListener = null;
  }

  @Override public void showLoading() {
    refreshLayout.setRefreshing(true);
    //this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    refreshLayout.setRefreshing(false);
    // this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void renderTweetList(ArrayList<TweetModel> userModelCollection) {
    if (userModelCollection != null) {
      this.data = userModelCollection;
      tweetAdapter.setData(data);
    }
  }

  @Override public void viewTweet(TweetModel tweetModel) {
    if (this.userListListener != null) {
      this.userListListener.onUserClicked(tweetModel);
    }
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return this.getActivity().getApplicationContext();
  }

  private void setupRecyclerView() {
    if (rvTweetList != null) {
      tweetAdapter = new TweetAdapter(getActivity().getBaseContext());
      rvTweetList.setLayoutManager(new LinearLayoutManager(context()));
      rvTweetList.setAdapter(tweetAdapter);
      rvTweetList.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            fabPost.show();
          }
          super.onScrollStateChanged(recyclerView, newState);
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            hideComposeLayout();
          }
          if (dy > 0 || dy < 0 && fabPost.isShown()) {
            if (getActivity().getActionBar() != null) getActivity().getActionBar().hide();
            fabPost.hide();
          }else if(rvTweetList.getScrollY()==0){
            if (getActivity().getActionBar() != null) getActivity().getActionBar().show();
          }

        }
      });
    }
  }

  /**
   * Loads all users.
   */
  private void loadTweetList() {
    this.tweetListPresenter.initialize();
  }

  @OnClick(R.id.bt_retry) void onButtonRetryClick() {
    TweetListFragment.this.loadTweetList();
  }

  public void hideInput(View view) {
    try {
      if (view != null) {
        InputMethodManager imm =
            (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
    } catch (NullPointerException ex) {

    }
  }

  public void displayInput() {
    InputMethodManager imgr =
        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    imgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
  }
}
