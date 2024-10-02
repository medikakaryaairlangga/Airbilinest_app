package com.mka.airbilinest.ui.left

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
import com.mka.airbilinest.databinding.FragmentBilinormBinding

class LeftFragment : Fragment() {

    private lateinit var biliWebView: WebView
    private lateinit var bilitryAgainButton: Button
    private lateinit var bilitextError: TextView

    private var _binding: FragmentBilinormBinding? = null

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

        _binding = FragmentBilinormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        biliWebView = root.findViewById(R.id.bilinorm_WV)
        bilitryAgainButton = root.findViewById(R.id.tryAgainButton_bilinorm)
        bilitextError = root.findViewById(R.id.errorText_bilinorm)

        biliWebView.settings.javaScriptEnabled = true
        // Enable Web Storage (localStorage and sessionStorage)
        biliWebView.settings.domStorageEnabled = true
        // Enable database storage
        biliWebView.settings.databaseEnabled = true

        biliWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
                //error: WebViewClient.WebResourceError
            ) {
                // Hide WebView and show "Try Again" button when there's an error
                biliWebView.visibility = View.GONE
                bilitryAgainButton.visibility = View.VISIBLE
                bilitextError.visibility = View.VISIBLE
            }
        }

        fun loadWebPage() {
            biliWebView.visibility = View.VISIBLE
            bilitryAgainButton.visibility = View.GONE
            biliWebView.loadUrl("https://www.bilinorm.babyhealthsby.org/#/intro")
        }

        loadWebPage()

        bilitryAgainButton.setOnClickListener {
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