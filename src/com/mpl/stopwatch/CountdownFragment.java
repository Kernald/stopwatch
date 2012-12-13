package com.mpl.stopwatch;

import net.simonvt.widget.NumberPicker;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CountdownFragment extends Fragment {
	private boolean			_running = false;
	private Button			_btnStart;
	private NumberPicker	_npHours;
	private NumberPicker	_npMinutes;
	private NumberPicker	_npSeconds;
	private CountDownTimer	_cdt = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.countdown, container, false);

		_btnStart = (Button) v.findViewById(R.id.btn_start_stop);

		_btnStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (_running)
					stop();
				else
					start((((long)_npHours.getValue() * 60 + _npMinutes.getValue()) * 60 + _npSeconds.getValue()) * 1000);
			}
		});

		_npHours = (NumberPicker) v.findViewById(R.id.np_hours);
		_npHours.setMaxValue(24);
		_npHours.setMinValue(0);
		_npHours.setFocusable(true);
		_npHours.setFocusableInTouchMode(true);

		_npMinutes = (NumberPicker) v.findViewById(R.id.np_minutes);
		_npMinutes.setMaxValue(59);
		_npMinutes.setMinValue(0);
		_npMinutes.setFocusable(true);
		_npMinutes.setFocusableInTouchMode(true);
		_npMinutes.setValue(1);

		_npSeconds = (NumberPicker) v.findViewById(R.id.np_seconds);
		_npSeconds.setMaxValue(59);
		_npSeconds.setMinValue(0);
		_npSeconds.setFocusable(true);
		_npSeconds.setFocusableInTouchMode(true);

		return v;
	}
	
	private void start(long time) {
		_cdt = new CountDownTimer(time, 1000) {
			public void onTick(long millisUntilFinished) {
				millisUntilFinished += millisUntilFinished % 1000;
				_npSeconds.setValue((int)(millisUntilFinished / 1000) % 60);
				_npMinutes.setValue((int)(millisUntilFinished / 1000 / 60) % 60);
				_npHours.setValue((int)(millisUntilFinished / 1000 / 60 / 60));
			}

			public void onFinish() {
				_npSeconds.setValue(0);
				stop();
			}
		}.start();
		_running = true;
		_npHours.setEnabled(false);
		_npMinutes.setEnabled(false);
		_npSeconds.setEnabled(false);
		_btnStart.setText(R.string.btn_stop);
	}
	
	private void stop() {
		_cdt.cancel();
		_running = false;
		_npHours.setEnabled(true);
		_npMinutes.setEnabled(true);
		_npSeconds.setEnabled(true);
		_btnStart.setText(R.string.btn_start);
	}
}
