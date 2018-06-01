package com.zyyoona7.extensions.log

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by zyyoona7 on 2017/8/25.
 * 日志打印类
 */
object ZLog {

    var logEnabled = true
    var logGlobalTag = ""

    private val TOP_LEFT_CORNER = '╔'
    private val BOTTOM_LEFT_CORNER = '╚'
    private val MIDDLE_CORNER = '╟'
    private val VERTICAL_DOUBLE_LINE = '║'
    private val HORIZONTAL_DOUBLE_LINE = "═════════════════════════════════════════════════"
    private val SINGLE_LINE = "─────────────────────────────────────────────────"

    private val TOP_BORDER = TOP_LEFT_CORNER + HORIZONTAL_DOUBLE_LINE + HORIZONTAL_DOUBLE_LINE
    private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER + HORIZONTAL_DOUBLE_LINE + HORIZONTAL_DOUBLE_LINE
    private val MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_LINE + SINGLE_LINE

    private val CALL_INDEX = 5


    fun v(msg: String, customTag: String = "") {
        log(Log.VERBOSE, customTag, msg)
    }

    fun d(msg: String, customTag: String = "") {
        log(Log.DEBUG, customTag, msg)
    }

    fun i(msg: String, customTag: String = "") {
        log(Log.INFO, customTag, msg)
    }

    fun w(msg: String, customTag: String = "") {
        log(Log.WARN, customTag, msg)
    }

    fun e(msg: String, customTag: String = "") {
        log(Log.ERROR, customTag, msg)
    }

    fun json(msg: String, customTag: String = "") {
        val json = formatJson(msg)
        log(Log.ERROR, customTag, json)
    }

    /**
     * 格式化json
     * @param json
     */
    private fun formatJson(json: String): String {
        return try {
            val trimJson = json.trim()
            when {
                trimJson.startsWith("{") -> JSONObject(trimJson).toString(4)
                trimJson.startsWith("[") -> JSONArray(trimJson).toString(4)
                else -> trimJson
            }
        } catch (e: JSONException) {
            e.printStackTrace().toString()
        }

    }

    /**
     * 输出日志
     * @param priority 日志级别
     * @param customTag
     * @param msg
     */
    private fun log(priority: Int, customTag: String, msg: String) {
        if (!logEnabled) return

        val elements = Thread.currentThread().stackTrace
        val index = findIndex(elements)
        val element = elements[index]
        val tag = handleTag(element, customTag)
        var message = msg
        if (msg.contains("\n")) {
            message = msg.replace("\n".toRegex(), "\n$VERTICAL_DOUBLE_LINE ")
        }

        Log.println(priority, tag, handleFormat(element, message))
    }

    /**
     * 格式化log
     * @param element
     */
    private fun handleFormat(element: StackTraceElement, msg: String): String {

        return StringBuilder().apply {
            append(TOP_BORDER)
            appendln()
            // 添加当前线程名
            append("║ " + "Thread: " + Thread.currentThread().name)
            appendln()
            append(MIDDLE_BORDER)
            appendln()
            // 添加类名、方法名、行数
            append("║ ").append(element.className)
                    .append(".")
                    .append(element.methodName).append(" (")
                    .append(element.fileName)
                    .append(":")
                    .append(element.lineNumber)
                    .append(")")
            appendln()
            append(MIDDLE_BORDER)
            appendln()
            // 添加打印的日志信息
            append("$VERTICAL_DOUBLE_LINE ")
            append("$msg")
            appendln()
            append(BOTTOM_BORDER)
            appendln()
        }.toString()
    }

    /**
     * 处理tag逻辑
     * @param element
     * @param customTag
     */
    private fun handleTag(element: StackTraceElement, customTag: String): String = when {
        customTag.isNotBlank() -> customTag
        logGlobalTag.isNotBlank() -> logGlobalTag
        else -> element.className.substringAfterLast(".")
    }

    /**
     * 寻找当前调用类在[elements]中的下标
     * @param elements
     */
    private fun findIndex(elements: Array<StackTraceElement>): Int {
        var index = CALL_INDEX
        while (index < elements.size) {
            val className = elements[index].className
            if (className != ZLog::class.java.name && !elements[index].methodName.startsWith("log")) {
                return index
            }
            index++
        }
        return -1
    }
}