package com.zyyoona7.kextensions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zyyoona7.lib.screenWidth
import com.zyyoona7.lib.snackbar

class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e(tag,"screenWidth=$screenWidth")
    }
}
