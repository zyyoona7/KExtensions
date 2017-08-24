package com.zyyoona7.lib

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by zyyoona7 on 2017/8/24.
 * Snackbar 扩展函数
 */
/*
  ---------- Context ----------
 */
fun Context.snackbar(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(view, msg, duration)
}

fun Context.showSnackbar(view: View,
                         msg: String,
                         duration: Int = Snackbar.LENGTH_SHORT,
                         actionMsg: String = "",
                         action: (view: View) -> Unit = {}) {
    snackbar(view, msg, duration)
            .setAction(actionMsg, action)
            .show()
}