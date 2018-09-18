LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := media
LOCAL_SRC_FILES := main.cpp
LOCAL_LDLIBS := -llog
LOCAL_SHARED_LIBRARIES := libavformat libavcodec libavdevice libswscale libavutil libswresample libavfilter
include $(BUILD_SHARED_LIBRARY)

$(call import-module,android/ffmpeg-android)
