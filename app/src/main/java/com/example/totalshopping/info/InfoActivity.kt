package com.example.totalshopping.info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.totalshopping.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        findViewById<Button>(R.id.btn_opensource_license)?.let {
            it.setOnClickListener {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
              }
            }
        }
}