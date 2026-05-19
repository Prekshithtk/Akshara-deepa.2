package com.aksharadeepa.tutor.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aksharadeepa.tutor.activities.LoginActivity
import com.aksharadeepa.tutor.databinding.FragmentSettingsBinding
import com.aksharadeepa.tutor.utils.PreferenceManager
import com.aksharadeepa.tutor.utils.ReminderScheduler

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: PreferenceManager

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) ReminderScheduler.schedule(requireContext())
        binding.switchReminder.isChecked = granted
        prefs.isReminderEnabled = granted
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        prefs = PreferenceManager(requireContext())
        
        binding.switchDarkMode.isChecked = prefs.isDarkMode
        binding.switchReminder.isChecked = prefs.isReminderEnabled
        
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.isDarkMode = isChecked
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) enableReminder()
            else {
                prefs.isReminderEnabled = false
                ReminderScheduler.cancel(requireContext())
            }
        }
        
        binding.buttonAbout.setOnClickListener { showAbout() }
        binding.buttonHelp.setOnClickListener { showHelp() }
        binding.buttonLogout.setOnClickListener {
            prefs.isLoggedIn = false
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        
        return binding.root
    }

    private fun enableReminder() {
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            return
        }
        prefs.isReminderEnabled = true
        ReminderScheduler.schedule(requireContext())
    }

    private fun showAbout() {
        AlertDialog.Builder(requireContext())
            .setTitle("About Akshara-Deepa Tutor")
            .setMessage("Akshara-Deepa Tutor is an offline self-study companion for 10th-grade SSLC students.\n\n" +
                    "It helps students track chapter completion, revise key course content, attend chapter quizzes, and identify weak subjects using local analytics.\n\n" +
                    "All data stays on this device. No internet, account server, Firebase, or cloud API is used.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showHelp() {
        AlertDialog.Builder(requireContext())
            .setTitle("Help")
            .setMessage("How to use the app:\n\n" +
                    "1. Open Tracker and choose a subject.\n" +
                    "2. Tap Open to read the course notes for a chapter.\n" +
                    "3. Mark Course Complete only after you understand the notes.\n" +
                    "4. Take the chapter quiz. Wrong answers show what to revise.\n" +
                    "5. Check Strength to find weak subjects and plan revision.\n\n" +
                    "Tip: Complete at least one topic daily to maintain your streak.")
            .setPositiveButton("OK", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
