package com.mpl.stopwatch;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StopwatchFragment extends Fragment {
	private boolean				_running = false;
	private Button				_btnStart;
	private Button				_btnReset;
	private Chronometer			_chronometer;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.stopwatch, container, false);
		
		_btnStart = (Button) v.findViewById(R.id.btn_start_stop);
		_btnReset = (Button) v.findViewById(R.id.btn_reset);
		_chronometer = (Chronometer) v.findViewById(R.id.chronometer);

		_btnStart.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	if (_running) {
	        		_chronometer.stop();
	        		_running = false;
	        		_btnStart.setText(R.string.btn_start);
	        	} else {
		        	_chronometer.setBase(SystemClock.elapsedRealtime());
	        		_chronometer.start();
	        		_running = true;
	        		_btnStart.setText(R.string.btn_stop);
	        	}
	         }
	     });

		_btnReset.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	if (_running)
	        		_chronometer.stop();
	        	_chronometer.setBase(SystemClock.elapsedRealtime());

	        	if (_running)
	        		_chronometer.start();
	         }
	     });
		
		return v;
	}
}
