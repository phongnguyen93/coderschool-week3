package vn.com.phongnguyen93.noisybirdy.lite;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by phongnguyen on 3/10/17.
 */

public class TabFragmentAdapter extends FragmentStatePagerAdapter {
  private ArrayList<Fragment> fragments;
  private ArrayList<String> title;

  public TabFragmentAdapter(FragmentManager fm, LinkedHashMap<String,Fragment> fragmentLinkedHashMap) {
    super(fm);
    this.fragments = new ArrayList<>(fragmentLinkedHashMap.values());
    this.title = new ArrayList<>(fragmentLinkedHashMap.keySet());
  }

  @Override public CharSequence getPageTitle(int position) {
    return title.get(position);
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }


  @Override public int getItemPosition(Object object) {
    int position = fragments.indexOf(object);
    if (position >= 0) {
      return position;
    } else {
      return POSITION_NONE;
    }
  }

  @Override public int getCount() {
    return fragments.size();
  }
}
