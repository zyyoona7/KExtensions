package com.zyyoona7.lib

import com.zyyoona7.lib.log.ZLog

/**
 * Created by zyyoona7 on 2017/8/24.
 * Log 打印 扩展函数
 * Log output extensions
 */

fun logv(msg: String, customTag: String = "") = ZLog.v(msg, customTag)

fun logd(msg: String, customTag: String = "") = ZLog.d(msg, customTag)

fun logi(msg: String, customTag: String = "") = ZLog.i(msg, customTag)

fun logw(msg: String, customTag: String = "") = ZLog.w(msg, customTag)

fun loge(msg: String, customTag: String = "") = ZLog.e(msg, customTag)

fun logJson(msg: String, customTag: String = "") = ZLog.json(msg, customTag)

fun logGlobalTag(global: String) {
    ZLog.logGlobalTag = global
}

fun logEnabled(logEnabled: Boolean) {
    ZLog.logEnabled = logEnabled
}
