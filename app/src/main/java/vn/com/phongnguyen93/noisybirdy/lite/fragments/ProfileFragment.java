package vn.com.phongnguyen93.noisybirdy.lite.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.twitter.sdk.android.core.models.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.lite.TweetAdapter;
import vn.com.phongnguyen93.noisybirdy.lite.TwitterClient;

/**
 * Created by phongnguyen on 3/13/17.
 */
public class ProfileFragment extends Fragment implements TweetAdapter.TweetInteractionCallback {

  private static final String TAG = ProfileFragment.class.getSimpleName();

  @BindView(R.id.img_background) ImageView imgBackground;
  @BindView(R.id.img_profile) ImageView imgProfile;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.rv_content) RecyclerView rvContent;
  @BindView(R.id.tv_follower_count) TextView tvFollower;
  @BindView(R.id.tv_like_count) TextView tvLike;
  @BindView(R.id.tv_tweet_count) TextView tvTweetCount;
  @BindView(R.id.tvName) TextView tvName;
  @BindView(R.id.tvScreenName) TextView tvScreenName;

  private String screenName;
  private long id;
  private TweetAdapter adapter;

  public ProfileFragment() {
  }

  public static ProfileFragment newInstance(String userScreenName, long id) {
    ProfileFragment fragment = new ProfileFragment();
    if (userScreenName != null) {
      Bundle args = new Bundle();
      args.putString("screenName", userScreenName);
      args.putLong("id", id);
      fragment.setArguments(args);
    }
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      id = getArguments().getLong("id");
      screenName = getArguments().getString("screenName");
    }
  }

  /**
   * Change the null parameter in {@code inflater.inflate()}
   * to a layout resource {@code R.layout.example}
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
    ButterKnife.bind(this, rootView);
    adapter = new TweetAdapter(getContext(), this);
    if (getActivity().getActionBar() != null) {
      getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
    rvContent.setAdapter(adapter);
    return rootView;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    getUserProfile();
    getUserTimeline();
  }

  private void getUserTimeline() {
    TwitterClient.getInstance()
        .getStatusesService()
        .userTimeline(id, screenName, 50, null, null, false, false, false, false)
        .enqueue(new Callback<List<Tweet>>() {
          @Override public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
            if (response.isSuccessful()) {
              adapter.setData(response.body());
            }
          }

          @Override public void onFailure(Call<List<Tweet>> call, Throwable t) {

          }
        });
  }

  private void getUserProfile() {
    TwitterClient.getInstance()
        .getTwitterEndpoint()
        .getUser(id, screenName)
        .enqueue(new Callback<User>() {
          @Override public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
             populateUserData(response.body());
            }
          }

          @Override public void onFailure(Call<User> call, Throwable t) {

          }
        });
  }

  private void populateUserData(User item) {
    Glide.with(getContext())
        .load(item.profileImageUrl)
        .asBitmap()
        .centerCrop()
        .into(new BitmapImageViewTarget(imgProfile) {
          @Override protected void setResource(Bitmap resource) {
            RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(getContext().getResources(),
                    resource);
            circularBitmapDrawable.setCircular(true);
            imgProfile.setImageDrawable(circularBitmapDrawable);
          }
        });
    Glide.with(getContext()).load(item.profileBannerUrl).into(imgBackground);
    tvFollower.setText(Html.fromHtml(String.format(getString(R.string.base_follower_count),item.followersCount)));
    tvLike.setText(Html.fromHtml(String.format(getString(R.string.base_following_count),item.favouritesCount)));
    tvTweetCount.setText(Html.fromHtml(String.format(getString(R.string.base_tweet_count),item.statusesCount)));
    tvName.setText(item.name);
    tvScreenName.setText("@"+item.screenName);
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    super.onDestroy();
  }

  @Override public void onTweetClick(Tweet tweet) {

  }
}