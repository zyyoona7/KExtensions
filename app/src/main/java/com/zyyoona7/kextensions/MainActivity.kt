package com.zyyoona7.kextensions

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.zyyoona7.lib.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    lateinit var btnHello:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logEnabled(true)
//        logGlobalTag("GlobalTag")
//        logv("Hello")
//        logd("Hello", customTag = "customTag")
//        logi("Hello")
//        logw("Hello")
//        loge("Hello")
//        val json = """         {'name':'zyyoona7','age':'20'}   """
//        logJson(json)
//
//        loge(fileDirPath)
//        loge(cacheDirPath)
//        loge(externalFileDirPath)
//        loge(externalCacheDirPath)
//
//        loge("dir exist ${isDirExists(externalFileDirPath)}")
//
//        loge("dir exist ${isDirExists(externalFileDirPath + "/a")}")
//
//        loge(getDirSize(publicDownloadDir))
//        loge(getDirSize(publicDCIMDir))
        loge(getFileByPath(publicPictureDir)?.dirSize ?: " ")
        loge(getFileByPath(publicPictureDir)?.dirSize ?: " ")
        loge(getFileByPath(publicPictureDir)?.dirSize ?: " ")
//        loge(getDirSize(publicMusicDir))
//        loge(getDirSize(publicMovieDir))
//
//        loge("path=$download \nDir=${getDirName(download)}")
//        loge("path=$download \nFile=${getFileName(download)}")
//

//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {

//        } else {
//            writeFile()
//        }

        val cal = Calendar.getInstance()
//        cal.add(Calendar.HOUR_OF_DAY, -25)
//        loge(cal.timeInMillis.formatAgoStyleForWeChat())
//        loge(cal.timeInMillis.formatAgoStyleForWeibo())
        cal.add(Calendar.DATE, 2)
        loge("day number of week = ${cal.timeInMillis.dayOfWeek}")

        btnHello=findViewById(R.id.btn_hello) as Button
        btnHello.setOnClickListener{
            requestPermission(arrayOf(Manifest.permission.CAMERA)
                    , 11223)
        }
    }

    private fun writeFile() {
        val download = publicDownloadDir + "/a.txt"

        val content = """a
            |a
            |a
            |a
            |s
            |d
            |d
            |f
        """.trimMargin()
        writeStringAsFile(download, content)

        loge(readFileAsString(download))
        val destFilePath = publicPictureDir + "/b.txt"
        createOrExistsFile(destFilePath)
        loge("copy finished ${copyOrMoveFile(download, destFilePath, true)}")
//        installApp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11223) {
//            writeFile()
            handlePermissionResult(permissions, grantResults,{
                loge("permissions granted ${isPermissionGranted(Manifest.permission.CAMERA)}")
                takePhotoNoCompress()
            },{
                loge("permissions denied ${arePermissionAlwaysDenied(*permissions)}")
            })
        }
    }

    private fun takePhotoNoCompress() {

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val filename = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(Date()) + ".png"
            val file = File(getExternalStorageDirectory(), filename)
//            mCurrentPhotoPath = file.absolutePath

            val fileUri = getUriFromFile( file)

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            startActivityForResult(takePictureIntent, 12345)
        }
    }

    private fun installApp() {
        val file = getFileByPath("$publicDownloadDir/app-debug.apk")
        file?.let {
            installApp(file, "a", false)
        }
    }
}
