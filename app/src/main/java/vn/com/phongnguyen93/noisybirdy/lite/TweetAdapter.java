package vn.com.phongnguyen93.noisybirdy.lite;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.twitter.sdk.android.core.models.Tweet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import vn.com.phongnguyen93.noisybirdy.R;

/**
 * Created by phongnguyen on 3/6/17.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

  private static final String TAG = TweetAdapter.class.getSimpleName();
  private TweetInteractionCallback callback;
  private List<Tweet> objects;
  private Context context;

  public interface TweetInteractionCallback {
    void onTweetClick(Tweet tweet);
  }

  public void setData(List<Tweet> tweetModels) {
    if (tweetModels != null && tweetModels.size() > 0) {
      objects = tweetModels;
      notifyDataSetChanged();
    }
  }

  /**
   * Change {@link List} type according to your needs
   */
  public TweetAdapter(Context context, TweetInteractionCallback callback) {
    this.context = context;
    this.callback = callback;
  }

  /**
   * Change the null parameter to a layout resource {@code R.layout.example}
   */
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_layout, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bindItem(objects.get(position), context);
  }

  @Override public int getItemCount() {
    return objects != null ? objects.size() : 0;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_tweet_username) TextView tvUserName;
    @BindView(R.id.tv_tweet_text) TextView tvText;
    @BindView(R.id.tv_tweet_time) TextView tvTime;
    @BindView(R.id.img_content) ImageView imgContent;
    TextView tvFavCount;
    TextView tvRetweetCount;
    private View view;
    @BindView(R.id.imgUser) ImageView imgAvatar;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      view = itemView;
      tvRetweetCount = (TextView) itemView.findViewById(R.id.tv_retweetcount);
      tvFavCount = (TextView) itemView.findViewById(R.id.tv_favcount);
    }

    void bindItem(final Tweet item, final Context context) {
      tvText.setText(item.text);
      tvTime.setText(getRelativeTimeAgo(item.createdAt));
      tvUserName.setText(item.user.name);
      tvFavCount.setText(String.valueOf(item.favoriteCount));
      tvRetweetCount.setText(String.valueOf(item.retweetCount));
      if (item.entities != null && item.entities.media != null && item.entities.media.size() > 0) {
        Glide.with(context).load(item.entities.media.get(0).mediaUrl).into(imgContent);
      }
      Glide.with(context)
          .load(item.user.profileImageUrl)
          .asBitmap()
          .centerCrop()
          .into(new BitmapImageViewTarget(imgAvatar) {
            @Override protected void setResource(Bitmap resource) {
              RoundedBitmapDrawable circularBitmapDrawable =
                  RoundedBitmapDrawableFactory.create(context.getResources(), resource);
              circularBitmapDrawable.setCircular(true);
              imgAvatar.setImageDrawable(circularBitmapDrawable);
            }
          });
      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          callback.onTweetClick(item);
        }
      });
    }
  }

  private static String getRelativeTimeAgo(String rawJsonDate) {
    String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
    sf.setLenient(true);

    String relativeDate = "";
    try {
      long dateMillis = sf.parse(rawJsonDate).getTime();
      relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(),
          DateUtils.SECOND_IN_MILLIS).toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return relativeDate;
  }
}