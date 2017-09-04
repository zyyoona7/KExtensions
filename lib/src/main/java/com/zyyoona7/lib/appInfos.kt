package com.zyyoona7.lib

import android.app.ActivityManager
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import java.io.File


/**
 * Created by zyyoona7 on 2017/8/28.
 * Application信息相关扩展函数
 */

/*
  ---------- Context ----------
 */
/**
 * 获取应用的版本名称
 *
 * @param pkgName 包名
 * @return App版本号  ""表示失败
 */
fun Context.getVersionName(pkgName: String = packageName): String {
    if (pkgName.isBlank()) return ""
    return try {
        packageManager.getPackageInfo(pkgName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}

/**
 * 获取App版本码
 *
 * @param pkgName 包名
 * @return App版本码  -1表示失败
 */
fun Context.getVersionCode(pkgName: String = packageName): Int {
    if (pkgName.isBlank()) return -1
    return try {
        packageManager.getPackageInfo(pkgName, 0).versionCode
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        -1
    }
}

fun Context.installApp(file: File, authority: String, writeEnable: Boolean) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    setIntentDataAndType(intent, "application/vnd.android.package-archive", file, authority, writeEnable)
    startActivity(intent)
}

/**
 * 判断App是否安装
 *
 * @param pkgName 包名
 * @return
 */
fun Context.isInstallApp(pkgName: String): Boolean {
    return pkgName.isNotBlank() && packageManager.getLaunchIntentForPackage(packageName) != null
}

/**
 * 判断App是否处于前台
 *
 * @return
 */
fun Context.isAppForeground(pkgName: String = packageName): Boolean {
    if (pkgName.isBlank()) return false
    val infoList: (List<ActivityManager.RunningAppProcessInfo>)? = activityManager?.runningAppProcesses
    infoList?.forEach {
        if (it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return it.processName == pkgName
        }
    }
    return false
}

/**
 * 是否是平板设备
 *
 * @return true 是， false 不是
 */
fun Context.isTablet(): Boolean {
    return this.resources.configuration.screenLayout and Configuration
            .SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

/*
  ---------- Fragment ----------
 */
fun Fragment.getVersionName(pkgName: String = activity.packageName): String {
    return activity.getVersionName(pkgName)
}

fun Fragment.getVersionCode(pkgName: String = activity.packageName): Int {
    return activity.getVersionCode(pkgName)
}

fun Fragment.installApp(file: File, authority: String, writeEnable: Boolean) {
    activity.installApp(file, authority, writeEnable)
}

fun Fragment.isInstallApp(pkgName: String): Boolean {
    return activity.isInstallApp(pkgName)
}

fun Fragment.isAppForeground(pkgName: String = activity.packageName): Boolean {
    return activity.isAppForeground(pkgName)
}

fun Fragment.isTablet(): Boolean {
    return activity.isTablet()
}

fun android.support.v4.app.Fragment.getVersionName(pkgName: String = activity.packageName): String {
    return activity.getVersionName(pkgName)
}

fun android.support.v4.app.Fragment.getVersionCode(pkgName: String = activity.packageName): Int {
    return activity.getVersionCode(pkgName)
}

fun android.support.v4.app.Fragment.installApp(file: File, authority: String, writeEnable: Boolean) {
    activity.installApp(file, authority, writeEnable)
}

fun android.support.v4.app.Fragment.isInstallApp(pkgName: String): Boolean {
    return activity.isInstallApp(pkgName)
}

fun android.support.v4.app.Fragment.isAppForeground(pkgName: String = activity.packageName): Boolean {
    return activity.isAppForeground(pkgName)
}

fun android.support.v4.app.Fragment.isTablet(): Boolean {
    return activity.isTablet()
}