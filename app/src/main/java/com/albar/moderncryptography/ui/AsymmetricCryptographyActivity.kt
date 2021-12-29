package com.albar.moderncryptography.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albar.moderncryptography.R
import com.albar.moderncryptography.databinding.ActivityAsymmetricCryptographyBinding
import com.albar.moderncryptography.databinding.ActivitySymmetricCryptographyBinding

class AsymmetricCryptographyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsymmetricCryptographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsymmetricCryptographyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}