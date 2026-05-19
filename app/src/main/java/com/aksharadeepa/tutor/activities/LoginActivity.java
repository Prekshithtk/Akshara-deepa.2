package com.aksharadeepa.tutor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aksharadeepa.tutor.databinding.ActivityLoginBinding;
import com.aksharadeepa.tutor.repositories.StudyRepository;
import com.aksharadeepa.tutor.utils.PreferenceManager;
import com.aksharadeepa.tutor.utils.SecurityUtils;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private PreferenceManager prefs;
    private StudyRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new PreferenceManager(this);
        repository = new StudyRepository(this);
        if (prefs.isLoggedIn()) {
            openMain();
            return;
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String username = String.valueOf(binding.inputUsername.getText()).trim();
        String password = String.valueOf(binding.inputPassword.getText());
        if (username.length() < 2 || password.length() < 4) {
            Toast.makeText(this, "Enter a username and at least 4 password characters.", Toast.LENGTH_SHORT).show();
            return;
        }
        String hash = SecurityUtils.sha256(username + ":" + password);
        binding.buttonLogin.setEnabled(false);
        repository.loginOrCreate(username, hash, ok -> runOnUiThread(() -> {
            binding.buttonLogin.setEnabled(true);
            if (ok) {
                prefs.setUsername(username);
                prefs.setLoggedIn(binding.checkRemember.isChecked());
                openMain();
            } else {
                Toast.makeText(this, "Incorrect password for this offline account.", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
