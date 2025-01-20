package com.mka.airbilinest

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {

    private const val LANGUAGE_KEY = "My_Lang"

    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // Update the shared preferences with the new language
        val prefs: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().putString(LANGUAGE_KEY, languageCode).apply()

        return context.createConfigurationContext(config)
    }

    fun loadLocale(context: Context): Context {
        val prefs: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val languageCode = prefs.getString(LANGUAGE_KEY, "en") ?: "en"
        return setLocale(context, languageCode)
    }
}
