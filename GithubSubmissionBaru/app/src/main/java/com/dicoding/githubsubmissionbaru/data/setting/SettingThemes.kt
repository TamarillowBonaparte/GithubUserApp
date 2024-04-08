package com.dicoding.githubsubmissionbaru.data.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.githubsubmissionbaru.R
import com.dicoding.githubsubmissionbaru.data.viewmodel.FactoryView
import com.dicoding.githubsubmissionbaru.data.viewmodel.MainViewModell
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingThemes : AppCompatActivity() {
    private val mainViewModel : MainViewModell by viewModels { FactoryView.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_themes)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}