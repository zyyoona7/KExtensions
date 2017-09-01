package com.zyyoona7.lib

import android.app.Fragment
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * Created by zyyoona7 on 2017/8/26.
 * 资源相关的扩展函数
 *
 */

/*
  ---------- Context ----------
 */
fun Context.loadColor(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

fun Context.loadDrawable(@DrawableRes id: Int): Drawable = ContextCompat.getDrawable(this, id)
//todo assert resources and other


/*
  ---------- Fragment ----------
 */
fun Fragment.loadColor(@ColorRes id: Int): Int = activity.loadColor(id)

fun Fragment.loadDrawable(@DrawableRes id: Int): Drawable = activity.loadDrawable(id)

fun android.support.v4.app.Fragment.loadColor(@ColorRes id: Int): Int = activity.loadColor(id)

fun android.support.v4.app.Fragment.loadDrawable(@DrawableRes id: Int): Drawable = activity.loadDrawable(id)


/*
  ---------- View ----------
 */
fun View.loadColor(@ColorRes id: Int): Int = context.loadColor(id)

fun View.loadDrawable(@ColorRes id: Int): Drawable = context.loadDrawable(id)
