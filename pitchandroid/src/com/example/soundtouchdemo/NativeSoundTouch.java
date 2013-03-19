package com.example.soundtouchdemo;

public final class NativeSoundTouch {

	private static NativeSoundTouch instance = null;

	private long nativeSoundTouch;

	public synchronized static NativeSoundTouch getSoundTouch() {
		if (null == instance) {
			instance = new NativeSoundTouch();
		}

		return instance;
	}

	private NativeSoundTouch() {
		nativeSoundTouch = soundTouchCreate();
	}

	public native long soundTouchCreate();

	public native void soundTouchDestory();

	public native String soundTouchgethVersion();
	
	public native void setPitchSemiTones(float pitch);
	
	public native void setTempoChange(float newTempo);
	
	public native void shiftingPitch(byte[] pcmData, int offset, int length);
	
	public native int receiveSamples(byte[] pitchData, int bufferLenght);
	
	public native void soundTouchFlushLastSamples();

	@Override
	protected void finalize() throws Throwable {

		soundTouchDestory();

		super.finalize();
	}
}
