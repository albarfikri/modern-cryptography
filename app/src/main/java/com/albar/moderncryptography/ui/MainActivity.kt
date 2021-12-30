package com.albar.moderncryptography.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
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
        statusBarColor()
        startingAnimation()
        buttonClicked()
    }

    private fun buttonClicked() {

        binding.btnAsymmetric.setOnClickListener{
            Intent(applicationContext, AsymmetricCryptographyActivity::class.java).also{
                startActivity(it)
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            }
        }

        binding.btnSymmetric.setOnClickListener{
            Intent(applicationContext, SymmetricCryptographyActivity::class.java).also{
                startActivity(it)
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
            }
        }
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

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }
}