package com.aksharadeepa.tutor.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aksharadeepa.tutor.activities.LoginActivity;
import com.aksharadeepa.tutor.databinding.FragmentSettingsBinding;
import com.aksharadeepa.tutor.utils.PreferenceManager;
import com.aksharadeepa.tutor.utils.ReminderScheduler;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private PreferenceManager prefs;
    private final ActivityResultLauncher<String> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            granted -> {
                if (granted) ReminderScheduler.schedule(requireContext());
                binding.switchReminder.setChecked(granted);
                prefs.setReminderEnabled(granted);
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        prefs = new PreferenceManager(requireContext());
        binding.switchDarkMode.setChecked(prefs.isDarkMode());
        binding.switchReminder.setChecked(prefs.isReminderEnabled());
        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.setDarkMode(isChecked);
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });
        binding.switchReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) enableReminder();
            else {
                prefs.setReminderEnabled(false);
                ReminderScheduler.cancel(requireContext());
            }
        });
        binding.buttonAbout.setOnClickListener(v -> showAbout());
        binding.buttonHelp.setOnClickListener(v -> showHelp());
        binding.buttonLogout.setOnClickListener(v -> {
            prefs.setLoggedIn(false);
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });
        return binding.getRoot();
    }

    private void enableReminder() {
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            return;
        }
        prefs.setReminderEnabled(true);
        ReminderScheduler.schedule(requireContext());
    }

    private void showAbout() {
        new AlertDialog.Builder(requireContext())
                .setTitle("About Akshara-Deepa Tutor")
                .setMessage("Akshara-Deepa Tutor is an offline self-study companion for 10th-grade SSLC students.\n\n"
                        + "It helps students track chapter completion, revise key course content, attend chapter quizzes, and identify weak subjects using local analytics.\n\n"
                        + "All data stays on this device. No internet, account server, Firebase, or cloud API is used.")
                .setPositiveButton("OK", null)
                .show();
    }

    private void showHelp() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Help")
                .setMessage("How to use the app:\n\n"
                        + "1. Open Tracker and choose a subject.\n"
                        + "2. Tap Open to read the course notes for a chapter.\n"
                        + "3. Mark Course Complete only after you understand the notes.\n"
                        + "4. Take the chapter quiz. Wrong answers show what to revise.\n"
                        + "5. Check Strength to find weak subjects and plan revision.\n\n"
                        + "Tip: Complete at least one topic daily to maintain your streak.")
                .setPositiveButton("OK", null)
                .show();
    }
}
