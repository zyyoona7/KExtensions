package com.zyyoona7.lib

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.AppOpsManagerCompat
import android.support.v4.content.ContextCompat

/**
 * Created by zyyoona7 on 2017/9/5.
 *
 * 动态权限相关扩展函数
 */

/**
 * [permission]权限是否已经授权
 * from https://github.com/yanzhenjie/AndPermission
 *
 * @param permission
 */
fun Context.isPermissionGranted(permission: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true
    val opPermission: String? = AppOpsManagerCompat.permissionToOp(permission)
    if (!opPermission.isNullOrEmpty()) {
        val result = AppOpsManagerCompat.noteProxyOp(this, opPermission!!, packageName)
        if (result == AppOpsManagerCompat.MODE_IGNORED) return false
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) return false
    }

    return true
}

/**
 * [permissions]所有权限是否已经授权
 *
 * @param permissions
 */
fun Context.arePermissionGranted(vararg permissions: String): Boolean =
        permissions.all { isPermissionGranted(it) }

/**
 * [permission]权限是否被拒绝或不再提示
 *
 * @param permission
 */
fun Activity.isPermissionAlwaysDenied(permission: String): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return false
    return !shouldShowRequestPermissionRationale(permission)
}

/**
 * [permissions]所有权限是否被拒绝或不再提示
 *
 * @param permissions
 */
fun Activity.arePermissionAlwaysDenied(vararg permissions: String): Boolean =
        permissions.all { isPermissionAlwaysDenied(it) }

/**
 * 请求[permissions]授权
 *
 * @param permissions
 * @param requestCode
 */
fun Activity.requestPermission(permissions: Array<out String>, requestCode: Int) =
        ActivityCompat.requestPermissions(this, permissions, requestCode)

/**
 * 请求[permissions]授权
 *
 * @param permissions
 * @param requestCode
 * @param rationale
 */
inline fun Activity.requestPermissionWithRationale(permissions: Array<out String>, requestCode: Int, rationale: ((permissions: Array<out String>) -> Unit)) {
    permissions.forEach {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, it)) {
            rationale(permissions)
            return
        }
    }
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

/**
 * 处理请求权限结果
 *
 * @param permissions
 * @param grantResults
 * @param success
 * @param fail
 */
fun handlePermissionResult(permissions: Array<out String>, grantResults: IntArray,
                           success: ((permissions: List<String>) -> Unit)={},
                           fail: ((permissions: List<String>) -> Unit)={}) {
    val deniedList = arrayListOf<String>()
    permissions.indices
            .filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }
            .forEach { deniedList += permissions[it] }
    if (deniedList.isNotEmpty()) {
        fail(deniedList)
    } else {
        success(arrayListOf(*permissions))
    }
}

fun Fragment.isPermissionGranted(permission: String): Boolean = activity.isPermissionGranted(permission)

fun Fragment.arePermissionGranted(vararg permissions: String): Boolean = activity.arePermissionGranted(*permissions)

fun Fragment.isPermissionAlwaysDenied(permission: String): Boolean = activity.isPermissionAlwaysDenied(permission)

fun Fragment.arePermissionAlwaysDenied(vararg permissions: String): Boolean = activity.arePermissionAlwaysDenied(*permissions)

fun Fragment.requestPermission(permissions: Array<out String>, requestCode: Int) = activity.requestPermission(permissions, requestCode)

inline fun Fragment.requestPermissionWithRationale(permissions: Array<out String>, requestCode: Int, rationale: ((permissions: Array<out String>) -> Unit)) =
        activity.requestPermissionWithRationale(permissions, requestCode, rationale)

fun android.support.v4.app.Fragment.isPermissionGranted(permission: String): Boolean = activity.isPermissionGranted(permission)

fun android.support.v4.app.Fragment.arePermissionGranted(vararg permissions: String): Boolean = activity.arePermissionGranted(*permissions)

fun android.support.v4.app.Fragment.isPermissionAlwaysDenied(permission: String): Boolean = activity.isPermissionAlwaysDenied(permission)

fun android.support.v4.app.Fragment.arePermissionAlwaysDenied(vararg permissions: String): Boolean = activity.arePermissionAlwaysDenied(*permissions)

fun android.support.v4.app.Fragment.requestPermission(permissions: Array<out String>, requestCode: Int) = activity.requestPermission(permissions, requestCode)

inline fun android.support.v4.app.Fragment.requestPermissionWithRationale(permissions: Array<out String>, requestCode: Int, rationale: ((permissions: Array<out String>) -> Unit)) =
        activity.requestPermissionWithRationale(permissions, requestCode, rationale)
