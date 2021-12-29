package com.albar.moderncryptography.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.albar.moderncryptography.databinding.ActivitySymmetricCryptographyBinding


class SymmetricCryptographyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySymmetricCryptographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymmetricCryptographyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}