/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vn.com.phongnguyen93.noisybirdy.presentation.presenter;

import android.support.annotation.NonNull;

import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Named;
import vn.com.phongnguyen93.noisybirdy.domain.Tweet;
import vn.com.phongnguyen93.noisybirdy.domain.exception.DefaultErrorBundle;
import vn.com.phongnguyen93.noisybirdy.domain.exception.ErrorBundle;
import vn.com.phongnguyen93.noisybirdy.domain.interactor.DefaultSubscriber;
import vn.com.phongnguyen93.noisybirdy.domain.interactor.UseCase;
import vn.com.phongnguyen93.noisybirdy.presentation.NoisyBirdyApplication;
import vn.com.phongnguyen93.noisybirdy.presentation.di.PerActivity;
import vn.com.phongnguyen93.noisybirdy.presentation.exception.ErrorMessageFactory;
import vn.com.phongnguyen93.noisybirdy.presentation.mapper.TweetModelDataMapper;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;
import vn.com.phongnguyen93.noisybirdy.presentation.view.TweetListView;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class TweetListPresenter implements Presenter {

  private TweetListView viewListView;

  private final UseCase getTweetListUseCase;
  private final TweetModelDataMapper tweetModelDataMapper;

  @Inject
  public TweetListPresenter(@Named("tweetList") UseCase getTweetListUseCase,
      TweetModelDataMapper modelDataMapper) {
    this.getTweetListUseCase = getTweetListUseCase;
    this.tweetModelDataMapper = modelDataMapper;
  }

  public void setView(@NonNull TweetListView view) {
    this.viewListView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.getTweetListUseCase.unsubscribe();
    this.viewListView = null;
  }

  /**
   * Initializes the presenter by start retrieving the user list.
   */
  public void initialize() {
    this.loadTweetList();
  }

  /**
   * Loads all users.
   */
  private void loadTweetList() {
    this.hideViewRetry();
    this.showViewLoading();
    this.getTweetList();
  }

  public void postTweet(final NoisyBirdyApplication application,String text){
    showViewLoading();
    application.getClient(application.getApplicationContext()).postTweet(
        text, new AsyncHttpResponseHandler() {
          @Override public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            if(statusCode==200){
              hideViewLoading();
              Toast.makeText(application.getApplicationContext(),"Tweet is posted",Toast.LENGTH_SHORT).show();
              loadTweetList();
            }
          }

          @Override public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
              Throwable error) {
            hideViewLoading();
          }
        });
  }

  public void onTweetClicked(TweetModel tweetModel) {
    this.viewListView.viewTweet(tweetModel);
  }

  private void showViewLoading() {
    this.viewListView.showLoading();
  }

  private void hideViewLoading() {
    this.viewListView.hideLoading();
  }

  private void showViewRetry() {
    this.viewListView.showRetry();
  }

  private void hideViewRetry() {
    this.viewListView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
        errorBundle.getException());
    this.viewListView.showError(errorMessage);
  }

  private void showTweetsCollectionInView(ArrayList<Tweet> tweets) {
    final ArrayList<TweetModel> tweetModels =
        this.tweetModelDataMapper.transformCollection(tweets);
    this.viewListView.renderTweetList(tweetModels);
  }

  private void getTweetList() {
    this.getTweetListUseCase.execute(new TweetListSubscriber());
  }


  private final class TweetListSubscriber extends DefaultSubscriber<ArrayList<Tweet>> {

    @Override public void onCompleted() {
      TweetListPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      TweetListPresenter.this.hideViewLoading();
      TweetListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      TweetListPresenter.this.showViewRetry();
    }

    @Override public void onSubscribe(Disposable d) {

    }

    @Override public void onNext(ArrayList<Tweet> tweets) {
      TweetListPresenter.this.hideViewLoading();
      TweetListPresenter.this.showTweetsCollectionInView(tweets);
    }
  }
}
