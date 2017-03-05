/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package vn.com.phongnguyen93.noisybirdy.presentation.view;

import java.util.ArrayList;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link TweetModel}.
 */
public interface TweetListView extends LoadDataView {
  /**
   * Render a user list in the UI.
   *
   * @param tweetModels The collection of {@link TweetModel} that will be shown.
   */
  void renderTweetList(ArrayList<TweetModel> tweetModels);

  /**
   * View a {@link TweetModel} profile/details.
   *
   * @param tweetModel The user that will be shown.
   */
  void viewTweet(TweetModel tweetModel);
}
