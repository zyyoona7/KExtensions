package com.zyyoona7.kextensions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zyyoona7.lib.*
import com.zyyoona7.lib.log.ZLog

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        logGlobalTag("GlobalTag")
        logv("Hello")
        logd("Hello")
        logi("Hello")
        logw("Hello")
        loge("Hello")
        val json = """         {'name':'zyyoona7','age':'20'}   """
        logJson(json)
        ZLog.d("你好啊")
    }
}
