package com.albar.moderncryptography.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.albar.moderncryptography.R
import com.albar.moderncryptography.databinding.ActivityAsymmetricCryptographyBinding
import com.albar.moderncryptography.util.RsaKey
import com.google.android.material.snackbar.Snackbar
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher

class AsymmetricCryptographyActivity : AppCompatActivity() {
    private val encryptDecrypt = RsaKey()
    private lateinit var binding: ActivityAsymmetricCryptographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsymmetricCryptographyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar(R.id.encryption_selection)

        binding.encrypt.setOnClickListener {
            var isEmptyFields = false
            if (binding.plaintext.text?.isEmpty() == true) {
                isEmptyFields = true
                binding.plaintext.error = getString(R.string.error)
            }

            if (binding.plaintext.text?.isEmpty() == true) {
                isEmptyFields = true
                binding.plaintext.error = getString(R.string.error)
            }

            if (!isEmptyFields) {
                closeKeyboard(binding.encrypt)
                binding.chipertext.setText(encrypt(binding.plaintext.text.toString()))
            }

        }

        binding.decrypt.setOnClickListener {
            var isEmptyFields = false


            if (!isEmptyFields) {
                closeKeyboard(binding.decrypt)

                binding.chipertext.setText(decrypt())
            }
        }

        changeRadioButtonColor("encryption")
        statusBarColor()
        radioIsSelected()
    }


    private fun radioIsSelected() {
        binding.radioNumber.setOnCheckedChangeListener { _, checkId ->
            when (true) {
                checkId == R.id.encryption_selection -> {
                    supportActionBar(R.id.encryption_selection)
                    reverseEditText("encryption")
                    closeKeyboard(binding.decryptionSelection)
                    clearFields()
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
                    supportActionBar(R.id.decryption_selection)
                    reverseEditText("decryption")
                    closeKeyboard(binding.encryptionSelection)
                    clearFields()
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

    private fun encrypt(message: String): String {
        val encryptCipher: Cipher = Cipher.getInstance("RSA")
        encryptCipher.init(Cipher.ENCRYPT_MODE, encryptDecrypt.publicKey)
        val charsets = Charsets.UTF_8
        val secretMessageBytes = message.toByteArray(charsets)
        val encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes)
        encryptDecrypt.byteForMoment = encryptedMessageBytes
        return Base64.getEncoder().encodeToString(encryptedMessageBytes)
    }

    private fun decrypt(): String {
        val decryptCipher = Cipher.getInstance("RSA")
        decryptCipher.init(Cipher.DECRYPT_MODE, encryptDecrypt.privateKey)
        val decryptedMessageBytes = decryptCipher.doFinal(encryptDecrypt.byteForMoment)
        return String(decryptedMessageBytes, StandardCharsets.UTF_8)
    }

    private fun clearFields() {
        binding.chipertext.text?.clear()
        binding.plaintext.text?.clear()
        binding.plaintext.clearFocus()
        binding.chipertext.clearFocus()
    }

    private fun reverseEditText(command: String) {
        if (command == "encryption") {
            binding.tfPlaintext.setStartIconDrawable(R.drawable.ic_plaintext)
            binding.tfPlaintext.setHint(R.string.plaintext)
            binding.tfChipertext.setStartIconDrawable(R.drawable.ic_key)
            binding.tfChipertext.setHint(R.string.chipertext)
        }
        if (command == "decryption") {
            binding.tfPlaintext.setStartIconDrawable(R.drawable.ic_key)
            binding.tfPlaintext.setHint(R.string.chipertext)
            binding.tfChipertext.setStartIconDrawable(R.drawable.ic_plaintext)
            binding.tfChipertext.setHint(R.string.plaintext)
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

    private fun supportActionBar(v: Int) {
        supportActionBar?.apply {
            if (v == R.id.encryption_selection) {
                title = "Encryption with RSA"
            } else if (v == R.id.decryption_selection) {
                title = "Decryption with RSA"
            }
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
        super.onBackPressed()
    }

    private fun statusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.color_btn)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun onClick(v: View) {
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

    private fun closeKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        binding.plaintext.clearFocus()
        binding.plaintext.clearFocus()
    }
}