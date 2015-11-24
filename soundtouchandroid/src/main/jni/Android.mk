LOCAL_PATH := $(call my-dir)

PACKAGE_NAME := com.example.soundtouchandroid.core

JAVA_SOURCE_DIR := $(LOCAL_PATH)/../java/$(shell echo $(PACKAGE_NAME) | sed 's/\./\//g')

$(warning($JAVA_SOURCE_DIR))
$(shell mkdir -p $(JAVA_SOURCE_DIR))
$(shell rm -rf `find $(JAVA_SOURCE_DIR) -name *.java`)

$(shell \
	/usr/local/bin/swig  -c++ -java -package $(PACKAGE_NAME) \
	-outdir $(JAVA_SOURCE_DIR) \
	$(LOCAL_PATH)/SoundtouchNative.i \
)

include $(CLEAR_VARS)

LOCAL_MODULE := soundtouchnative

LOCAL_C_INCLUDES := \
	$(LOCAL_PATH)/.

LOCAL_EXPORT_C_INCLUDES := \
	$(LOCAL_PATH)/.

LOCAL_SRC_FILES := FrameProcessor.cpp \
                   WavProcessor.cpp \
                   SoundtouchNative_wrap.cxx

LOCAL_SHARED_LIBRARIES := libsoundtouch

include $(BUILD_SHARED_LIBRARY)