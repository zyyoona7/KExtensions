package com.zyyoona7.extensions

import android.app.Fragment
import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.RawRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import java.io.File
import java.io.IOException
import java.io.InputStream

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

fun Context.loadRaw(@RawRes id: Int): InputStream? = resources.openRawResource(id)

fun Context.loadRaw(@RawRes id: Int, value: TypedValue): InputStream? = resources.openRawResource(id, value)

fun Context.loadAsset(fileName: String, accessMode: Int = AssetManager.ACCESS_STREAMING): InputStream? {
    return try {
        assets.open(fileName, accessMode)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun Context.loadTypefaceFromAsset(fileName: String): Typeface = Typeface.createFromAsset(assets, fileName)

fun loadTypefaceFromFile(filePath: String): Typeface = Typeface.createFromFile(filePath)

fun loadTypefaceFromFile(file: File): Typeface = Typeface.createFromFile(file)


/*
  ---------- Fragment ----------
 */
fun Fragment.loadColor(@ColorRes id: Int): Int = activity.loadColor(id)

fun Fragment.loadDrawable(@DrawableRes id: Int): Drawable = activity.loadDrawable(id)

fun Fragment.loadRaw(@RawRes id: Int): InputStream? = activity.loadRaw(id)

fun Fragment.loadRaw(@RawRes id: Int, value: TypedValue): InputStream? = activity.loadRaw(id, value)

fun Fragment.loadAsset(fileName: String, accessMode: Int = AssetManager.ACCESS_STREAMING): InputStream? = activity.loadAsset(fileName, accessMode)

fun Fragment.loadTypefaceFromAsset(fileName: String): Typeface = activity.loadTypefaceFromAsset(fileName)

fun android.support.v4.app.Fragment.loadColor(@ColorRes id: Int): Int = activity.loadColor(id)

fun android.support.v4.app.Fragment.loadDrawable(@DrawableRes id: Int): Drawable = activity.loadDrawable(id)

fun android.support.v4.app.Fragment.loadRaw(@RawRes id: Int): InputStream? = activity.loadRaw(id)

fun android.support.v4.app.Fragment.loadRaw(@RawRes id: Int, value: TypedValue): InputStream? = activity.loadRaw(id, value)

fun android.support.v4.app.Fragment.loadAsset(fileName: String, accessMode: Int = AssetManager.ACCESS_STREAMING): InputStream? = activity.loadAsset(fileName, accessMode)

fun android.support.v4.app.Fragment.loadTypefaceFromAsset(fileName: String): Typeface = activity.loadTypefaceFromAsset(fileName)


/*
  ---------- View ----------
 */
fun View.loadColor(@ColorRes id: Int): Int = context.loadColor(id)

fun View.loadDrawable(@ColorRes id: Int): Drawable = context.loadDrawable(id)

fun View.loadRaw(@RawRes id: Int): InputStream? = context.loadRaw(id)

fun View.loadRaw(@RawRes id: Int, value: TypedValue): InputStream? = context.loadRaw(id, value)

fun View.loadAsset(fileName: String, accessMode: Int = AssetManager.ACCESS_STREAMING): InputStream? = context.loadAsset(fileName, accessMode)

fun View.loadTypefaceFromAsset(fileName: String): Typeface = context.loadTypefaceFromAsset(fileName)
