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

LOCAL_C_INCLUDES :=  $(LOCAL_PATH)/  \
	                 $(LOCAL_PATH)/soundtouch/include/  \
	                 $(LOCAL_PATH)/soundtouch/source/SoundStretch/

LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ \
	                       $(LOCAL_PATH)/soundtouch/include/  \
	                       $(LOCAL_PATH)/soundtouch/source/SoundStretch/

LOCAL_SRC_FILES := FrameProcessor.cpp \
                   WavProcessor.cpp \
                   SoundtouchNative_wrap.cxx \
                   soundtouch/source/SoundTouch/AAFilter.cpp \
                   soundtouch/source/SoundTouch/BPMDetect.cpp \
                   soundtouch/source/SoundTouch/FIFOSampleBuffer.cpp \
                   soundtouch/source/SoundTouch/FIRFilter.cpp \
                   soundtouch/source/SoundTouch/PeakFinder.cpp \
                   soundtouch/source/SoundTouch/RateTransposer.cpp \
                   soundtouch/source/SoundTouch/SoundTouch.cpp \
                   soundtouch/source/SoundTouch/TDStretch.cpp \
                   soundtouch/source/SoundTouch/cpu_detect_x86.cpp \
                   soundtouch/source/SoundTouch/mmx_optimized.cpp \
                   soundtouch/source/SoundTouch/sse_optimized.cpp \
                   soundtouch/source/SoundTouch/InterpolateLinear.cpp \
                   soundtouch/source/SoundTouch/InterpolateShannon.cpp \
                   soundtouch/source/SoundTouch/InterpolateCubic.cpp \
                   soundtouch/source/SoundStretch/RunParameters.cpp \
                   soundtouch/source/SoundStretch/WavFile.cpp \

include $(BUILD_SHARED_LIBRARY)