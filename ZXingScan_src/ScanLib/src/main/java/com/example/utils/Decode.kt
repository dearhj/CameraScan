package com.example.utils

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.skateboard.zxinglib.CaptureActivity

class Decode : AppCompatActivity() {
    private var result = ""
    fun myDecode(activity: FragmentActivity, callback: Listen){
        ActivityLauncher.init(activity).startActivityForResult(
            CaptureActivity::class.java
        ) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK && data != null) {
                result = data.getStringExtra(CaptureActivity.KEY_DATA).toString()
                callback.getResult(result)
            }
            Log.d("logg",    result)
        }
    }

    interface Listen{
        fun getResult(data: String)
    }
}