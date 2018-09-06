//
// Created by 임운택 on 2018. 9. 7..
//

#include <io_untaek_animal_NativeAdapter.h>
#include "libavdevice/version.h"

JNIEXPORT jint JNICALL Java_io_untaek_animal_NativeAdapter_hello (JNIEnv *env, jobject obj) {
    return LIBAVUTIL_VERSION_MAJOR;
}

JNIEXPORT jstring JNICALL Java_io_untaek_animal_NativeAdapter_helloWorld
  (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "hahaha");
  }