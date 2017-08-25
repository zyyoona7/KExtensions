package com.zyyoona7.lib

import android.app.Fragment
import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * Created by zyyoona7 on 2017/8/24.
 * Toast 扩展函数
 */

/*
  ---------- Context ----------
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) = {
    if (duration == 0 || duration == 1) {
        Toast.makeText(applicationContext, msg, duration).show()
    } else {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}

/*
  ---------- Fragment ----------
 */
fun Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    activity.toast(msg, duration)
}

fun android.support.v4.app.Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    activity.toast(msg, duration)
}

/*
  ---------- View ----------
 */
fun View.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(msg, duration)
}