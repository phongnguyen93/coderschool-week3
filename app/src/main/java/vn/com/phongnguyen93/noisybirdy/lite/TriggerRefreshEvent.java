package vn.com.phongnguyen93.noisybirdy.lite;

/**
 * Created by phongnguyen on 3/13/17.
 */

public class TriggerRefreshEvent {
  public TriggerRefreshEvent(boolean isTriggered) {
    this.isTriggered = isTriggered;
  }

  public boolean isTriggered() {
    return isTriggered;
  }

  public void setTriggered(boolean triggered) {
    isTriggered = triggered;
  }

  boolean isTriggered;
}
