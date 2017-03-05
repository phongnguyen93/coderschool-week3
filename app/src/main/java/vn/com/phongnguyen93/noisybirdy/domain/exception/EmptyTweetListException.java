package vn.com.phongnguyen93.noisybirdy.domain.exception;

/**
 *
 * Created by phongnguyen on 3/5/17.
 */

public class EmptyTweetListException extends Exception {
  public EmptyTweetListException() {
    super();
  }

  public EmptyTweetListException(String message) {
    super(message);
  }

  public EmptyTweetListException(String message, Throwable cause) {
    super(message, cause);
  }

  public EmptyTweetListException(Throwable cause) {
    super(cause);
  }

}
