package com.infomedia.yunbain.utils;

public class VideoConfig {
    public boolean isLandscape = false;
    public float fps = Constants.DEFAULT_TARGET_FPS;
    public int resolution = Constants.VIDEO_RESOLUTION_480P;
    public int videoBitrate = Constants.DEFAULT_INIT_VIDEO_BITRATE;
    public int audioBitrate = Constants.DEFAULT_AUDIO_BITRATE;
    public int encodeType = AVConst.CODEC_ID_AVC;
    public int encodeMethod = Constants.ENCODE_METHOD_SOFTWARE;
    public int encodeProfile = VideoEncodeFormat.ENCODE_PROFILE_LOW_POWER;
    public int decodeMethod =Constants.DECODE_METHOD_HARDWARE;
    public int audioEncodeProfile = AVConst.PROFILE_AAC_LOW;
    public int videoCRF = 24;
    public int audioChannel = 1;
    public int audioSampleRate = 44100;
    public int width = 480;
    public int height = 480;
}
