package com.practice.totalshopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.totalshopping.R
import com.example.totalshopping.databinding.ActivityInfoBinding
import com.example.totalshopping.databinding.ActivityManualBinding

class ManualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "사용 설명서"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}