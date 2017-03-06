package vn.com.phongnguyen93.noisybirdy.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.presentation.model.TweetModel;

/**
 * Created by phongnguyen on 3/6/17.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

  private static final String TAG = TweetAdapter.class.getSimpleName();

  private ArrayList<TweetModel> objects;

  public void setData(ArrayList<TweetModel> tweetModels){
    if(tweetModels!=null && tweetModels.size()>0){
      objects = tweetModels;
      notifyDataSetChanged();
    }
  }

  /**
   * Change {@link List} type according to your needs
   */
  @Inject
  public TweetAdapter() {
  }

  /**
   * Change the null parameter to a layout resource {@code R.layout.example}
   */
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item_layout, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bindItem(objects.get(position));
  }

  @Override public int getItemCount() {
    return objects != null ? objects.size(): 0;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_tweet_username) TextView tvUserName;
    @BindView(R.id.tv_tweet_text) TextView tvText;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this,itemView);
    }

    void bindItem(TweetModel item){
      tvText.setText(item.getText());
      tvUserName.setText(item.getTweetUserName());
    }
  }
}