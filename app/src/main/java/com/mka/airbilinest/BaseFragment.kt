package com.mka.airbilinest

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    override fun onAttach(context: android.content.Context) {
        super.onAttach(LocaleHelper.loadLocale(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper.loadLocale(requireContext()) // Apply locale when fragment is created
    }
}
