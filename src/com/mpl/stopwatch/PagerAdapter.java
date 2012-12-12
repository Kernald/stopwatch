package com.mpl.stopwatch;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

	private final List<Fragment> _fragments;

	public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		_fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return _fragments.get(position);
	}

	@Override
	public int getCount() {
		return _fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0)
			return "Stopwatch";
		else if (position == 1)
			return "Countdown";
		else
			return "";
	}
}