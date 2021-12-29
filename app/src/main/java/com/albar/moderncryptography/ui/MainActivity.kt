package com.albar.moderncryptography.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.albar.moderncryptography.R
import com.albar.moderncryptography.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    private fun startingAnimation() {
        val animationFromTop = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val animationFromLeft =
            AnimationUtils.loadAnimation(this, R.anim.enter_from_right_type_selection)
        val animationFromRight =
            AnimationUtils.loadAnimation(this, R.anim.enter_from_left_type_selection)

        with(binding) {
            imageView.animation = animationFromTop
            card1.animation = animationFromRight
            card2.animation = animationFromLeft
        }
    }
}