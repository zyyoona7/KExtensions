package com.zyyoona7.lib

import android.app.Fragment
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

/**
 * Created by zyyoona7 on 2017/8/26.
 * 网络相关扩展函数
 */
/*
  ---------- Context ----------
 */
/**
 * 活动网络信息
 * 需添加权限 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */
val Context.networkInfo: NetworkInfo?
    get() = connectivityManager?.activeNetworkInfo

/**
 * 网络是否连接
 * 需添加权限 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *
 */
val Context.isNetworkConnected: Boolean
    get() = networkInfo?.isConnected ?: false

/**
 * 判断/设置wifi是否打开
 * 需添加权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 */
var Context.isWifiEnable: Boolean
    get() = wifiManager?.isWifiEnabled ?: false
    set(value) {
        wifiManager?.isWifiEnabled = value
    }

/**
 * 是否是WiFi连接
 * 需添加权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 */
val Context.isWifiConnected: Boolean
    get() = networkInfo?.type == ConnectivityManager.TYPE_WIFI

/**
 * 是否是移动数据连接
 * 需添加权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 */
val Context.isMobileConnected: Boolean
    get() = networkInfo?.type == ConnectivityManager.TYPE_MOBILE

/**
 * 获取网络运营商名称
 * 中国移动、中国联通、中国电信
 */
val Context.networkOperatorName: String?
    get() = telephonyManager?.networkOperatorName

/**
 * from https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/NetworkUtils.java
 * 获取当前网络类型
 * 需添加权限 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * 16=NETWORK_TYPE_GSM  17=NETWORK_TYPE_TD_SCDMA  18=NETWORK_TYPE_IWLAN
 */
val Context.networkType: NetworkType
    get() {
        var type: NetworkType = NetworkType.NETWORK_NO
        val isAvailable: Boolean = networkInfo?.isAvailable ?: false
        if (!isAvailable) return type

        if (networkInfo!!.type == ConnectivityManager.TYPE_WIFI) {
            type = NetworkType.NETWORK_WIFI
        } else if (networkInfo!!.type == ConnectivityManager.TYPE_MOBILE) {
            when (networkInfo!!.subtype) {
                16, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE,
                TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN
                -> type = NetworkType.NETWORK_2G

                17, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0,
                TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA,
                TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP
                -> type = NetworkType.NETWORK_3G

                18, TelephonyManager.NETWORK_TYPE_LTE
                -> type = NetworkType.NETWORK_4G

                else -> {
                    val subtypeName = networkInfo!!.subtypeName
                    type = if (subtypeName.equals("TD-SCDMA", ignoreCase = true)
                            || subtypeName.equals("WCDMA", ignoreCase = true)
                            || subtypeName.equals("CDMA2000", ignoreCase = true)) {
                        NetworkType.NETWORK_3G
                    } else {
                        NetworkType.NETWORK_UNKNOWN
                    }
                }
            }
        }
        return type
    }

/*
  ---------- Fragment ----------
 */

val Fragment.networkInfo: NetworkInfo?
    get() = activity.networkInfo

val Fragment.isNetworkConnected: Boolean
    get() = activity.isNetworkConnected

var Fragment.isWifiEnable: Boolean
    get() = activity.isWifiEnable
    set(value) {
        activity.isWifiEnable = value
    }

val Fragment.isWifiConnected: Boolean
    get() = activity.isWifiConnected

val Fragment.isMobileConnected: Boolean
    get() = activity.isMobileConnected

val Fragment.networkOperatorName: String?
    get() = activity.networkOperatorName

val Fragment.networkType: NetworkType
    get() = activity.networkType

val android.support.v4.app.Fragment.networkInfo: NetworkInfo?
    get() = activity.networkInfo

val android.support.v4.app.Fragment.isNetworkConnected: Boolean
    get() = activity.isNetworkConnected

var android.support.v4.app.Fragment.isWifiEnable: Boolean
    get() = activity.isWifiEnable
    set(value) {
        activity.isWifiEnable = value
    }

val android.support.v4.app.Fragment.isWifiConnected: Boolean
    get() = activity.isWifiConnected

val android.support.v4.app.Fragment.isMobileConnected: Boolean
    get() = activity.isMobileConnected

val android.support.v4.app.Fragment.networkOperatorName: String?
    get() = activity.networkOperatorName

val android.support.v4.app.Fragment.networkType: NetworkType
    get() = activity.networkType

enum class NetworkType {
    NETWORK_WIFI,
    NETWORK_4G,
    NETWORK_3G,
    NETWORK_2G,
    NETWORK_UNKNOWN,
    NETWORK_NO
}