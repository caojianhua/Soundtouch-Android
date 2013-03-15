package com.example.soundtouchdemo;

import com.example.soundtouchdemo.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	static {
		System.loadLibrary("_pitchandroid_soundtouch_jni_gyp");
	}

	private static final String TAG = "soundtouchdemo";

	private SoundTouchRecorder soundTouchRec;

	private Button recorderStartBut;
	private Button recorderStopBut;
	private Button playerStartBut;
	private Button playerStopBut;

	private TextView pitchShow;
	private SeekBar pitchSeekBar;

	private TextView tempoShow;
	private SeekBar tempoSeekBar;

	private String lastRecordFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recorderStartBut = (Button) findViewById(R.id.record_start);
		recorderStartBut.setOnClickListener(recordStartListener);

		recorderStopBut = (Button) findViewById(R.id.record_stop);
		recorderStopBut.setOnClickListener(recordStopListener);

		playerStartBut = (Button) findViewById(R.id.play_start);
		playerStartBut.setOnClickListener(playStartListener);

		playerStopBut = (Button) findViewById(R.id.play_stop);
		playerStopBut.setOnClickListener(playStopListener);

		pitchSeekBar = (SeekBar) findViewById(R.id.pitch_seek);
		pitchSeekBar.setOnSeekBarChangeListener(onPitchSeekBarListener);

		pitchShow = (TextView) findViewById(R.id.pitch_show);

		tempoSeekBar = (SeekBar) findViewById(R.id.tempo_seek);
		tempoSeekBar.setOnSeekBarChangeListener(onTempoSeekBarListener);

		tempoShow = (TextView) findViewById(R.id.tempo_show);

		soundTouchRec = new SoundTouchRecorder(this);

		// String versionCode = NativeSoundTouch.getSoundTouch()
		// .soundTouchgethVersion();
		// Log.d(TAG, "get soundtouch version :" + versionCode);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		soundTouchRec.stopRecorder();
		soundTouchRec.stopPlay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private OnClickListener recordStartListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			soundTouchRec.startRecord();
		}
	};

	private OnClickListener recordStopListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			lastRecordFile = soundTouchRec.stopRecorder();
		}
	};

	private OnClickListener playStartListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			soundTouchRec.startPlay(lastRecordFile);
		}
	};

	private OnClickListener playStopListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			soundTouchRec.stopPlay();
		}
	};

	private OnSeekBarChangeListener onPitchSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

			float pitch = (seekBar.getProgress() - 1000) / 100.0f;
			NativeSoundTouch.getSoundTouch().setPitchSemiTones(pitch);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			float pitch = (progress - 1000) / 100.0f;
			pitchShow.setText("Pitch Shift: " + pitch);
		}
	};

	private OnSeekBarChangeListener onTempoSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

			float tempo = (seekBar.getProgress() - 5000) / 100.0f;
			NativeSoundTouch.getSoundTouch().setTempoChange(tempo);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			float tempo = (progress - 5000) / 100.0f;
			tempoShow.setText("Tempo Change: " + tempo + "%");
		}
	};

}
