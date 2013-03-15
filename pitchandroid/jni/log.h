#include<android/log.h>

#define LOGV(...)   __android_log_print((int)ANDROID_LOG_VERBOSE, "SOUNDTOUCH", __VA_ARGS__)
#define LOGD(...)   __android_log_print((int)ANDROID_LOG_DEBUG, "SOUNDTOUCH", __VA_ARGS__)
#define LOGI(...)   __android_log_print((int)ANDROID_LOG_INFO, "SOUNDTOUCH", __VA_ARGS__)
#define LOGW(...)   __android_log_print((int)ANDROID_LOG_WARN, "SOUNDTOUCH", __VA_ARGS__)
#define LOGE(...)   __android_log_print((int)ANDROID_LOG_ERROR, "SOUNDTOUCH", __VA_ARGS__)





