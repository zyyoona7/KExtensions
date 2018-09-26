package com.zyyoona7.extensions

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
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * screen height in pixels
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels

/**
 * returns dip(dp) dimension value in pixels
 * @param value dp
 */
fun Context.dip2px(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dip2px(value: Float): Int = (value * resources.displayMetrics.density).toInt()

/**
 * return sp dimension value in pixels
 * @param value sp
 */
fun Context.sp2px(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

fun Context.sp2px(value: Float): Int = (value * resources.displayMetrics.scaledDensity).toInt()

/**
 * converts [px] value into dip or sp
 * @param px
 */
fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density

fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

/**
 * return dimen resource value in pixels
 * @param resource dimen resource
 */
fun Context.dimen2px(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

/*
  ---------- Fragment ----------
 */

val Fragment.screenWidth
    get() = activity.screenWidth

val Fragment.screenHeight
    get() = activity.screenHeight

fun Fragment.dip2px(value: Int): Int = activity?.dip2px(value) ?: 0
fun Fragment.dip2px(value: Float): Int = activity?.dip2px(value) ?: 0

fun Fragment.sp2px(value: Int): Int = activity?.sp2px(value) ?: 0
fun Fragment.sp2px(value: Float): Int = activity?.sp2px(value) ?: 0

fun Fragment.px2dip(px: Int): Float = activity?.px2dip(px) ?: 0f
fun Fragment.px2sp(px: Int): Float = activity?.px2sp(px) ?: 0f

fun Fragment.dimen2px(@DimenRes resource: Int): Int = activity?.dimen2px(resource) ?: 0

val android.support.v4.app.Fragment.screenWidth
    get() = activity?.screenWidth

val android.support.v4.app.Fragment.screenHeight
    get() = activity?.screenHeight

fun android.support.v4.app.Fragment.dip2px(value: Int): Int = activity?.dip2px(value) ?: 0
fun android.support.v4.app.Fragment.dip2px(value: Float): Int = activity?.dip2px(value) ?: 0

fun android.support.v4.app.Fragment.sp2px(value: Int): Int = activity?.sp2px(value) ?: 0
fun android.support.v4.app.Fragment.sp2px(value: Float): Int = activity?.sp2px(value) ?: 0

fun android.support.v4.app.Fragment.px2dip(px: Int): Float = activity?.px2dip(px) ?: 0f
fun android.support.v4.app.Fragment.px2sp(px: Int): Float = activity?.px2sp(px) ?: 0f

fun android.support.v4.app.Fragment.dimen2px(@DimenRes resource: Int): Int = activity?.dimen2px(resource)
        ?: 0

/*
  ---------- View ----------
 */
val View.screenWidth
    get() = context.screenWidth

val View.screenHeight
    get() = context.screenHeight

fun View.dip2px(value: Int):Int = context.dip2px(value)
fun View.dip2px(value: Float):Int = context.dip2px(value)

fun View.sp2px(value: Int):Int = context.sp2px(value)
fun View.sp2px(value: Float):Int = context.sp2px(value)

fun View.px2dip(px: Int):Float = context.px2dip(px)
fun View.px2sp(px: Int):Float = context.px2sp(px)

fun View.dimen2px(@DimenRes resource: Int):Int = context.dimen2px(resource)