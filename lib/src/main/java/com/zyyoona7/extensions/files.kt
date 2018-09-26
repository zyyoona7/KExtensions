package com.zyyoona7.extensions

import android.app.Fragment
import android.content.Context
import android.os.Environment
import java.io.*


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
    get() = getExternalFilesDir("")?.absolutePath ?: ""

/**
 * 获取应用外置缓存目录
 *
 * 应用程序缓存目录("/Android/data/<包名>/cache")
 */
val Context.externalCacheDirPath: String
    get() = externalCacheDir?.absolutePath ?: ""

/*
  ---------- Fragment ----------
 */
val Fragment.fileDirPath: String
    get() = activity?.fileDirPath ?: ""

val Fragment.cacheDirPath: String
    get() = activity?.cacheDirPath ?: ""

val Fragment.externalFileDirPath: String
    get() = activity?.externalFileDirPath ?: ""

val Fragment.externalCacheDirPath: String
    get() = activity?.externalCacheDirPath ?: ""

val android.support.v4.app.Fragment.fileDirPath: String
    get() = activity?.fileDirPath ?: ""

val android.support.v4.app.Fragment.cacheDirPath: String
    get() = activity?.cacheDirPath ?: ""

val android.support.v4.app.Fragment.externalFileDirPath: String
    get() = activity?.externalFileDirPath ?:""

val android.support.v4.app.Fragment.externalCacheDirPath: String
    get() = activity?.externalCacheDirPath ?:""

/*
  ---------- File/Any ----------
 */
/**
 * 获取公共下载文件夹路径
 */
val publicDownloadDir: String
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath

/**
 * 获取公共的照片文件夹路径
 */
val publicDCIMDir: String
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath

/**
 * 获取公共的图片文件夹路径
 */
val publicPictureDir: String
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath

/**
 * 获取公共的音乐文件夹路径
 */
val publicMusicDir: String
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath

/**
 * 获取公共的电影文件夹路径
 */
val publicMovieDir: String
    get() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).absolutePath


/**
 * 内存卡是否挂载
 */
val isExternalStorageWritable: Boolean
    get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/**
 * 通过文件路径获取File对象
 *
 * @param filePath
 * @return nullable
 */
fun getFileByPath(filePath: String): File? = if (filePath.isBlank()) null else File(filePath)

/**
 * 判断文件是否存在
 *
 */
val File.isFileExists: Boolean get() = exists() && isFile

/**
 * 判断文件是否存在
 *
 * @param filePath
 */
fun isFileExists(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    return file?.isFileExists ?: false
}

/**
 * 判断文件夹是否存在
 *
 */
val File.isDirExists: Boolean get() = exists() && isDirectory

/**
 * 判断文件夹是否存在
 *
 * @param filePath
 */
fun isDirExists(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    return file?.isDirExists ?: false
}

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @return true 文件夹存在或者创建成功  false 文件夹不存在或者创建失败
 */
fun File.createOrExistsDir(): Boolean =
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        if (exists()) isDirectory else mkdirs()

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param filePath
 * @return true 文件夹存在或者创建成功  false 路径无效、文件夹不存在或者创建失败
 */
fun createOrExistsDir(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    return file?.createOrExistsDir() ?: false
}

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @return true 文件存在或者创建成功  false 文件不存在或者创建失败
 */
fun File.createOrExistsFile(): Boolean {
    if (exists()) return isFile
    if (parentFile?.createOrExistsDir() != true) return false

    return try {
        createNewFile()
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
    return file?.createOrExistsFile() ?: false
}

/**
 * 获取文件夹目录大小
 *
 * @return 文件大小 单位：B、KB、MB、GB
 */
val File.dirSize: String
    get() = if (dirLength == -1L) "" else byte2FitMemorySize(dirLength)

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
 * @return 目录长度
 */
val File.dirLength: Long
    get() {
        if (!isDirExists) return -1
        var len: Long = 0
        val files: Array<File>? = listFiles()
        files?.forEach {
            len += if (it.isDirectory) it.dirLength else it.length()
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
    return dir?.dirLength ?: -1
}

/**
 * 获取文件大小
 *
 * @return 文件大小 单位：B、KB、MB、GB
 */
val File.fileSize: String
    get() = if (fileLength == -1L) "" else byte2FitMemorySize(fileLength)

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
 * @return 文件长度
 */
val File.fileLength: Long get() = if (!isFileExists) -1 else length()

/**
 * 获取文件长度
 *
 * @param filePath
 * @return 文件长度
 */
fun getFileLength(filePath: String): Long {
    val file = getFileByPath(filePath)
    return file?.fileLength ?: -1
}

/**
 * 字节数转合适内存大小
 *
 * 保留3位小数
 *
 * @param byteNum 字节数
 * @return 合适内存大小
 */
private fun byte2FitMemorySize(byteNum: Long): String = when {
    byteNum < 0 -> "shouldn't be less than zero!"
    byteNum < 1024 -> String.format("%.3fB", byteNum.toDouble() + 0.0005)
    byteNum < 1048576 -> String.format("%.3fKB", byteNum.toDouble() / 1024 + 0.0005)
    byteNum < 1073741824 -> String.format("%.3fMB", byteNum.toDouble() / 1048576 + 0.0005)
    else -> String.format("%.3fGB", byteNum.toDouble() / 1073741824 + 0.0005)
}

/**
 * 获取全路径中的最长目录
 *
 * @return 最长目录
 */
val File.dirName: String get() = getDirName(path)

/**
 * 获取全路径中的最长目录
 *
 * @param filePath
 * @return 最长目录
 */
fun getDirName(filePath: String): String =
        if (filePath.isBlank()) filePath else filePath.substringBeforeLast(File.separator)

/**
 * 获取全路径中的文件名
 *
 * @return 文件名
 */
val File.fileName: String get() = getFileName(path)

/**
 * 获取全路径中的文件名
 *
 * @param filePath
 * @return 文件名
 */
fun getFileName(filePath: String): String =
        if (filePath.isBlank()) filePath else filePath.substringAfterLast(File.separator)

/*
  ---------- 读/写文件----------
 */
/**
 * 将字符串写入文件
 *
 * @param content 写入内容
 * @param append  是否追加在文件末
 * @return true 写入成功 false 写入失败
 * @exception Exception
 */
fun File.writeStringAsFile(content: String, append: Boolean = false): Boolean {
    if (!createOrExistsFile()) return false

    BufferedWriter(FileWriter(this, append)).use {
        it.write(content)
        return true
    }
}

/**
 * 将字符串写入文件
 *
 * @param filePath    文件
 * @param content 写入内容
 * @param append  是否追加在文件末
 * @return true 写入成功 false 写入失败
 * @exception Exception
 */
fun writeStringAsFile(filePath: String, content: String, append: Boolean = false): Boolean {
    val file = getFileByPath(filePath)
    return file?.writeStringAsFile(content, append) ?: false
}

/**
 * 将输入流写入文件
 *
 * @param inputStream
 * @param append
 * @return true  false
 */
fun File.writeISAsFile(inputStream: InputStream, append: Boolean = false): Boolean {
    if (!createOrExistsFile()) return false

    BufferedOutputStream(FileOutputStream(this, append)).use {
        inputStream.copyTo(it)
        return true
    }
}

/**
 * 将输入流写入文件
 *
 * @param filePath
 * @param inputStream
 * @param append
 * @return true  false
 */
fun writeISAsFile(filePath: String, inputStream: InputStream, append: Boolean = false): Boolean {
    val file = getFileByPath(filePath)

    return file?.writeISAsFile(inputStream, append) ?: false
}

/**
 * 读取文件到字符串中
 *
 * @param charsetName 编码格式
 * @return 字符串
 */
fun File.readFileAsString(charsetName: String = ""): String {
    if (!isFileExists) return ""
    val reader: BufferedReader =
            if (charsetName.isBlank()) BufferedReader(InputStreamReader(FileInputStream(this))) else
                BufferedReader(InputStreamReader(FileInputStream(this), charsetName))

    val sb = StringBuilder()
    reader.forEachLine {
        if (sb.isNotBlank()) sb.appendln()
        sb.append(it)
    }
    return sb.toString()
}

/**
 * 读取文件到字符串中
 *
 * @param filePath    文件路径
 * @param charsetName 编码格式
 * @return 字符串
 */
fun readFileAsString(filePath: String, charsetName: String = ""): String {
    val file = getFileByPath(filePath)
    return file?.readFileAsString(charsetName) ?: ""
}

/**
 * 读取文件到字符串列表中
 * Do not use this function for huge files.
 * @param charsetName
 * @return List<String>
 */
fun File.readFileAsList(charsetName: String = ""): List<String> {
    if (!isFileExists) return emptyList()
    val reader: BufferedReader =
            if (charsetName.isBlank()) BufferedReader(InputStreamReader(FileInputStream(this))) else
                BufferedReader(InputStreamReader(FileInputStream(this), charsetName))
    return reader.readLines()
}

/**
 * 读取文件到字符串列表中
 * Do not use this function for huge files.
 * @param filePath
 * @param charsetName
 * @return List<String>
 */
fun readFileAsList(filePath: String, charsetName: String = ""): List<String> {
    val file = getFileByPath(filePath)
    return file?.readFileAsList(charsetName) ?: emptyList()
}

/*
  ---------- 文件操作：复制、移动、删除----------
  ---------- thanks for https://github.com/Blankj/AndroidUtilCode ----------
 */

/**
 * 复制或移动目录（默认为复制目录）
 *
 * @param destDir
 * @param isMove default false
 */
fun File.copyOrMoveDir(destDir: File, isMove: Boolean = false): Boolean {
    val srcPath = path + File.separator
    val destPath = destDir.path + File.separator
    if (destPath.contains(srcPath)) return false
    if (!exists() || !isDirectory) return false
    if (!destDir.createOrExistsDir()) return false

    val files = listFiles()
    files?.forEach {
        val destFile = File(destPath + it.name)
        if (it.isFile) {
            if (!it.copyOrMoveFile(destFile, isMove)) return false
        } else if (it.isDirectory) {
            if (!copyOrMoveDir(destFile, isMove)) return false
        }
    }

    return !isMove || deleteDir()
}

/**
 * 复制或移动目录（默认为复制目录）
 *
 * @param srcDirPath
 * @param destDirPath
 * @param isMove default false
 */
fun copyOrMoveDir(srcDirPath: String, destDirPath: String, isMove: Boolean = false): Boolean {
    val srcDirFile = getFileByPath(srcDirPath)
    val destDirFile = getFileByPath(destDirPath)
    return if (srcDirFile != null && destDirFile != null) {
        srcDirFile.copyOrMoveDir(destDirFile, isMove)
    } else {
        false
    }
}

/**
 * 复制或移动文件（默认为复制文件）
 *
 * @param destFile
 * @param isMove default false
 */
fun File.copyOrMoveFile(destFile: File, isMove: Boolean = false): Boolean {
    if (!exists() || !isFile) return false
    if (!destFile.exists() || !destFile.isFile) return false

    if (parentFile?.createOrExistsDir() != true) return false

    return try {
        destFile.writeISAsFile(FileInputStream(this)) && !(isMove && !deleteFile())
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * 复制或移动文件（默认为复制文件）
 *
 * @param srcPath
 * @param destPath
 * @param isMove default false
 */
fun copyOrMoveFile(srcPath: String, destPath: String, isMove: Boolean = false): Boolean {
    val srcFile = getFileByPath(srcPath)
    val destFile = getFileByPath(destPath)
    return if (srcFile != null && destFile != null) {
        srcFile.copyOrMoveFile(destFile, isMove)
    } else {
        false
    }
}


/**
 * 删除文件夹
 *
 * @return true  false
 *
 */
fun File.deleteDir(): Boolean {
    if (!exists()) return true
    if (!isDirectory) return false

    val files = listFiles()
    files?.forEach {
        if (it.isFile) {
            if (!it.delete()) return false
        } else if (it.isDirectory) {
            if (!deleteDir()) return false
        }
    }

    return delete()
}

/**
 * 删除文件夹
 *
 * @param dirPath
 * @return true  false
 *
 */
fun deleteDir(dirPath: String): Boolean {
    val file = getFileByPath(dirPath)
    return file?.deleteDir() ?: false
}

/**
 * 删除文件
 *
 * @return true  false
 */
fun File.deleteFile(): Boolean = !exists() || (isFile && delete())

/**
 * 删除文件
 *
 * @param filePath
 * @return true  false
 */
fun deleteFile(filePath: String): Boolean {
    val file = getFileByPath(filePath)
    return file?.deleteFile() ?: false
}