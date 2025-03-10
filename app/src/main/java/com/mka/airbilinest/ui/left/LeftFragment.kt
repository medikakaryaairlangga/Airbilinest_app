package com.mka.airbilinest.ui.left

import android.app.Dialog
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
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

        _binding = FragmentBilinormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        biliWebView = root.findViewById(R.id.bilinorm_WV)
        bilitryAgainButton = root.findViewById(R.id.tryAgainButton_bilinorm)
        bilitextError = root.findViewById(R.id.errorText_bilinorm)

        // Enable JavaScript and other settings
        biliWebView.settings.javaScriptEnabled = true
        biliWebView.settings.domStorageEnabled = true
        biliWebView.settings.databaseEnabled = true
        biliWebView.settings.setSupportMultipleWindows(true) // Enable multiple windows


        biliWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                // Hide WebView and show "Try Again" button when there's an error
                biliWebView.visibility = View.GONE
                bilitryAgainButton.visibility = View.VISIBLE
                bilitextError.visibility = View.VISIBLE
            }
        }

        // Handle popups using WebChromeClient
        biliWebView.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                val newWebView = WebView(requireContext())
                newWebView.settings.javaScriptEnabled = true
                newWebView.settings.domStorageEnabled = true
                newWebView.webChromeClient = this

                // Show the new WebView in a dialog
                val dialog = Dialog(requireContext())
                dialog.setContentView(newWebView)
                dialog.setOnDismissListener {
                    newWebView.destroy()
                }
                dialog.show()

                // Send the WebView to the transport
                val transport = resultMsg?.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }

        fun loadWebPage() {
            biliWebView.visibility = View.VISIBLE
            bilitryAgainButton.visibility = View.GONE
            bilitextError.visibility = View.GONE
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