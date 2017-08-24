package com.zyyoona7.lib

import android.app.Fragment
import android.content.Context
import android.support.annotation.DimenRes
import android.view.View

/**
 * Created by zyyoona7 on 2017/8/24.
 *
 * 尺寸、尺寸转换 扩展函数
 * dimension extensions
 */
/*
  ---------- Context ----------
 */

/**
 * screen width in pixels
 */
inline val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * screen height in pixels
 */
inline val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

/**
 * returns dip(dp) dimension value in pixels
 * @param value dp
 */
inline fun Context.dip2px(value: Int): Int = (value * resources.displayMetrics.density).toInt()

inline fun Context.dip2px(value: Float): Int = (value * resources.displayMetrics.density).toInt()

/**
 * return sp dimension value in pixels
 * @param value sp
 */
inline fun Context.sp2px(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

inline fun Context.sp2px(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

/**
 * converts [px] value into dip or sp
 * @param px
 */
inline fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density

inline fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

/**
 * return dimen resource value in pixels
 * @param resource dimen resource
 */
inline fun Context.dimen2px(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

/*
  ---------- Fragment ----------
 */

inline val Fragment.screenWidth
    get() = activity.screenWidth

inline val Fragment.screenHeight
    get() = activity.screenHeight

inline fun Fragment.dip2px(value: Int) = activity.dip2px(value)
inline fun Fragment.dip2px(value: Float) = activity.dip2px(value)

inline fun Fragment.sp2px(value: Int) = activity.sp2px(value)
inline fun Fragment.sp2px(value: Float) = activity.sp2px(value)

inline fun Fragment.px2dip(px: Int) = activity.px2dip(px)
inline fun Fragment.px2sp(px: Int) = activity.px2sp(px)

inline fun Fragment.dimen2px(@DimenRes resource: Int) = activity.dimen2px(resource)

inline val android.support.v4.app.Fragment.screenWidth
    get() = activity.screenWidth

inline val android.support.v4.app.Fragment.screenHeight
    get() = activity.screenHeight

inline fun android.support.v4.app.Fragment.dip2px(value: Int) = activity.dip2px(value)
inline fun android.support.v4.app.Fragment.dip2px(value: Float) = activity.dip2px(value)

inline fun android.support.v4.app.Fragment.sp2px(value: Int) = activity.sp2px(value)
inline fun android.support.v4.app.Fragment.sp2px(value: Float) = activity.sp2px(value)

inline fun android.support.v4.app.Fragment.px2dip(px: Int) = activity.px2dip(px)
inline fun android.support.v4.app.Fragment.px2sp(px: Int) = activity.px2sp(px)

inline fun android.support.v4.app.Fragment.dimen2px(@DimenRes resource: Int) = activity.dimen2px(resource)

/*
  ---------- View ----------
 */
inline val View.screenWidth
    get() = context.screenWidth

inline val View.screenHeight
    get() = context.screenHeight

inline fun View.dip2px(value: Int) = context.dip2px(value)
inline fun View.dip2px(value: Float) = context.dip2px(value)

inline fun View.sp2px(value: Int) = context.sp2px(value)
inline fun View.sp2px(value: Float) = context.sp2px(value)

inline fun View.px2dip(px: Int) = context.px2dip(px)
inline fun View.px2sp(px: Int) = context.px2sp(px)

inline fun View.dimen2px(@DimenRes resource: Int) = context.dimen2px(resource)