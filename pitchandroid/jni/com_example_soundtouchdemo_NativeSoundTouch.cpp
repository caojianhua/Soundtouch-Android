#include "com_example_soundtouchdemo_NativeSoundTouch.h"
#include "SoundTouch.h"
#include "log.h"

static soundtouch::SoundTouch* getTouch(JNIEnv *env, jobject obj) {

	jclass cls = env->GetObjectClass(obj);
	jfieldID fid = env->GetFieldID(cls, "nativeSoundTouch", "J");
	soundtouch::SoundTouch* soundTouch =
			(soundtouch::SoundTouch*) (env->GetLongField(obj, fid));

	return soundTouch;
}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    soundTouchCreate
 * Signature: ()J
 */
jlong Java_com_example_soundtouchdemo_NativeSoundTouch_soundTouchCreate(
		JNIEnv *, jobject) {

	soundtouch::SoundTouch* soundTouch = new soundtouch::SoundTouch();

	const char* verStr = soundTouch->getVersionString();
	LOGD("Create SoundTouch object[version: %s]", verStr);

	soundTouch->setSampleRate(8000);
	soundTouch->setChannels(1);
	//soundTouch->setPitchSemiTones(0.00f);

	soundTouch->setSetting(SETTING_USE_QUICKSEEK, 0);

	soundTouch->setSetting(SETTING_SEQUENCE_MS, 32);
	soundTouch->setSetting(SETTING_SEEKWINDOW_MS, 15);
	soundTouch->setSetting(SETTING_OVERLAP_MS, 8);
	soundTouch->setSetting(SETTING_USE_AA_FILTER, 1);

	return (jlong) soundTouch;
}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    soundTouchDestory
 * Signature: ()V
 */
void Java_com_example_soundtouchdemo_NativeSoundTouch_soundTouchDestory(
		JNIEnv *env, jobject obj) {

	soundtouch::SoundTouch* soundTouch = getTouch(env, obj);

	delete soundTouch;
}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    soundTouchgethVersion
 * Signature: ()Ljava/lang/String;
 */
jstring Java_com_example_soundtouchdemo_NativeSoundTouch_soundTouchgethVersion(
		JNIEnv *env, jobject obj) {

	const char *verStr;

	soundtouch::SoundTouch* soundTouch = getTouch(env, obj);
	verStr = soundTouch->getVersionString();

	// return version as string
	return env->NewStringUTF(verStr);
}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    setPitchSemiTones
 * Signature: (D)V
 */
void Java_com_example_soundtouchdemo_NativeSoundTouch_setPitchSemiTones(
		JNIEnv *env, jobject obj, jfloat jpitch) {

	soundtouch::SoundTouch* soundTouch = getTouch(env, obj);

	float pitch = jpitch;
	soundTouch->setPitchSemiTones(pitch);
}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    setTempoChange
 * Signature: (F)V
 */
void Java_com_example_soundtouchdemo_NativeSoundTouch_setTempoChange(
		JNIEnv *env, jobject obj, jfloat jtempo) {

	soundtouch::SoundTouch* soundTouch = getTouch(env, obj);

	float tempo = jtempo;
	soundTouch->setTempoChange(tempo);

}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    shiftingPitch
 * Signature: ([BII)V
 */
void Java_com_example_soundtouchdemo_NativeSoundTouch_shiftingPitch(JNIEnv *env,
		jobject obj, jbyteArray jarray, jint offset, jint length) {

	soundtouch::SoundTouch* soundTouch = getTouch(env, obj);

	jbyte *data;

	data = env->GetByteArrayElements(jarray, JNI_FALSE);

	//soundtouch::SAMPLETYPE sampleBuffer[length];
	//memcpy(&sampleBuffer, data, length);

	//(16*1)/8=2bytes,length/2=x;x¸ösample

	soundTouch->putSamples((soundtouch::SAMPLETYPE*) data, length / (16 / 8));

	env->ReleaseByteArrayElements(jarray, data, 0);

}

/*
 * Class:     com_example_soundtouchdemo_NativeSoundTouch
 * Method:    receiveSamples
 * Signature: ([BI)I
 */
jint Java_com_example_soundtouchdemo_NativeSoundTouch_receiveSamples(
		JNIEnv *env, jobject obj, jbyteArray jarray, jint maxsamples) {

	int receiveSamples = 0;

	soundtouch::SoundTouch* soundTouch = getTouch(env, obj);

	jbyte *data;

	data = env->GetByteArrayElements(jarray, JNI_FALSE);

	receiveSamples = soundTouch->receiveSamples((soundtouch::SAMPLETYPE*) data,
			maxsamples);

	//memcpy(data, sampleBuffer, receiveSize);

	env->ReleaseByteArrayElements(jarray, data, 0);

	return receiveSamples;
}

