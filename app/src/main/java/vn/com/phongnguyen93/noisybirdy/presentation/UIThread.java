package vn.com.phongnguyen93.noisybirdy.presentation;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.domain.executor.PostExecutionThread;

/**
 * Created by phongnguyen on 3/5/17.
 */

public class UIThread implements PostExecutionThread {

  @Inject
  public UIThread(){}

  @Override public Scheduler getScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
