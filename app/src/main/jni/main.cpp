//
// Created by 임운택 on 2018. 9. 7..
//

#include <io_untaek_animal_NativeAdapter.h>

extern "C" {
#include <libavdevice/version.h>
#include <libavcodec/avcodec.h>
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <libavutil/mathematics.h>

}

#include <stdio.h>
#include <android/log.h>

#define LOG_TAG "FFmpegForAndroid"
#define LOGI(...) __android_log_print(4, LOG_TAG, __VA_ARGS__);
#define LOGE(...) __android_log_print(6, LOG_TAG, __VA_ARGS__);


JNIEXPORT jint JNICALL Java_io_untaek_animal_NativeAdapter_hello (JNIEnv *env, jobject obj) {
    return LIBAVDEVICE_VERSION_MAJOR;
}

JNIEXPORT jstring JNICALL Java_io_untaek_animal_NativeAdapter_helloWorld
  (JNIEnv *env, jobject obj) {
    return env->NewStringUTF("hahaha");
  }

JNIEXPORT jint JNICALL Java_io_untaek_animal_NativeAdapter_getThumbnails
  (JNIEnv *env, jclass obj, jstring filepath) {

    const char* nativeFilepath = env->GetStringUTFChars(filepath , NULL ) ;

        AVFormatContext   *avFormatCtx = NULL;
        AVCodecContext    *avCodecCtx = NULL;
        AVCodec           *avCodec = NULL;
        int               videoStream;
        AVFrame           *avFrame;
        AVFrame           *avFrameRGB;
        AVPacket          avPacket;
        int               numBytes;
        uint8_t           *buffer = NULL;
        int               pts;
        AVRational        frames;
        int               frame;
        int64_t           timeBase;

        AVDictionary      *optionsDict = NULL;

        // nativeFilepath로 avFormatContext 가져오기

        if(avformat_open_input(&avFormatCtx, nativeFilepath, NULL, NULL) < 0)
        {
            LOGE("Can't open input file '%s'", nativeFilepath);
            env->ReleaseStringUTFChars(filepath, nativeFilepath);
            return -1;
        }

        pts = (int)(avFormatCtx->duration / 1000);
        LOGI("duration : %ld ms\n", avFormatCtx->duration / 1000);

        // 유효한 스트림 정보 찾기

        if(avformat_find_stream_info(avFormatCtx, NULL) < 0)
        {
            LOGE("Failed to retrieve input stream information");
            env->ReleaseStringUTFChars(filepath, nativeFilepath);
            return -2;
        }

        frames = avFormatCtx->streams[videoStream]->avg_frame_rate;
        frame = frames.num / frames.den * pts/1000;
        LOGI("frame : %d\n", frames.num / frames.den);
        LOGI("frames : %d\n", frame);

        // avFormatContext->nb_streams : 비디오 파일의 전체 스트림 수

        for(unsigned int index = 0; index < avFormatCtx->nb_streams; index++)
        {
            AVCodecParameters* avCodecParameters = avFormatCtx->streams[index]->codecpar;
            if(avCodecParameters->codec_type == AVMEDIA_TYPE_VIDEO)
            {
                videoStream = index;
                LOGI("------- Video info -------");
                LOGI("codec_id : %d", avCodecParameters->codec_id);
                LOGI("bitrate : %ld", avCodecParameters->bit_rate);
                LOGI("width : %d / height : %d", avCodecParameters->width, avCodecParameters->height);
                LOGI("timebase: %d", avFormatCtx->streams[videoStream]->time_base.den);
            }
            else if(avCodecParameters->codec_type == AVMEDIA_TYPE_AUDIO)
            {
                LOGI("------- Audio info -------");
                LOGI("codec_id : %d", avCodecParameters->codec_id);
                LOGI("bitrate : %lld", avCodecParameters->bit_rate);
                LOGI("sample_rate : %d", avCodecParameters->sample_rate);
                LOGI("number of channels : %d", avCodecParameters->channels);
            }
        }

        avCodec = avcodec_find_decoder(avFormatCtx->streams[videoStream]->codecpar->codec_id);
        avCodecCtx = avcodec_alloc_context3(avCodec);

        timeBase = (int64_t(avCodecCtx->time_base.num) * AV_TIME_BASE) / int64_t(avCodecCtx->time_base.den);

        if(avCodec == NULL) {
            LOGE("Unsupported codec!\n");
            return -1; // Codec not found
        }

        LOGI("\nvideo codec_name : %s\n", avCodec->name);

        if(avcodec_open2(avCodecCtx, avCodec, &optionsDict) < 0) {
            LOGE("Could not open codec!\n");
            return -1; // Could not open codec
        }

        avFrame = av_frame_alloc();
        avFrameRGB = av_frame_alloc();

        LOGI("\ndecoder open\n");

        int ret;
        AVRational tb = avFormatCtx->streams[videoStream]->time_base;

        for(unsigned int i = 0; i < 1000; i++) {
            int64_t t = ( 1 * i) * AV_TIME_BASE;
            t = av_rescale_q(t, AV_TIME_BASE_Q, avFormatCtx->streams[videoStream]->time_base);
            LOGI("frame time : %lld\n", t);

            ret = av_seek_frame(avFormatCtx, -1, i*100, AVSEEK_FLAG_BACKWARD);
            if(ret < 0){
                LOGI("\nav_seek_frame fail\n");
                continue;
            }

            while(av_read_frame(avFormatCtx, &avPacket) >= 0){
                if(avPacket.stream_index != videoStream) {
                    av_packet_unref(&avPacket);
                    continue;
                }
                break;
            }

            LOGI("\navstream index: %d\n", avPacket.stream_index);

            ret = avcodec_send_packet(avCodecCtx, &avPacket);
            if(ret == AVERROR(EAGAIN)) {
                LOGI("\navcodec_send_packet fail 1\n");
                continue;
            }
            else if (ret == AVERROR(EINVAL)) {
                LOGI("\navcodec_send_packet fail 2\n");
                continue;
            }
            else if (ret == AVERROR(ENOMEM)) {
                LOGI("\navcodec_send_packet fail 3\n");
                continue;
            }
            else if (ret == AVERROR_EOF) {
                LOGI("\navcodec_send_packet fail 3\n");
                continue;
            }
            while(true){
                ret = avcodec_receive_frame(avCodecCtx, avFrame);

                if(ret == AVERROR(EAGAIN)) {
                    LOGI("\navcodec_receive_frame fail 1\n");
                    break;
                }
                else if (ret == AVERROR_EOF) {
                    LOGI("\navcodec_receive_frame fail 2\n");
                    break;
                }
                else if (ret < 0) {
                    LOGI("\navcodec_receive_frame fail 3\n");
                    break;
                }

            }
            LOGI("pts1 : %lld\n", avPacket.pts);
            LOGI("pts2 : %lld\n", avFrame->pts);

            av_packet_unref(&avPacket);
        }


        // release
        av_free(buffer);
        av_free(avFrameRGB);
        av_free(avFrame);
        avcodec_free_context(&avCodecCtx);
        avformat_close_input(&avFormatCtx);
        env->ReleaseStringUTFChars(filepath, nativeFilepath);

        return 0;
}