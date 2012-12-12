package com.mpl.stopwatch;

import java.util.List;
import java.util.Vector;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public class MainActivity extends SherlockFragmentActivity {

	private static final String	TAG = "MainActivity";
	private PagerAdapter		_pagerAdapter;
	private TitlePageIndicator	_titleIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, StopwatchFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, CountdownFragment.class.getName()));

		_pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(_pagerAdapter);
		_titleIndicator = (TitlePageIndicator) findViewById(R.id.titles);
		_titleIndicator.setTextColor(0xAA000000);
		_titleIndicator.setSelectedColor(0xFF000000);
		_titleIndicator.setViewPager(pager);
	}
}
