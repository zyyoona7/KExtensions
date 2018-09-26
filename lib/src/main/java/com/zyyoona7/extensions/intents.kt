package com.zyyoona7.extensions

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.RequiresPermission

/**
 * Created by zyyoona7 on 2017/8/24.
 *
 * Intent 启动 扩展函数
 * Intent extensions
 */

inline fun <reified T : Context> Context.createIntent(extras: Bundle? = null): Intent {
    val intent = Intent(this, T::class.java)
    extras?.let {
        intent.putExtras(it)
    }
    return intent
}


/**
 * Activity startActivity extensions
 */

/**
 * @param extras
 *
 */
inline fun <reified T : Activity> Activity.startActivity(extras: Bundle? = null) {
    startActivity(createIntent<T>(extras))
}

/**
 * @param extras Nullable
 * @param requestCode
 */
inline fun <reified T : Activity> Activity.startActivityForResult(extras: Bundle? = null, requestCode: Int) {
    startActivityForResult(createIntent<T>(extras), requestCode)
}

/**
 * Service startActivity extensions
 */

/**
 * @param extras Nullable
 */
inline fun <reified T : Service> Service.startActivity(extras: Bundle? = null) {
    startActivity(createIntent<T>(extras))
}

/**
 * Fragment startActivity extensions
 */

/**
 * @param extras Nullable
 */
inline fun <reified T : Activity> Fragment.startActivity(extras: Bundle? = null) {
    startActivity(activity?.createIntent<T>(extras))
}

/**
 * @param extras
 * @param requestCode
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(extras: Bundle? = null, requestCode: Int) {
    startActivityForResult(activity?.createIntent<T>(extras), requestCode)
}

/**
 * @param extras Nullable
 */
inline fun <reified T : Activity> android.support.v4.app.Fragment.startActivity(extras: Bundle? = null) {
    startActivity(activity?.createIntent<T>(extras))
}

/**
 * @param extras
 * @param requestCode
 */
inline fun <reified T : Activity> android.support.v4.app.Fragment.startActivityForResult(extras: Bundle? = null, requestCode: Int) {
    startActivityForResult(activity?.createIntent<T>(extras), requestCode)
}

/**
 * Context startService extensions
 */
inline fun <reified T : Service> Context.startService(extras: Bundle? = null) {
    startService(createIntent<T>(extras))
}
/*
  ----------from anko----------
 */
/**
 *  send sms
 */
fun Fragment.sendSMS(number: String, text: String = ""): Boolean = activity?.sendSMS(number, text) ?:false

/**
 * @param number
 * @param text sms body
 */
fun Context.sendSMS(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
        intent.putExtra("sms_body", text)
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * call phone
 */
@RequiresPermission(value = "android.permission.CALL_PHONE")
fun Fragment.makeCall(number: String): Boolean = activity?.makeCall(number) ?: false

/**
 * @param number phone number
 */
@RequiresPermission(value = "android.permission.CALL_PHONE")
fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * send email
 */

fun Fragment.email(email: String, subject: String = "", text: String = "") = activity?.email(email, subject, text)

/**
 * @param email
 * @param subject
 * @param text
 */
fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if (subject.isNotEmpty())
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.isNotEmpty())
        intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false

}

/**
 * shared
 */
fun Fragment.share(text: String, subject: String = "") = activity?.share(text, subject)

/**
 * @param text
 * @param subject
 */
fun Context.share(text: String, subject: String = ""): Boolean {
    return try {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * browse web
 */
fun Fragment.browse(url: String, newTask: Boolean = false) = activity?.browse(url, newTask)

/**
 * @param url
 * @param newTask
 */
fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}