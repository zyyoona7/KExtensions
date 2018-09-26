package com.zyyoona7.extensions

import android.util.Base64
import java.io.File
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * Created by zyyoona7 on 2017/9/12.
 *
 * 编码/解码相关扩展函数
 */

/**
 * 文件Base64编码
 */
fun File.base64Encode(flags: Int = Base64.NO_WRAP): String = Base64.encodeToString(this.readBytes(), flags)

/**
 * 字符串解码成文件类型
 *
 * @param filePath
 */
fun String.base64Decode(filePath: String, flags: Int = Base64.NO_WRAP): File? {
    val file = getFileByPath(filePath)
    file?.let {
        val bytes = Base64.decode(this, flags)
        it.writeBytes(bytes)
    }
    return file
}
