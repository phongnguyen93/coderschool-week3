package vn.com.phongnguyen93.noisybirdy.lite.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.lite.ProfileActivity;
import vn.com.phongnguyen93.noisybirdy.lite.TweetAdapter;
import vn.com.phongnguyen93.noisybirdy.lite.TwitterClient;

/**
 * Created by phongnguyen on 3/13/17.
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
    TweetAdapter.TweetInteractionCallback {

  private static final String TAG = HomeFragment.class.getSimpleName();
  @BindView(R.id.rv_home) RecyclerView rvHomeTimeline;
  @BindView(R.id.error_layout) RelativeLayout errorLayout;
  @BindView(R.id.loading_layout) RelativeLayout loaddingLayout;
  @BindView(R.id.swipeLayout) SwipeRefreshLayout swipeRefreshLayout;

  private TweetAdapter adapter;

  public HomeFragment() {
  }

  public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Change the null parameter in {@code inflater.inflate()}
   * to a layout resource {@code R.layout.example}
   */
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, rootView);
    swipeRefreshLayout.setOnRefreshListener(this);
    adapter = new TweetAdapter(getContext(),this);
    rvHomeTimeline.setLayoutManager(new LinearLayoutManager(getContext()));
    rvHomeTimeline.setAdapter(adapter);
    rvHomeTimeline.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0
            : recyclerView.getChildAt(0).getTop();
        swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
      }
    });
    return rootView;
  }

  private void getHomeTimeline() {
    onLoading();
    TwitterClient.getInstance()
        .getStatusesService()
        .homeTimeline(100, null, null, false, true, false, false)
        .enqueue(new Callback<List<Tweet>>() {
          @Override public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
            if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
              onSuccess();
              adapter.setData(response.body());
            } else {
              onError();
            }
          }

          @Override public void onFailure(Call<List<Tweet>> call, Throwable t) {
            onError();
          }
        });
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
    getHomeTimeline();
  }

  @Override protected void onReload() {
    getHomeTimeline();
  }

  @Override protected void onError() {
    swipeRefreshLayout.setRefreshing(false);
    errorLayout.setVisibility(View.VISIBLE);
    loaddingLayout.setVisibility(View.GONE);
    rvHomeTimeline.setVisibility(View.GONE);
  }

  @Override protected void onLoading() {
    swipeRefreshLayout.setRefreshing(true);
    errorLayout.setVisibility(View.GONE);
    loaddingLayout.setVisibility(View.VISIBLE);
    rvHomeTimeline.setVisibility(View.GONE);
  }

  @Override protected void onSuccess() {
    swipeRefreshLayout.setRefreshing(false);
    errorLayout.setVisibility(View.GONE);
    loaddingLayout.setVisibility(View.GONE);
    rvHomeTimeline.setVisibility(View.VISIBLE);
  }

  @Override public void onRefresh() {
    getHomeTimeline();
  }

  @Override public void onTweetClick(Tweet tweet) {
    Intent t =  new Intent(getContext(), ProfileActivity.class);
    t.putExtra("id",tweet.user.id);
    t.putExtra("screenName",tweet.user.screenName);
    startActivity(t);
  }
}