package com.zyyoona7.lib

import android.util.Base64
import java.io.File
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * Created by zyyoona7 on 2017/9/12.
 *
 * 编码/解码相关拓展函数
 */

/*
  Base64：（Base64编码算法不算是真正的加密算法。）
  Base64是一种基于64个可打印字符来表示二进制数据的表示方法。
  Base64常用于在通常处理文本数据的场合，表示、传输、存储一些二进制数据。
 */

/**
 * Base64编码
 *
 */
fun String.base64Encode2Str(charset: Charset = Charsets.US_ASCII): String = String(Base64.encode(this.toByteArray(), Base64.DEFAULT), charset)

fun String.base64Encode(): ByteArray = Base64.encode(this.toByteArray(), Base64.DEFAULT)

/**
 * Base64编码
 *
 */
fun ByteArray.base64Encode2Str(charset: Charset = Charsets.US_ASCII): String = String(Base64.encode(this, Base64.DEFAULT), charset)

fun ByteArray.base64Encode(): ByteArray = Base64.encode(this, Base64.DEFAULT)

/**
 * Base64解码
 *
 */
fun String.base64Decode2Str(charset: Charset = Charsets.US_ASCII): String = String(Base64.decode(this, Base64.DEFAULT),charset)

fun String.base64Decode(): ByteArray = Base64.decode(this, Base64.DEFAULT)

fun ByteArray.base64Decode2Str(charset: Charset = Charsets.US_ASCII): String = String(Base64.decode(this, Base64.DEFAULT),charset)

fun ByteArray.base64Decode(): ByteArray = Base64.decode(this, Base64.DEFAULT)

/**
 * 文件Base64编码
 */
fun File.base64Encode(): String = Base64.encodeToString(this.readBytes(), Base64.DEFAULT)

/**
 * 字符串解码成文件类型
 *
 * @param filePath
 */
fun String.base64Decode(filePath: String): File? {
    val file = getFileByPath(filePath)
    file?.let {
        val bytes = Base64.decode(this, Base64.DEFAULT)
        it.writeBytes(bytes)
    }
    return file
}


/*
  UrlEncode/Decode
 */

fun String.urlEncode(charset: String = "UTF-8"): String = URLEncoder.encode(this, charset)

fun String.urlDecode(charset: String = "UTF-8"): String = URLDecoder.decode(this, charset)