package com.albar.moderncryptography.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.albar.moderncryptography.R
import com.albar.moderncryptography.databinding.ActivitySymmetricCryptographyBinding
import com.google.android.material.snackbar.Snackbar
import com.scottyab.aescrypt.AESCrypt
import java.io.IOException
import java.security.GeneralSecurityException


class SymmetricCryptographyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySymmetricCryptographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySymmetricCryptographyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Symmetric with AES"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.encrypt.setOnClickListener {
            var isEmptyFields = false
            if (binding.key.text?.isEmpty() == true) {
                isEmptyFields = true
                binding.key.error = getString(R.string.error)
            }

            if (binding.message.text?.isEmpty() == true) {
                isEmptyFields = true
                binding.message.error = getString(R.string.error)
            }

            if (!isEmptyFields) {
                encrypt()
            }
        }

        binding.decrypt.setOnClickListener {
            decrypt()
        }

        changeRadioButtonColor("encryption")
        statusBarColor()
        radioIsSelected()
    }

    private fun radioIsSelected() {
        binding.radioNumber.setOnCheckedChangeListener { _, checkId ->
            when (true) {
                checkId == R.id.encryption_selection -> {
                    Snackbar.make(
                        binding.encryptionSelection,
                        R.string.encrypt_mode,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    binding.encrypt.isEnabled = true
                    binding.decrypt.isEnabled = false
                    changeRadioButtonColor("encryption")


                }
                checkId == R.id.decryption_selection -> {
                    Snackbar.make(
                        binding.decryptionSelection,
                        R.string.decrypt_mode,
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    binding.decrypt.isEnabled = true
                    binding.encrypt.isEnabled = false
                    changeRadioButtonColor("decryption")

                }
                else -> {}
            }
        }
    }

    private fun changeRadioButtonColor(button: String) {
        if (button == "encryption") {
            binding.decrypt.setBackgroundColor(getColor(R.color.disabled))
            binding.encrypt.setBackgroundColor(getColor(R.color.color_btn))
        } else if (button == "decryption") {
            binding.encrypt.setBackgroundColor(getColor(R.color.disabled))
            binding.decrypt.setBackgroundColor(getColor(R.color.blue_700))
        }
    }

    private fun encrypt() {
        val encrypted =
            AESCrypt.encrypt(binding.key.text.toString(), binding.message.text.toString())
        binding.result.setText(encrypted.toString())
    }

    private fun decrypt() {
        try {
            val decrypted =
                AESCrypt.decrypt(binding.key.text.toString(), binding.message.text.toString())
            binding.result.setText(decrypted.toString())
        } catch (e: GeneralSecurityException) {
            Toast.makeText(applicationContext, "Wrong key input", Toast.LENGTH_SHORT).show()
        }

    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View) {
        if (v.id == R.id.encrypt) {
            if (binding.radioNumber.checkedRadioButtonId > 0) {
                when (binding.radioNumber.checkedRadioButtonId) {
                    R.id.encrypt -> {
                        binding.encrypt.isEnabled = true
                        binding.encrypt.isEnabled = false
                    }
                    R.id.decrypt -> {
                        binding.decrypt.isEnabled = true
                        binding.decrypt.isEnabled = false
                    }
                }
            }
        }
    }
}