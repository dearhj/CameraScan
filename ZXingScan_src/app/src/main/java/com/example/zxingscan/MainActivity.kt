package com.example.zxingscan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.utils.CreateQRCode
import com.example.utils.Decode
import com.google.zxing.BarcodeFormat
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions


class MainActivity : AppCompatActivity() {
//    private val requestCodeScan = 9

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ss = resources.getStringArray(R.array.Barcode)
        var code: BarcodeFormat? = null
        XXPermissions.with(this).permission(Permission.CAMERA)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, all: Boolean) {}
                override fun onDenied(permissions: MutableList<String>, never: Boolean) {}
            })
        val imageView = findViewById<ImageView>(R.id.imageView)
        val editText = findViewById<EditText>(R.id.editText)
        val text = findViewById<TextView>(R.id.sp)
        findViewById<Button>(R.id.button0).setOnClickListener {
            if (code != null) {
                Log.d("TAG", "onCreate:   开始创建 $code")
                val bitMap = CreateQRCode.createQRCode(editText.text.toString(), code, this)
                imageView.setImageBitmap(bitMap)
            }
        }
        findViewById<Button>(R.id.button1).setOnClickListener {
//            val intent = Intent(this@MainActivity, CaptureActivity::class.java)
//            startActivityForResult(intent, requestCodeScan)
            Decode().myDecode(this, object: Decode.Listen{
                override fun getResult(data: String) {
                    Log.d("logg1", data)
                }
            })
//            Log.d("logg2", "result")
        }
        val adapter2: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ss)
        val spinner2 = findViewById<Spinner>(R.id.spinner)
        spinner2.adapter = adapter2
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                text.text = ss[position]
                code = BarcodeFormat.valueOf(ss[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == requestCodeScan && resultCode == Activity.RESULT_OK) {
//            if (data != null) {
//                val content = data.getStringExtra(KEY_DATA)
//                findViewById<TextView>(R.id.textView).text = content
//            }
//        }
//    }
}