package com.example.soundtouchdemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

public class SoundTouchRecorder {

	private static final String TAG = "SoundTouchRecorder";

	private AudioRecord mAudioRecorder = null;

	private AudioTrack mAudioTrack;

	private int minRecBuffSize = 0;

	private int minPlayBufferSize = 0;

	private byte[] recorderBuffer;

	private byte[] playerBuffer;

	private boolean recordingstart = false;

	private boolean playingstart = false;

	private Context context;

	private RecordThread recordThread = null;

	private PlayThread playThread = null;

	class RecordThread extends Thread {

		private FileOutputStream recordOutputStream = null;

		private String fullFileName;

		RecordThread() {

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy_MM_dd_HH_mm_ss_SSS");
			Date curDate = new Date(System.currentTimeMillis());
			String fileName = formatter.format(curDate);

			Log.d(TAG, "recordfile name:" + fileName);

			fullFileName = /* recordFolderPath + */fileName + ".pcm";
			try {
				recordOutputStream = context.openFileOutput(fullFileName,
						Context.MODE_PRIVATE);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		public void run() {

			int totalPCMSamples = 0;
			int totalSTSamples = 0;

			int receiveSTSamples = 0;

			while (recordingstart && recordOutputStream != null) {
				int len = mAudioRecorder.read(recorderBuffer, 0,
						recorderBuffer.length);

				int inputSamples = len / ((16 * 1) / 8);
				totalPCMSamples += inputSamples;

				try {

					Log.d(TAG, "input ST pcm size :" + inputSamples);

					NativeSoundTouch.getSoundTouch().shiftingPitch(
							recorderBuffer, 0, len);

					do {
						receiveSTSamples = NativeSoundTouch.getSoundTouch()
								.receiveSamples(recorderBuffer,
										recorderBuffer.length);

						totalSTSamples += receiveSTSamples;
						Log.d(TAG, "receive ST pcm samples :"
								+ receiveSTSamples);

						if (receiveSTSamples != 0) {
							recordOutputStream.write(recorderBuffer, 0,
									receiveSTSamples * ((16 * 1) / 8));
							recordOutputStream.flush();
						}

						// sava into file
					} while (receiveSTSamples != 0);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// receive the remainder of the speech data
			NativeSoundTouch.getSoundTouch().soundTouchFlushLastSamples();

			do {
				receiveSTSamples = NativeSoundTouch.getSoundTouch()
						.receiveSamples(recorderBuffer, recorderBuffer.length);

				Log.d(TAG, "receive remainder ST samples:" + receiveSTSamples);

				totalSTSamples += receiveSTSamples;

				if (receiveSTSamples != 0) {

					try {
						recordOutputStream.write(recorderBuffer, 0,
								receiveSTSamples * ((16 * 1) / 8));
						recordOutputStream.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			} while (receiveSTSamples != 0);

			Log.d(TAG, "Total input pcm samples:" + totalPCMSamples);
			Log.d(TAG, "total receive ST samoles:" + totalSTSamples);

			mAudioRecorder.stop();
			mAudioRecorder.release();
			recordingstart = false;

			try {
				recordOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		};
	};

	class PlayThread extends Thread {

		private FileInputStream playInputStream = null;

		PlayThread(String fileName) {

			if (fileName != null) {
				try {
					playInputStream = context.openFileInput(fileName);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void run() {

			while (playingstart && playInputStream != null) {
				int len = 0;
				try {
					len = playInputStream.read(playerBuffer, 0,
							playerBuffer.length);

				} catch (IOException e) {
					e.printStackTrace();
				}

				if (len == -1) {
					Log.d(TAG, "file read finish!");

					break;
				}

				if (len != 0) {
					mAudioTrack.write(playerBuffer, 0, len);
					Log.d(TAG, "write [" + len + "]pcm data into player!");
				}

			}

			mAudioTrack.stop();
			mAudioTrack.release();
			playingstart = false;

			if (playInputStream != null) {
				try {
					playInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	SoundTouchRecorder(Context context) {
		this.context = context;
	}

	public void startRecord() {

		if (playThread != null && playThread.isAlive()) {
			Log.w(TAG, "AudioPlayer is running, please stop Player!");
			return;
		}

		if (recordThread != null && recordThread.isAlive()) {
			Log.w(TAG, "AudioRecorder is already start!");
			return;
		}

		minRecBuffSize = AudioRecord.getMinBufferSize(8000,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

		mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
				AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
				minRecBuffSize * 3);

		mAudioRecorder.startRecording();

		if (minRecBuffSize != 0) {
			recorderBuffer = new byte[minRecBuffSize * 3];

			recordingstart = true;
			recordThread = new RecordThread();
			recordThread.start();
		}

	}

	public String stopRecorder() {
		recordingstart = false;

		String fileName = null;

		if (recordThread != null) {
			fileName = recordThread.fullFileName;
			recordThread = null;
		}

		return fileName;
	}

	public void startPlay(String fileName) {

		if (recordThread != null && recordThread.isAlive()) {
			Log.w(TAG, "Record is not complite!");
			return;
		}

		if (playThread != null && playThread.isAlive()) {
			Log.w(TAG, "AudioPlayer is already started!");
			return;
		}

		minPlayBufferSize = AudioTrack.getMinBufferSize(8000,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				minPlayBufferSize * 3, AudioTrack.MODE_STREAM);

		if (minPlayBufferSize != 0) {

			playerBuffer = new byte[minPlayBufferSize * 3];
			mAudioTrack.play();
			playingstart = true;
			playThread = new PlayThread(fileName);
			playThread.start();
		}

	}

	public void stopPlay() {
		playingstart = false;
		playThread = null;
	}

}
