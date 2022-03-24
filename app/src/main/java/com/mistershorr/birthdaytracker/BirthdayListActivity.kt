package com.mistershorr.birthdaytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mistershorr.birthdaytracker.databinding.ActivityBirthdayListBinding

class BirthdayListActivity : AppCompatActivity() {

    lateinit var binding : ActivityBirthdayListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthdayListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        blah()

    }

    private fun blah() {

    }

}