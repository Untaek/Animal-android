LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := media
LOCAL_SRC_FILES := main.c
LOCAL_LDLIBS := -llog
LOCAL_SHARED_LIBRARIES := libavformat libavcodec libavdevice libswscale libavutil libswresample libavfilter
include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_MODULE := avcodec
LOCAL_SRC_FILES := ffmpeg/lib/libavcodec.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avdevice
LOCAL_SRC_FILES := ffmpeg/lib/libavdevice.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avfilter
LOCAL_SRC_FILES := ffmpeg/lib/libavfilter.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avformat
LOCAL_SRC_FILES := ffmpeg/lib/libavformat.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avutil
LOCAL_SRC_FILES := ffmpeg/lib/libavutil.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swresample
LOCAL_SRC_FILES := ffmpeg/lib/libswresample.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swscale
LOCAL_SRC_FILES := ffmpeg/lib/libswscale.so
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/ffmpeg/include
include $(PREBUILT_SHARED_LIBRARY)



