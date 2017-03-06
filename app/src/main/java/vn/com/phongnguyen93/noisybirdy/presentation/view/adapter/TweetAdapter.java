package vn.com.phongnguyen93.noisybirdy.presentation.view.adapter;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;

/**
 * Created by phongnguyen on 3/6/17.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

  private static final String TAG = TweetAdapter.class.getSimpleName();

  private ArrayList<TweetModel> objects;
  private Context context;

  public void setData(ArrayList<TweetModel> tweetModels) {
    if (tweetModels != null && tweetModels.size() > 0) {
      objects = tweetModels;
      notifyDataSetChanged();
    }
  }

  /**
   * Change {@link List} type according to your needs
   */
  public TweetAdapter(Context context) {
    this.context = context;
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
    holder.bindItem(objects.get(position),context);
  }

  @Override public int getItemCount() {
    return objects != null ? objects.size() : 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_tweet_username) TextView tvUserName;
    @BindView(R.id.tv_tweet_text) TextView tvText;
    @BindView(R.id.tv_tweet_time) TextView tvTime;
    TextView tvFavCount;
    TextView tvRetweetCount;
    @BindView(R.id.imgUser) ImageView imgAvatar;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      tvRetweetCount = (TextView)itemView.findViewById(R.id.tv_retweetcount);
      tvFavCount = (TextView)itemView.findViewById(R.id.tv_favcount);
    }

    void bindItem(TweetModel item, final Context context) {
      tvText.setText(item.getText());
      tvTime.setText(getRelativeTimeAgo(item.getDate()));
      tvUserName.setText(item.getTweetUserName());
      tvFavCount.setText(String.valueOf(item.getFavCount()));
      tvRetweetCount.setText(String.valueOf(item.getRetweetCount()));
      Glide.with(context).load(item.getTweetUserImage()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imgAvatar) {
        @Override
        protected void setResource(Bitmap resource) {
          RoundedBitmapDrawable circularBitmapDrawable =
              RoundedBitmapDrawableFactory.create(context.getResources(), resource);
          circularBitmapDrawable.setCircular(true);
          imgAvatar.setImageDrawable(circularBitmapDrawable);
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
      relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
          System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return relativeDate;
  }
}