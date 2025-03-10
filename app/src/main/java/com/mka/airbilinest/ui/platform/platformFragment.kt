package com.mka.airbilinest.ui.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mka.airbilinest.R
import com.mka.airbilinest.databinding.FragmentPlatformBinding

class platformFragment : Fragment() {
    private lateinit var platformWebView: WebView
    private lateinit var platformTryAgainButton: Button
    private lateinit var platformTextError: TextView

    private var _binding: FragmentPlatformBinding? = null
    private val binding get() = _binding!!

    private val webViewTimeoutHandler = Handler(Looper.getMainLooper())
    private val webViewTimeoutRunnable = Runnable {
        platformWebView.visibility = View.GONE
        platformTryAgainButton.visibility = View.VISIBLE
        platformTextError.visibility = View.VISIBLE
        platformTextError.text = "Timeout: Gagal memuat halaman"
        Log.e("PlatformFragment", "WebView timeout")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlatformBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi view
        platformWebView = root.findViewById(R.id.platform_WV)
        platformTryAgainButton = root.findViewById(R.id.tryAgainButton_platform)
        platformTextError = root.findViewById(R.id.errorText_platform)

        // Aktifkan debugging untuk WebView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        // Atur pengaturan WebView
        platformWebView.settings.javaScriptEnabled = true
        platformWebView.settings.domStorageEnabled = true
        platformWebView.settings.databaseEnabled = true
        platformWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        // WebViewClient untuk menangani error
        platformWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                webViewTimeoutHandler.removeCallbacks(webViewTimeoutRunnable) // Hapus timeout
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                webViewTimeoutHandler.removeCallbacks(webViewTimeoutRunnable) // Hapus timeout
                platformWebView.visibility = View.GONE
                platformTryAgainButton.visibility = View.VISIBLE
                platformTextError.visibility = View.VISIBLE
                platformTextError.text = "Error: ${error.description}"
                Log.e("PlatformFragment", "WebView error: ${error.description}")
            }

            override fun onReceivedHttpError(
                view: WebView,
                request: WebResourceRequest,
                errorResponse: WebResourceResponse
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                webViewTimeoutHandler.removeCallbacks(webViewTimeoutRunnable) // Hapus timeout
                platformWebView.visibility = View.GONE
                platformTryAgainButton.visibility = View.VISIBLE
                platformTextError.visibility = View.VISIBLE
                platformTextError.text = "HTTP Error: ${errorResponse.statusCode}"
                Log.e("PlatformFragment", "HTTP error: ${errorResponse.statusCode}")
            }
        }

        // Cek koneksi internet sebelum memuat halaman
        if (isInternetAvailable(requireContext())) {
            loadWebPage()
        } else {
            Toast.makeText(requireContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
        }

        // Tombol "Coba Lagi"
        platformTryAgainButton.setOnClickListener {
            if (isInternetAvailable(requireContext())) {
                loadWebPage()
            } else {
                Toast.makeText(requireContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun loadWebPage() {
        platformWebView.visibility = View.VISIBLE
        platformTryAgainButton.visibility = View.GONE
        platformTextError.visibility = View.GONE
        platformWebView.loadUrl("https://airbilinest.com/")
        Log.d("PlatformFragment", "Loading URL: https://airbilinest.com/")

        // Set timeout 10 detik
        webViewTimeoutHandler.postDelayed(webViewTimeoutRunnable, 10000) // 10 detik
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}