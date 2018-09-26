package com.zyyoona7.extensions

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
/*
 所有危险的 Android 系统权限都属于权限组。
 如果设备运行的是 Android 6.0（API 级别 23），并且应用的 targetSdkVersion 是 23 或更高版本，
 则当用户请求危险权限时系统会发生以下行为：

 1.如果应用请求其清单中列出的危险权限，而应用目前在权限组中没有任何权限，
   则系统会向用户显示一个对话框，描述应用要访问的权限组。对话框不描述该组内的具体权限。
   例如，如果应用请求 READ_CONTACTS 权限，系统对话框只说明该应用需要访问设备的联系信息。
   如果用户批准，系统将向应用授予其请求的权限。

 2.如果应用请求其清单中列出的危险权限，而应用在同一权限组中已有另一项危险权限，则系统会立即授予该权限，
   而无需与用户进行任何交互。例如，如果某应用已经请求并且被授予了 READ_CONTACTS 权限，
   然后它又请求 WRITE_CONTACTS，系统将立即授予该权限。

 Dangerous permissions and permission groups.

 Permission Group                            Permissions

 CALENDAR                                    READ_CALENDAR              日历相关
                                             WRITE_CALENDAR
 CAMERA                                      CAMERA                     访问相机或者拍照录像
 CONTACTS                                    READ_CONTACTS              联系人相关
                                             WRITE_CONTACTS
                                             GET_ACCOUNTS
 LOCATION                                    ACCESS_FINE_LOCATION       访问设备位置
                                             ACCESS_COARSE_LOCATION
 MICROPHONE                                  RECORD_AUDIO               访问麦克风音频相关
 PHONE                                       READ_PHONE_STATE           电话相关
                                             CALL_PHONE
                                             READ_CALL_LOG
                                             WRITE_CALL_LOG
                                             ADD_VOICEMAIL
                                             USE_SIP
                                             PROCESS_OUTGOING_CALLS
 SENSORS                                     BODY_SENSORS               身体传感器相关 如心率
 SMS                                         SEND_SMS                   短信消息相关
                                             RECEIVE_SMS
                                             READ_SMS
                                             RECEIVE_WAP_PUSH
                                             RECEIVE_MMS
 STORAGE                                     READ_EXTERNAL_STORAGE      共享外部存储相关
                                             WRITE_EXTERNAL_STORAGE
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

fun Fragment.isPermissionGranted(permission: String): Boolean = activity?.isPermissionGranted(permission) ?: true

fun Fragment.arePermissionGranted(vararg permissions: String): Boolean = activity?.arePermissionGranted(*permissions) ?:true

fun Fragment.isPermissionAlwaysDenied(permission: String): Boolean = activity?.isPermissionAlwaysDenied(permission)?:true

fun Fragment.arePermissionAlwaysDenied(vararg permissions: String): Boolean = activity?.arePermissionAlwaysDenied(*permissions) ?:true

fun Fragment.requestPermission(permissions: Array<out String>, requestCode: Int) = activity?.requestPermission(permissions, requestCode)

inline fun Fragment.requestPermissionWithRationale(permissions: Array<out String>, requestCode: Int, rationale: ((permissions: Array<out String>) -> Unit)) =
        activity?.requestPermissionWithRationale(permissions, requestCode, rationale)

fun android.support.v4.app.Fragment.isPermissionGranted(permission: String): Boolean = activity?.isPermissionGranted(permission) ?: true

fun android.support.v4.app.Fragment.arePermissionGranted(vararg permissions: String): Boolean = activity?.arePermissionGranted(*permissions) ?: true

fun android.support.v4.app.Fragment.isPermissionAlwaysDenied(permission: String): Boolean = activity?.isPermissionAlwaysDenied(permission) ?: true

fun android.support.v4.app.Fragment.arePermissionAlwaysDenied(vararg permissions: String): Boolean = activity?.arePermissionAlwaysDenied(*permissions) ?: true

fun android.support.v4.app.Fragment.requestPermission(permissions: Array<out String>, requestCode: Int) = activity?.requestPermission(permissions, requestCode)

inline fun android.support.v4.app.Fragment.requestPermissionWithRationale(permissions: Array<out String>, requestCode: Int, rationale: ((permissions: Array<out String>) -> Unit)) =
        activity?.requestPermissionWithRationale(permissions, requestCode, rationale)
