package com.aksharadeepa.tutor.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aksharadeepa.tutor.databinding.ActivityLoginBinding
import com.aksharadeepa.tutor.repositories.StudyRepository
import com.aksharadeepa.tutor.utils.PreferenceManager
import com.aksharadeepa.tutor.utils.SecurityUtils
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: PreferenceManager
    private lateinit var repository: StudyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager(this)
        repository = StudyRepository(this)
        
        if (prefs.isLoggedIn) {
            openMain()
            return
        }
        
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonLogin.setOnClickListener { login() }
    }

    private fun login() {
        val username = binding.inputUsername.text.toString().trim()
        val password = binding.inputPassword.text.toString()
        
        if (username.length < 2 || password.length < 4) {
            Toast.makeText(this, "Enter a username and at least 4 password characters.", Toast.LENGTH_SHORT).show()
            return
        }
        
        val hash = SecurityUtils.sha256("$username:$password")
        binding.buttonLogin.isEnabled = false
        
        lifecycleScope.launch {
            val ok = repository.loginOrCreate(username, hash)
            binding.buttonLogin.isEnabled = true
            if (ok) {
                prefs.username = username
                prefs.isLoggedIn = binding.checkRemember.isChecked
                openMain()
            } else {
                Toast.makeText(this@LoginActivity, "Incorrect password for this offline account.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
