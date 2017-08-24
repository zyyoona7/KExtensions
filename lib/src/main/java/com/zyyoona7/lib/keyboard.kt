package com.zyyoona7.lib

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by zyyoona7 on 2017/8/24.
 * 软键盘操作 扩展函数
 * Keyboard operation extensions
 */

/*
  ---------- Context ----------
 */
inline val Context.inputMethodManager: InputMethodManager?
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

inline fun Context.showSoftInput(view: View) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    inputMethodManager?.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

inline fun Context.hideSoftInput(view: View) {
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

inline fun Context.hideSoftInput(activity: Activity) {
    var view: View = activity.currentFocus
    view = if (view != null) view else activity.window.decorView
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

/*
  ---------- Fragment ----------
 */

inline fun Fragment.showSoftInput(view: View) {
    activity.showSoftInput(view)
}

inline fun Fragment.hideSoftInput(view: View) {
    activity.hideSoftInput(view)
}

inline fun Fragment.hideSoftInput() {
    activity.hideSoftInput(activity)
}

inline fun android.support.v4.app.Fragment.showSoftInput(view: View) {
    activity.showSoftInput(view)
}

inline fun android.support.v4.app.Fragment.hideSoftInput(view: View) {
    activity.hideSoftInput(view)
}

inline fun android.support.v4.app.Fragment.hideSoftInput() {
    activity.hideSoftInput(activity)
}

/*
  ---------- View ----------
 */
inline fun View.showSoftInput(view: View) {
    context.showSoftInput(view)
}

inline fun View.hideSoftInput(view: View) {
    context.hideSoftInput(view)
}