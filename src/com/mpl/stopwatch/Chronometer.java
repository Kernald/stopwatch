package com.mpl.stopwatch;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;
import java.text.DecimalFormat;

public class Chronometer extends TextView {
	@SuppressWarnings("unused")
	private static final String TAG = "Chronometer";

	public interface OnChronometerTickListener {
		void onChronometerTick(Chronometer chronometer);
	}

	private long						_base;
	private boolean						_visible;
	private boolean						_started;
	private boolean						_running;
	private OnChronometerTickListener	_onChronometerTickListener;

	private static final int TICK_WHAT = 2;

	public Chronometer(Context context) {
		this (context, null, 0);
	}

	public Chronometer(Context context, AttributeSet attrs) {
		this (context, attrs, 0);
	}

	public Chronometer(Context context, AttributeSet attrs, int defStyle) {
		super (context, attrs, defStyle);

		init();
	}

	private void init() {
		_base = SystemClock.elapsedRealtime();
		updateText(_base);
	}

	public void setBase(long base) {
		_base = base;
		dispatchChronometerTick();
		updateText(SystemClock.elapsedRealtime());
	}

	public long getBase() {
		return _base;
	}

	public void setOnChronometerTickListener(
			OnChronometerTickListener listener) {
		_onChronometerTickListener = listener;
	}

	public OnChronometerTickListener getOnChronometerTickListener() {
		return _onChronometerTickListener;
	}

	public void start() {
		_started = true;
		updateRunning();
	}

	public void stop() {
		_started = false;
		updateRunning();
	}


	public void setStarted(boolean started) {
		_started = started;
		updateRunning();
	}

	@Override
	protected void onDetachedFromWindow() {
		super .onDetachedFromWindow();
		_visible = false;
		updateRunning();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super .onWindowVisibilityChanged(visibility);
		_visible = visibility == VISIBLE;
		updateRunning();
	}

	private synchronized void updateText(long now) {
		long timeElapsed = now - _base;

		DecimalFormat df = new DecimalFormat("00");

		int hours = (int)(timeElapsed / (3600 * 1000));
		int remaining = (int)(timeElapsed % (3600 * 1000));

		int minutes = (int)(remaining / (60 * 1000));
		remaining = (int)(remaining % (60 * 1000));

		int seconds = (int)(remaining / 1000);
		remaining = (int)(remaining % (1000));

		int milliseconds = (int)(((int)timeElapsed % 1000) / 10);

		String text = "";

		if (hours > 0) {
			text += df.format(hours) + ":";
		}

		text += df.format(minutes) + ":";
		text += df.format(seconds) + ":";
		text += df.format(milliseconds);

		setText(text);
	}

	private void updateRunning() {
		boolean running = _visible && _started;
		if (running != _running) {
			if (running) {
				updateText(SystemClock.elapsedRealtime());
				dispatchChronometerTick();
				_handler.sendMessageDelayed(Message.obtain(_handler, TICK_WHAT), 10);
			} else {
				_handler.removeMessages(TICK_WHAT);
			}
			_running = running;
		}
	}

	private Handler _handler = new Handler() {
		public void handleMessage(Message m) {
			if (_running) {
				updateText(SystemClock.elapsedRealtime());
				dispatchChronometerTick();
				sendMessageDelayed(Message.obtain(this , TICK_WHAT), 10);
			}
		}
	};

	void dispatchChronometerTick() {
		if (_onChronometerTickListener != null) {
			_onChronometerTickListener.onChronometerTick(this);
		}
	}
}