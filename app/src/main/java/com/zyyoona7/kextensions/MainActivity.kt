package com.zyyoona7.kextensions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zyyoona7.lib.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logable(true)
        logGlobalTag("GlobalTag")
        logv("Hello")
        logd("Hello", customTag = "customTag")
        logi("Hello")
        logw("Hello")
        loge("Hello")
        val json = """         {'name':'zyyoona7','age':'20'}   """
        logJson(json)
        getVersionName("123")
    }
}
