package vn.com.phongnguyen93.noisybirdy.domain.interactor;

import io.reactivex.Observable;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.domain.Tweet;
import vn.com.phongnguyen93.noisybirdy.domain.executor.PostExecutionThread;
import vn.com.phongnguyen93.noisybirdy.domain.executor.ThreadExecutor;
import vn.com.phongnguyen93.noisybirdy.domain.repository.Repository;

/**
 * This class is an implementation of {@link UseCase} that represents for a use case that
 * we retrieve a list of {@link Tweet}
 *
 * Created by phongnguyen on 3/4/17.
 */

public class GetTimeline extends UseCase {
  private final Repository repository;
  private final int limit;

  @Inject
  public GetTimeline(Repository repository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread,int limit) {
    super(threadExecutor, postExecutionThread);
    this.repository = repository;
    this.limit = limit;
  }


  @Override protected Observable buildUseCaseObservable() {
    return repository.getTimeline(limit);
  }
}
