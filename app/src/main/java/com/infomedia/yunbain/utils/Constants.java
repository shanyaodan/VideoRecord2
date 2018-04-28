package com.infomedia.yunbain.utils;

/**
 * Created by pc on 2018/4/27.
 */

public class Constants {
    /**
     * 编解码方式
     */
    public static final int ENCODE_METHOD_SOFTWARE_COMPAT = 1;
    public static final int ENCODE_METHOD_HARDWARE = 2;
    public static final int ENCODE_METHOD_SOFTWARE = 3;
    public static final int DECODE_METHOD_SOFTWARE = 1;
    public static final int DECODE_METHOD_HARDWARE = 2;

    /**
     * 分辨率
     */
    public static final int VIDEO_RESOLUTION_360P = 0;
    public static final int VIDEO_RESOLUTION_480P = 1;
    public static final int VIDEO_RESOLUTION_540P = 2;
    public static final int VIDEO_RESOLUTION_720P = 3;
    public static final int VIDEO_RESOLUTION_1080P = 4;

    /**
     * 默认值
     */
    public static final int DEFAULT_PREVIEW_RESOLUTION = VIDEO_RESOLUTION_720P;
    public static final float DEFAULT_PREVIEW_FPS = 15.0F;
    public static final int DEFAULT_TARGET_RESOLUTION = 0;
    public static final float DEFAULT_TARGET_FPS = 15.0F;
    public static final float DEFAULT_IFRAME_INTERVAL = 3.0F;
    public static final int DEFAULT_MAX_VIDEO_BITRATE = 800000;
    public static final int DEFAULT_INIT_VIDEO_BITRATE = 600000;
    public static final int DEFAILT_MIN_VIDEO_BITRATE = 200000;
    public static final int DEFAULT_AUDIO_BITRATE = 48000;
    public static final int DEFAULT_AUDIO_SAMPLE_RATE = 44100;
    public static final int DEFAULT_AUDIO_CHANNELS = 1;





}
