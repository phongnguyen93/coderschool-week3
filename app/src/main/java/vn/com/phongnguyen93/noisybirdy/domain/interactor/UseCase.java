package vn.com.phongnguyen93.noisybirdy.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import vn.com.phongnguyen93.noisybirdy.domain.executor.PostExecutionThread;
import vn.com.phongnguyen93.noisybirdy.domain.executor.ThreadExecutor;


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.
 *
 * Created by phongnguyen on 3/4/17.
 */

public abstract class UseCase  {
  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;

  private Observable dataStream;

  protected UseCase(ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
  }

  /**
   * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
   */
  protected abstract Observable buildUseCaseObservable();

  /**
   * Executes the current use case.
   *
   * @param useCaseSubscriber The guy who will be listen to the observable build
   * with {@link #buildUseCaseObservable()}.
   */
  @SuppressWarnings("unchecked")
  public void execute(Observer useCaseSubscriber){
    dataStream = buildUseCaseObservable();
    dataStream.subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler())
        .subscribe(useCaseSubscriber);
  }

  /**
   * Unsubscribes from current {@link Subscription}.
   */
  public void unsubscribe() {
      dataStream.unsubscribeOn(Schedulers.from(threadExecutor));

  }
}
