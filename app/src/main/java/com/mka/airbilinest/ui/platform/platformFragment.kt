package com.mka.airbilinest.ui.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mka.airbilinest.R
import com.mka.airbilinest.databinding.FragmentPlatformBinding
import com.mka.airbilinest.ui.left.LeftViewModel

class platformFragment : Fragment(){
    private lateinit var platformWebView: WebView
    private lateinit var platformtryAgainButton: Button
    private lateinit var platformtextError: TextView

    private var _binding: FragmentPlatformBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(LeftViewModel::class.java)

        _binding = FragmentPlatformBinding.inflate(inflater, container, false)
        val root: View = binding.root

        platformWebView = root.findViewById(R.id.platform_WV)
        platformtryAgainButton = root.findViewById(R.id.tryAgainButton_platform)
        platformtextError = root.findViewById(R.id.errorText_platform)

        platformWebView.settings.javaScriptEnabled = true
        // Enable Web Storage (localStorage and sessionStorage)
        platformWebView.settings.domStorageEnabled = true
        // Enable database storage
        platformWebView.settings.databaseEnabled = true

        platformWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
                //error: WebViewClient.WebResourceError
            ) {
                // Hide WebView and show "Try Again" button when there's an error
                platformWebView.visibility = View.GONE
                platformtryAgainButton.visibility = View.VISIBLE
                platformtextError.visibility = View.VISIBLE
            }
        }

        fun loadWebPage() {
            platformWebView.visibility = View.VISIBLE
            platformtryAgainButton.visibility = View.GONE
            platformWebView.loadUrl("https://medikakaryaairlangga.github.io/")
        }

        loadWebPage()

        platformtryAgainButton.setOnClickListener {
            // Retry loading the webpage
            loadWebPage()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}