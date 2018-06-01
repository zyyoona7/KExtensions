package com.zyyoona7.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by zyyoona7 on 2017/8/30.
 * 日期转换相关的拓展函数
 */

/*
SimpleDateFormat https://developer.android.com/reference/java/text/SimpleDateFormat.html
匹配格式

Date转换成年
 G               ---- 公元
 y               ---- 2017
 yy              ---- 17
 yyy             ---- 2017
 yyyy            ---- 2017
 yyyyy           ---- 002017

Date转换成月份格式
 M               ---- 8        （个位数时不以0补齐）
 MM              ---- 08       （个位数时以0补齐）
 MMM             ---- 8月
 MMMM            ---- 八月
 L               ---- 8        （个位数时不以0补齐）
 LL              ---- 08       （个位数时以0补齐）
 LLL             ---- 8月
 LLLL            ---- 八月

Date转换成星期格式
 E/EE/EEE        ---- 周几
 EEEE            ---- 星期几
 EEEEE           ---- 几

Date转换成周数
 w(small)        ---- 年中的周数   （个位数时不以0补齐）
 ww(small)       ---- 年中的周数   （个位数时以0补齐）
 W(big)          ---- 月份中的周数  （个位数时不以0补齐）
 WW(big)         ---- 月份中的周数  （个位数时以0补齐）

Date转换成天数
 d               ---- 1        （日期9月1号）月份中的第几天 （个位数时不以0补齐）
 dd              ---- 01       （日期9月1号）月份中的第几天 （个位数时以0补齐）
 D               ---- 244      （日期9月1号）一年当中的第几天

输出当前时间是上午/下午
 a               ---- 上午

输出当前时间的小时位
 H               ---- 9        （9:00）Hour in day (0-23) 24时制   （个位数时不以0补齐）
 HH              ---- 09       （9:00）Hour in day (0-23) 24时制   （个位数时以0补齐）
 K               ---- 9        （9:00）Hour in am/pm (0-11) 12时制 （个位数时不以0补齐）
 KK              ---- 09       （9:00）Hour in am/pm (0-11) 12时制 （个位数时以0补齐）

输出当前时间的分钟位
 m               ---- 4         分钟数（个位数时不以0补齐）
 mm              ---- 04        分钟数（个位数时以0补齐）

输出当前时间的秒位
 s               ---- 5        （个位数时不以0补齐）
 ss              ---- 05       （个位数时以0补齐）

输出当前时间的毫秒位
 S               ---- 1         保留一位
 SS              ---- 10        保留两位
 SSS             ---- 100       保留三位

时区
 z               ---- GMT+08:00
 zzzz            ---- 中国标准时间
 Z               ---- +0800
*/
/**
 * 单例默认的日期格式化
 */
internal object DefaultDateFormat {

    private const val DEFAULT_DATE_STR = "yyyy-MM-dd HH:mm:ss"
    val DEFAULT_FORMAT = ThreadLocal<SimpleDateFormat>().apply { set(SimpleDateFormat(DEFAULT_DATE_STR)) }
}

/**
 * 当前时间毫秒值
 */
val currentTimeMills: Long get() = System.currentTimeMillis()

/**
 * 当前时间格式化成指定格式的String类型
 */
fun currentTimeString(format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String = currentTimeMills.format2DateString(format)

/**
 * 当前时间的Date类型
 */
val currentDate: Date get() = Date()

/**
 * Date类型格式化成指定格式的String类型
 *
 * @param format
 */
fun Date.format2String(format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String = format.format(this)

/**
 * Date类型格式化成指定格式的String类型
 *
 * @param formatPattern
 */
@SuppressLint("SimpleDateFormat")
fun Date.format2String(formatPattern: String): String = format2String(SimpleDateFormat(formatPattern))

/**
 * Long类型格式化成指定格式的String类型的日期
 *
 * @param formatPattern
 */
fun Long.format2DateString(formatPattern: String): String = Date(this).format2String(formatPattern)

/**
 * Long类型格式化成指定格式的String类型的日期
 *
 * @param format
 */
fun Long.format2DateString(format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String = Date(this).format2String(format)

/**
 * 解析String类型的日期为Long类型
 *
 * @param time
 * @param format
 */
fun parseDateString2Mills(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): Long {
    return try {
        format.parse(time).time
    } catch (e: ParseException) {
        e.printStackTrace()
        -1L
    }
}

/**
 * 解析String类型的日期为Date类型
 *
 * @param time
 * @param format
 */
fun parseString2Date(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): Date {
    return try {
        format.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
        Date()
    }
}

/**
 * 获取两个日期的时间差
 *
 * @param otherCalendar  默认值：当前日期
 * @param unit       返回值的时间单位  默认值：天
 */
fun Calendar.getTimeSpan(otherCalendar: Calendar = Calendar.getInstance(), unit: TimeUnit): Long =
        calculateTimeSpan(Math.abs(this.timeInMillis - otherCalendar.timeInMillis), unit)

/**
 * 获取两个日期的时间差
 *
 * @param otherDate  默认值：当前日期
 * @param unit       返回值的时间单位  默认值：天
 */
fun Date.getTimeSpan(otherDate: Date = Date(), unit: TimeUnit = TimeUnit.DAYS): Long =
        calculateTimeSpan(Math.abs(this.time - otherDate.time), unit)

/**
 * 获取两个日期的时间差
 *
 * @param otherMills  默认值：当前时间毫秒值
 * @param unit        返回值的时间单位  默认值：天
 */
fun Long.getTimeSpan(otherMills: Long = currentTimeMills, unit: TimeUnit = TimeUnit.DAYS): Long =
        calculateTimeSpan(Math.abs(this - otherMills), unit)

/**
 * 获取两个日期的时间差
 * （如果使用当前日期的默认值做比较，DateFormat必须是默认类型，否则需要全部替换掉默认参数）
 *
 * @param time1   默认值：当前日期
 * @param time2
 * @param format  默认值："yyyy-MM-dd HH:mm:ss"格式的format
 * @param unit    返回值的时间单位  默认值：天
 */
fun getTimeSpan(time1: String = currentTimeString(), time2: String,
                format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get(),
                unit: TimeUnit = TimeUnit.DAYS): Long =
        calculateTimeSpan(Math.abs(parseDateString2Mills(time1, format) -
                parseDateString2Mills(time2, format)), unit)

/**
 * 计算时间间隔
 *
 * @param diffMills 时间差值
 * @param unit
 */
fun calculateTimeSpan(diffMills: Long, unit: TimeUnit): Long = when (unit) {
    TimeUnit.NANOSECONDS -> TimeUnit.MILLISECONDS.toNanos(diffMills)
    TimeUnit.MICROSECONDS -> TimeUnit.MILLISECONDS.toMicros(diffMills)
    TimeUnit.MILLISECONDS -> TimeUnit.MILLISECONDS.toMillis(diffMills)
    TimeUnit.SECONDS -> TimeUnit.MILLISECONDS.toSeconds(diffMills)
    TimeUnit.MINUTES -> TimeUnit.MILLISECONDS.toMinutes(diffMills)
    TimeUnit.HOURS -> TimeUnit.MILLISECONDS.toHours(diffMills)
    else -> TimeUnit.MILLISECONDS.toDays(diffMills)
}

/**
 * 将时间戳转换成 xx小时前 的样式（同微博）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果在1小时外的今天内，显示今天15:32
 * 如果是昨天的，显示昨天15:32
 * 如果是同一年，显示 09-01 15:32
 * 其余显示，2017-09-01
 */
fun formatAgoStyleForWeibo(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String = parseDateString2Mills(time, format).formatAgoStyleForWeibo()

/**
 * 将时间戳转换成 xx小时前 的样式（同微信）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果是昨天，显示昨天
 * 如果在一个月内，显示xx天前
 * 如果在一年内，显示xx月前
 * 如果在两年内，显示xx年前
 * 其余显示，2017-09-01
 */
fun formatAgoStyleForWeChat(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): String = parseDateString2Mills(time, format).formatAgoStyleForWeChat()

/**
 * 将时间戳转换成 xx小时前 的样式（同微博）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果在1小时外的今天内，显示今天15:32
 * 如果是昨天的，显示昨天15:32
 * 如果是同一年，显示 09-01 15:32
 * 其余显示，2017-09-01
 */
fun Date.formatAgoStyleForWeibo(): String = this.time.formatAgoStyleForWeibo()

/**
 * 将时间戳转换成 xx小时前 的样式（同微信）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果是昨天，显示昨天
 * 如果在一个月内，显示xx天前
 * 如果在一年内，显示xx月前
 * 如果在两年内，显示xx年前
 * 其余显示，2017-09-01
 */
fun Date.formatAgoStyleForWeChat(): String = time.formatAgoStyleForWeChat()

/**
 * 将时间戳转换成 xx小时前 的样式（同微博）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果在1小时外的今天内，显示今天15:32
 * 如果是昨天的，显示昨天15:32
 * 如果是同一年，显示 09-01 15:32
 * 其余显示，2017-09-01
 */
fun Long.formatAgoStyleForWeibo(): String {
    val now = currentTimeMills
    val span = now - this
    return when {
        span <= TimeUnit.SECONDS.toMillis(1) -> "刚刚"
        span <= TimeUnit.MINUTES.toMillis(1) -> String.format("%d秒前", span / TimeUnit.SECONDS.toMillis(1))
        span <= TimeUnit.HOURS.toMillis(1) -> String.format("%d分钟前", span / TimeUnit.MINUTES.toMillis(1))
        span <= TimeUnit.DAYS.toMillis(1) -> String.format("%d小时前", span / TimeUnit.HOURS.toMillis(1))
        span >= TimeUnit.DAYS.toMillis(1) && span <= TimeUnit.DAYS.toMillis(1) * 2 -> String.format("昨天%tR", this)
        isSameYear(now) -> String.format("%tm-%td %tR", this, this, this)
        else -> String.format("%tF", this)
    }
}

/**
 * 将时间戳转换成 xx小时前 的样式（同微信）
 *
 * @return
 *
 * 如果小于1秒钟内，显示刚刚
 * 如果在1分钟内，显示xx秒前
 * 如果在1小时内，显示xx分钟前
 * 如果是昨天，显示昨天
 * 如果在一个月内，显示xx天前
 * 如果在一年内，显示xx月前
 * 如果在两年内，显示xx年前
 * 其余显示，2017-09-01
 */
fun Long.formatAgoStyleForWeChat(): String {
    val now = currentTimeMills
    val span = now - this
    loge("span=$span")
    return when {
        span <= TimeUnit.SECONDS.toMillis(1) -> "刚刚"
        span <= TimeUnit.MINUTES.toMillis(1) -> String.format("%d秒前", span / TimeUnit.SECONDS.toMillis(1))
        span <= TimeUnit.HOURS.toMillis(1) -> String.format("%d分钟前", span / TimeUnit.MINUTES.toMillis(1))
        span <= TimeUnit.DAYS.toMillis(1) -> String.format("%d小时前", span / TimeUnit.HOURS.toMillis(1))
        span >= TimeUnit.DAYS.toMillis(1) && span <= TimeUnit.DAYS.toMillis(1) * 2 -> "昨天"
        span <= TimeUnit.DAYS.toMillis(1) * 30 -> String.format("%d天前", span / TimeUnit.DAYS.toMillis(1))
        span <= TimeUnit.DAYS.toMillis(1) * 30 * 12 -> String.format("%d月前", span / (TimeUnit.DAYS.toMillis(1) * 30))
        span <= TimeUnit.DAYS.toMillis(1) * 30 * 12 * 2 -> String.format("%d年前", span / (TimeUnit.DAYS.toMillis(1) * 30 * 12))
        else -> String.format("%tF", this)
    }
}

/**
 * 判断两个毫秒值是否在同一年
 */
fun Long.isSameYear(otherMills: Long): Boolean {
    val cal = Calendar.getInstance()
    cal.time = Date(this)
    val cal1 = Calendar.getInstance()
    cal1.time = Date(otherMills)
    return cal[Calendar.YEAR] == cal1[Calendar.YEAR]
}

fun Date.isSameYear(otherDate: Date): Boolean {
    val cal = Calendar.getInstance()
    cal.time = this
    val cal1 = Calendar.getInstance()
    cal1.time = otherDate
    return cal[Calendar.YEAR] == cal1[Calendar.YEAR]
}

/**
 * 日期是否在两个日期之间
 *
 * @param minCal 最小日期
 * @param maxCal 最大日期
 */
fun Date.betweenDates(minCal: Calendar, maxCal: Calendar): Boolean = betweenDates(minCal.time, maxCal.time)

/**
 * 日期是否在两个日期之间
 *
 * @param minDate 最小日期
 * @param maxDate 最大日期
 */
fun Date.betweenDates(minDate: Date, maxDate: Date): Boolean =
        (this == minDate || this.after(minDate)) // >= minCal
                && this.before(maxDate) // && < maxCal

/**
 * 将日期时间设置为0点，00:00:00:0
 */
fun Calendar.ofTimeZero(): Calendar {
    return apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}

/**
 * 获取星期的下标
 *
 * @return 星期日 为1
 */
val Date.dayOfWeek: Int
    get() {
        val cal = Calendar.getInstance()
        cal.time = this
        return cal.get(Calendar.DAY_OF_WEEK)
    }

/**
 * 获取星期的下标
 *
 * @return 星期日 为1
 */
val Calendar.dayOfWeek: Int
    get() = get(Calendar.DAY_OF_WEEK)

/**
 * 获取星期的下标
 *
 * @return 星期日 为1
 */
val Long.dayOfWeek: Int
    get() {
        val cal = Calendar.getInstance()
        cal.timeInMillis = this
        return cal.get(Calendar.DAY_OF_WEEK)
    }

/**
 * 获取星期的下标
 *
 * @param time
 * @param format
 * @return 星期日 为1
 */
fun dayOfWeek(time: String, format: DateFormat = DefaultDateFormat.DEFAULT_FORMAT.get()): Int {
    return parseString2Date(time, format).dayOfWeek
}