
APP_ABI := armeabi x86

ROOT := $(call my-dir)

APP_BUILD_SCRIPT := $(ROOT)/Android.mk
APP_STL := gnustl_static
APP_CPPFLAGS := -frtti -fexceptions