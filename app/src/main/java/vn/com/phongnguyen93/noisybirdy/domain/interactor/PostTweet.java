package vn.com.phongnguyen93.noisybirdy.domain.interactor;

import io.reactivex.Observable;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.domain.executor.PostExecutionThread;
import vn.com.phongnguyen93.noisybirdy.domain.executor.ThreadExecutor;
import vn.com.phongnguyen93.noisybirdy.domain.repository.Repository;

/**
 * This is an implementation of {@link UseCase} that represents a use case that
 * user post a new tweet
 *
 * Created by phongnguyen on 3/5/17.
 */

public class PostTweet extends UseCase {

  private final Repository repository;
  private final String text;

  @Inject
  public PostTweet(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, Repository repository, String text) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.text = text;
  }

  @Override protected Observable buildUseCaseObservable() {
    return repository.postTweet(text);
  }
}
