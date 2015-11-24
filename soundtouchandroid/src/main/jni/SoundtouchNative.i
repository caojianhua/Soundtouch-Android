%module SoundtouchNative

%include "std_string.i"
%include "typemaps.i"
%include "carrays.i"
%include "arrays_java.i"

%header %{
#include "FrameProcessor.h"
#include "WavProcessor.h"
%}

%include "FrameProcessor.h"
%include "WavProcessor.h"

%pragma(java) jniclasscode=%{
  static {
    try {
        System.loadLibrary("soundtouchnative");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load. \n" + e);
      System.exit(1);
    }
  }
%}