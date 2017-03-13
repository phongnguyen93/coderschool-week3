package vn.com.phongnguyen93.noisybirdy.lite.fragments;

import android.support.v4.app.Fragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import vn.com.phongnguyen93.noisybirdy.lite.TriggerRefreshEvent;

/**
 * Created by phongnguyen on 3/13/17.
 */

public abstract class BaseFragment extends Fragment {

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void handleTriggerEvent(TriggerRefreshEvent event) {
    if (event.isTriggered()) onReload();
  }

  protected abstract void onReload();

  protected abstract void onError();

  protected abstract void onLoading();

  protected abstract void onSuccess();

  @Override public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }
}
