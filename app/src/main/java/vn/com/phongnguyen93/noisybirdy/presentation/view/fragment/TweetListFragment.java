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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.presentation.NoisyBirdyApplication;
import vn.com.phongnguyen93.noisybirdy.presentation.di.components.UserComponent;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;
import vn.com.phongnguyen93.noisybirdy.presentation.presenter.TweetListPresenter;
import vn.com.phongnguyen93.noisybirdy.presentation.view.TweetListView;
import vn.com.phongnguyen93.noisybirdy.presentation.view.adapter.TweetAdapter;

/**
 * Fragment that shows a list of Users.
 */
public class TweetListFragment extends BaseFragment implements TweetListView {

  /**
   * Interface for listening user list events.
   */
  public interface UserListListener {
    void onUserClicked(final TweetModel userModel);
  }

  @Inject TweetListPresenter tweetListPresenter;
  @Inject TweetAdapter tweetAdapter;

  @BindView(R.id.rv_tweet) RecyclerView rvTweetList;
  @BindView(R.id.rl_progress) RelativeLayout rl_progress;
  @BindView(R.id.rl_retry) RelativeLayout rl_retry;
  @BindView(R.id.bt_retry) Button bt_retry;
  @BindView(R.id.btn_start) Button btnStart;
  @BindView(R.id.btn_post) FloatingActionButton fabPost;

  private ArrayList<TweetModel> data;
  private UserListListener userListListener;

  @Inject
  public TweetListFragment() {
    setRetainInstance(true);
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (activity instanceof UserListListener) {
      this.userListListener = (UserListListener) activity;
    }
  }


  @OnClick(R.id.btn_post)
  public void postTweet(){
    tweetListPresenter.postTweet(((NoisyBirdyApplication)getActivity().getApplication()),"asdasda");
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(UserComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View fragmentView = inflater.inflate(R.layout.fragment_tweet_list, container, false);
    ButterKnife.bind(this, fragmentView);
    setupRecyclerView();
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.tweetListPresenter.setView(this);
  }

  @Override public void onResume() {
    super.onResume();
    this.tweetListPresenter.resume();
    this.loadTweetList();
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
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
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
    if(rvTweetList!=null){
      rvTweetList.setLayoutManager(new LinearLayoutManager(context()));
      rvTweetList.setAdapter(tweetAdapter);
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


}
