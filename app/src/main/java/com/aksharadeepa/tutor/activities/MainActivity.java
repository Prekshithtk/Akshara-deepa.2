package com.aksharadeepa.tutor.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aksharadeepa.tutor.R;
import com.aksharadeepa.tutor.databinding.ActivityMainBinding;
import com.aksharadeepa.tutor.fragments.AnalyticsFragment;
import com.aksharadeepa.tutor.fragments.DashboardFragment;
import com.aksharadeepa.tutor.fragments.SettingsFragment;
import com.aksharadeepa.tutor.fragments.TrackerFragment;
import com.aksharadeepa.tutor.utils.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PreferenceManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        prefs = new PreferenceManager(this);
        setContentView(binding.getRoot());
        binding.buttonTopLogout.setOnClickListener(v -> logout());
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) show(new DashboardFragment(), "Home");
            else if (id == R.id.nav_tracker) show(new TrackerFragment(), "Tracker");
            else if (id == R.id.nav_analytics) show(new AnalyticsFragment(), "Strength");
            else show(new SettingsFragment(), "Settings");
            return true;
        });
        if (savedInstanceState == null) {
            binding.bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
        }
    }

    private void show(Fragment fragment, String title) {
        binding.textPageTitle.setText(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void logout() {
        prefs.setLoggedIn(false);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
