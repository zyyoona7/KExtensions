package com.zyyoona7.lib

import android.app.Fragment
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException


/**
 * Created by zyyoona7 on 2017/8/28.
 * 文件相关的扩展函数
 */

/*
  ---------- Context ----------
 */
/**
 * 获取应用文件目录
 *
 * 应用程序文件目录("/data/data/<包名>/files")
 */
val Context.fileDirPath: String
    get() = filesDir.absolutePath

/**
 * 获取应用缓存目录
 *
 * 应用程序缓存目录("/data/data/<包名>/cache")
 */
val Context.cacheDirPath: String
    get() = cacheDir.absolutePath

/**
 * 获取应用外置文件目录
 *
 * 应用程序文件目录("/Android/data/<包名>/files")
 */
val Context.externalFileDirPath: String
    get() = getExternalFilesDir("").absolutePath

/**
 * 获取应用外置缓存目录
 *
 * 应用程序缓存目录("/Android/data/<包名>/cache")
 */
val Context.externalCacheDirPath: String
    get() = externalCacheDir.absolutePath

/*
  ---------- Fragment ----------
 */
val Fragment.fileDirPath: String
    get() = activity.fileDirPath

val Fragment.cacheDirPath: String
    get() = activity.cacheDirPath

val Fragment.externalFileDirPath: String
    get() = activity.externalFileDirPath

val Fragment.externalCacheDirPath: String
    get() = activity.externalCacheDirPath

val android.support.v4.app.Fragment.fileDirPath: String
    get() = activity.fileDirPath

val android.support.v4.app.Fragment.cacheDirPath: String
    get() = activity.cacheDirPath

val android.support.v4.app.Fragment.externalFileDirPath: String
    get() = activity.externalFileDirPath

val android.support.v4.app.Fragment.externalCacheDirPath: String
    get() = activity.externalCacheDirPath

/*
  ---------- Any ----------
 */
/**
 * 获取公共下载文件夹路径
 */
val publicDownloadDir: String
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

/**
 * 内存卡是否挂载
 */
val isMountSdcard: Boolean
    get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/**
 * 通过文件路径获取File对象
 *
 * @param filePath
 * @return nullable
 */
fun getFileByPath(filePath: String): File? {
    return if (filePath.isBlank()) null else File(filePath)
}

/**
 * 判断文件是否存在
 *
 * @param file
 */
fun isFileExists(file: File): Boolean {
    return file.exists() && file.isFile
}

/**
 * 判断文件是否存在
 *
 * @param filePath
 */
fun isFileExists(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    file?.let {
        return isFileExists(it)
    }
    return false
}

/**
 * 判断文件夹是否存在
 *
 * @param file
 */
fun isDirExists(file: File): Boolean {
    return file.exists() && file.isDirectory
}

/**
 * 判断文件夹是否存在
 *
 * @param filePath
 */
fun isDirExists(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    file?.let {
        return isDirExists(it)
    }
    return false
}

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param file
 * @return true 文件夹存在或者创建成功  false 文件夹不存在或者创建失败
 */
fun createOrExistsDir(file: File): Boolean {
    // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
    return if (file.exists()) file.isDirectory else file.mkdirs()
}

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param filePath
 * @return true 文件夹存在或者创建成功  false 路径无效、文件夹不存在或者创建失败
 */
fun createOrExistsDir(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    file?.let {
        return createOrExistsDir(it)
    }
    return false
}

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @param file
 * @return true 文件存在或者创建成功  false 文件不存在或者创建失败
 */
fun createOrExistsFile(file: File): Boolean {
    if (file.exists()) return file.isFile
    if (!createOrExistsDir(file.parentFile)) return false

    return try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @param filePath
 * @return true 文件存在或者创建成功  false 路径无效、文件不存在或者创建失败
 */
fun createOrExistsFile(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    file?.let {
        return createOrExistsFile(file)
    }
    return false
}

/**
 * 获取文件夹目录大小
 *
 * @param dir
 * @return 文件大小 单位：B、KB、MB、GB
 */
fun getDirSize(dir: File): String {
    val len = getDirLength(dir)
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 * 获取文件夹目录大小
 *
 * @param dirPath
 * @return 文件大小 单位：B、KB、MB、GB
 */
fun getDirSize(dirPath: String): String {
    val len = getDirLength(dirPath)
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 * 获取目录长度
 *
 * @param dir
 * @return 目录长度
 */
fun getDirLength(dir: File): Long {
    if (!isDirExists(dir)) return -1
    var len: Long = 0
    val files: Array<File>? = dir.listFiles()
    files?.forEach {
        len += if (it.isDirectory) getDirLength(it) else it.length()
    }
    return len
}

/**
 * 获取目录长度
 *
 * @param dirPath
 * @return 目录长度
 */
fun getDirLength(dirPath: String): Long {
    val dir = getFileByPath(dirPath)
    dir?.let {
        return getDirLength(it)
    }
    return -1
}

/**
 * 获取文件大小
 *
 * @param file
 * @return 文件大小 单位：B、KB、MB、GB
 */
fun getFileSize(file: File): String {
    val len = getFileLength(file)
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 * 获取文件大小
 *
 * @param filePath
 * @return 文件大小 单位：B、KB、MB、GB
 */
fun getFileSize(filePath: String): String {
    val len = getFileLength(filePath)
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 * 获取文件长度
 *
 * @param file
 * @return 文件长度
 */
fun getFileLength(file: File): Long {
    return if (!isFileExists(file)) -1 else file.length()
}

/**
 * 获取文件长度
 *
 * @param filePath
 * @return 文件长度
 */
fun getFileLength(filePath: String): Long {
    val file = getFileByPath(filePath)
    file?.let {
        return getFileLength(it)
    }
    return -1
}

/**
 * 字节数转合适内存大小
 *
 * 保留3位小数
 *
 * @param byteNum 字节数
 * @return 合适内存大小
 */
private fun byte2FitMemorySize(byteNum: Long): String {
    return when {
        byteNum < 0 -> "shouldn't be less than zero!"
        byteNum < 1024 -> String.format("%.3fB", byteNum.toDouble() + 0.0005)
        byteNum < 1048576 -> String.format("%.3fKB", byteNum.toDouble() / 1024 + 0.0005)
        byteNum < 1073741824 -> String.format("%.3fMB", byteNum.toDouble() / 1048576 + 0.0005)
        else -> String.format("%.3fGB", byteNum.toDouble() / 1073741824 + 0.0005)
    }
}