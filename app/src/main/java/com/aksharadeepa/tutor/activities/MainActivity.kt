package com.aksharadeepa.tutor.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aksharadeepa.tutor.R
import com.aksharadeepa.tutor.databinding.ActivityMainBinding
import com.aksharadeepa.tutor.fragments.AnalyticsFragment
import com.aksharadeepa.tutor.fragments.DashboardFragment
import com.aksharadeepa.tutor.fragments.SettingsFragment
import com.aksharadeepa.tutor.fragments.TrackerFragment
import com.aksharadeepa.tutor.utils.PreferenceManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = PreferenceManager(this)
        setContentView(binding.root)
        
        binding.buttonTopLogout.setOnClickListener { logout() }
        
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> show(DashboardFragment(), "Home")
                R.id.nav_tracker -> show(TrackerFragment(), "Tracker")
                R.id.nav_analytics -> show(AnalyticsFragment(), "Strength")
                else -> show(SettingsFragment(), "Settings")
            }
            true
        }
        
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_dashboard
        }
    }

    private fun show(fragment: Fragment, title: String) {
        binding.textPageTitle.text = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun logout() {
        prefs.isLoggedIn = false
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
