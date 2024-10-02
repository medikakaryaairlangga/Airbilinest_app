package com.mka.airbilinest

import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class ControlActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView
    private lateinit var tryAgainButton: Button
    private lateinit var textError: TextView
    private lateinit var imageError: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_control)

        myWebView = findViewById(R.id.myWV)
        tryAgainButton = findViewById(R.id.tryAgainButton)
        textError = findViewById(R.id.errorText)
        imageError = findViewById(R.id.errorImage)

        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
                //error: WebViewClient.WebResourceError
            ) {
                // Hide WebView and show "Try Again" button when there's an error
                myWebView.visibility = View.GONE
                tryAgainButton.visibility = View.VISIBLE
                textError.visibility = View.VISIBLE
                imageError.visibility = View.VISIBLE

            }
        }

        loadWebPage()

        tryAgainButton.setOnClickListener {
            // Retry loading the webpage
            loadWebPage()
        }
    }

    private fun loadWebPage() {
        myWebView.visibility = View.VISIBLE
        tryAgainButton.visibility = View.GONE
        textError.visibility = View.GONE
        imageError.visibility = View.GONE
        myWebView.loadUrl("http://192.168.4.1/")
    }
}