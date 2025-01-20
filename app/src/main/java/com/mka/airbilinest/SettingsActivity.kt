package com.mka.airbilinest

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val languageRadioGroup = findViewById<RadioGroup>(R.id.languageRadioGroup)
        val englishRadioButton = findViewById<RadioButton>(R.id.englishRadioButton)
        val indonesianRadioButton = findViewById<RadioButton>(R.id.indonesianRadioButton)

        // Set the checked radio button based on the saved language
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        val savedLanguage = prefs.getString("My_Lang", "en")
        if (savedLanguage == "en") englishRadioButton.isChecked = true else indonesianRadioButton.isChecked = true

        languageRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedLanguage = if (checkedId == R.id.englishRadioButton) "en" else "in"

            // Update the language and restart the activity
            LocaleHelper.setLocale(this, selectedLanguage)
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
