LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libStatisticNative
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := statisticCalculate.c

include $(BUILD_SHARED_LIBRARY)
