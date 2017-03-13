package vn.com.phongnguyen93.noisybirdy.lite.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import java.util.LinkedHashMap;
import vn.com.phongnguyen93.noisybirdy.R;
import vn.com.phongnguyen93.noisybirdy.lite.NonSwipableViewPager;
import vn.com.phongnguyen93.noisybirdy.lite.TabFragmentAdapter;

/**
 * Created by phongnguyen on 3/13/17.
 */
public class TimelineFragment extends Fragment {

  private static final String TAG = TimelineFragment.class.getSimpleName();
  @BindView(R.id.tab_layout) SmartTabLayout tabLayout;
  @BindView(R.id.view_pager) NonSwipableViewPager viewPager;
  public TimelineFragment() {
  }

  public static TimelineFragment newInstance() {
    TimelineFragment fragment = new TimelineFragment();
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
    View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);
    ButterKnife.bind(this,rootView);
    TabFragmentAdapter fragmentAdapter =
        new TabFragmentAdapter(getChildFragmentManager(), getFragmentList());
    viewPager.setAdapter(fragmentAdapter);
    tabLayout.setViewPager(viewPager);
    return rootView;
  }

  private LinkedHashMap<String, Fragment> getFragmentList() {
    LinkedHashMap<String, Fragment> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("Home", HomeFragment.newInstance());
    linkedHashMap.put("Mention", MentionFragment.newInstance());
    return linkedHashMap;
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override public void onResume() {
    super.onResume();
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
}