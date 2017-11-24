package com.example.terry.ssumap_android

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        withDelay(2000) {
            functionX()
        }
    }

    // Temporary Functions
    fun functionX() {
        "After 2000 MilliSeconds".toast(this)
    }

    // Extension Functions
    fun withDelay(delay : Long, block : () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }

    // Extension Function for show Toast to Device
    fun Any.toast(context: Context) {
        Toast.makeText(context, this.toString(), Toast.LENGTH_LONG).show()
    }
}
