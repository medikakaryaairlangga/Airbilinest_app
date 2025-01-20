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

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class ControlActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView
    private lateinit var tryAgainButton: Button
    private lateinit var textError: TextView
    private lateinit var imageError: ImageView

    //private val handler = Handler(Looper.getMainLooper())
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_control)
        createNotificationChannel()

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

        // Start periodic sensor value checks
        //startSensorPolling()
        startPolling(serverUrl = "http://192.168.4.1")

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

//    private fun startSensorPolling() {
//        val runnable = object : Runnable {
//            override fun run() {
//                fetchSensorValue()
//                handler.postDelayed(this, 5000) // Poll every 5 seconds
//            }
//        }
//        handler.post(runnable)
//    }


    // Get Data
    fun fetchTemperatureData(serverUrl: String, onResult: (Double?) -> Unit) {
        Thread {
            try {
                val url = URL("$serverUrl/getData")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val jsonResponse = JSONObject(response)
                    val temperature = jsonResponse.getDouble("temperature")
                    onResult(temperature)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }.start()
    }

    fun fetchHumidityData(serverUrl: String, onResult: (Double?) -> Unit) {
        Thread {
            try {
                val url = URL("$serverUrl/getData")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val jsonResponse = JSONObject(response)
                    val humidity = jsonResponse.getDouble("humidity")
                    onResult(humidity)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }.start()
    }

    fun fetchHRData(serverUrl: String, onResult: (Double?) -> Unit) {
        Thread {
            try {
                val url = URL("$serverUrl/getData")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val jsonResponse = JSONObject(response)
                    val heartRate = jsonResponse.getDouble("heartRate")
                    onResult(heartRate)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }.start()
    }

    fun fetchSpOData(serverUrl: String, onResult: (Double?) -> Unit) {
        Thread {
            try {
                val url = URL("$serverUrl/getData")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val jsonResponse = JSONObject(response)
                    val spO2 = jsonResponse.getDouble("spO2")
                    onResult(spO2)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }.start()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Warning"
            val descriptionText = "Warning Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("temp_alerts", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    //Notification show
    private fun showNotificationTemp(temperature: Double) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "temp_alerts")
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Temperature Alert!")
            .setContentText("Current temperature: $temperature°C")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun showNotificationHumid(humidity: Double) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "temp_alerts")
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Humidity Alert!")
            .setContentText("Current humidity: $humidity%")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(2, notification)
    }

    private fun showNotificationHR(heartRate: Double) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "temp_alerts")
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Heart Rate Alert!")
            .setContentText("Current Heart Rate: $heartRate BPM")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(3, notification)
    }

    private fun showNotificationSpO(spO2: Double) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "temp_alerts")
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("SpO2 Alert!")
            .setContentText("Current SpO2: $spO2%")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(4, notification)
    }

    //Poling
    private val handler = Handler(Looper.getMainLooper())

    private fun startPolling(serverUrl: String) {
        val pollingTask = object : Runnable {
            override fun run() {
                fetchTemperatureData(serverUrl) { temperature ->
                    if (temperature != null && (temperature < 35.9 || temperature > 37.5)) {
                        runOnUiThread {
                            showNotificationTemp(temperature)
                        }
                    }
                }
                fetchHumidityData(serverUrl) { humidity ->
                    if (humidity != null && (humidity < 40 || humidity > 60)) {
                        runOnUiThread {
                            showNotificationHumid(humidity)
                        }
                    }
                }
                fetchHRData(serverUrl) { heartRate ->
                    if (heartRate != null && (heartRate < 119 || heartRate > 160)) {
                        runOnUiThread {
                            showNotificationHR(heartRate)
                        }
                    }

                    fetchSpOData(serverUrl) { spO2 ->
                        if (spO2 != null && (spO2 < 90 || spO2 > 95)) {
                            runOnUiThread {
                                showNotificationSpO(spO2)
                            }
                        }
                    }
                    handler.postDelayed(this, 5000) // Poll every 5 seconds
                }
            }
        }
        handler.post(pollingTask)
    }
}




//    private fun fetchSensorValue() {
//        thread {
//            try {
//                val request = Request.Builder()
//                    .url("http://192.168.4.1/getBuzzerState")
//                    .build()
//                client.newCall(request).execute().use { response ->
//                    if (response.isSuccessful) {
//                        try {
//                            val responseBody = response.body?.string()
//                            val buzzerValue = JSONObject(responseBody).optBoolean("buzzerStatus", false)
//                            //val buzzerValue = JSONObject(responseBody).getBoolean("buzzerStatus")
//                            if (buzzerValue) {
//                                showNotification()
//                            }
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            // Optional: Show a toast or log a detailed error message for easier debugging
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

//    private fun showNotification() {
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val channelId = "sensor_alerts"
//        val channelName = "Sensor Alerts"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val intent = Intent(this, ControlActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        val notification = NotificationCompat.Builder(this, channelId)
//            .setContentTitle("Sensor Alert")
//            .setContentText("Sensor value exceeded threshold")
//            .setSmallIcon(R.drawable.ic_alarm)
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//            .build()
//
//        notificationManager.notify(1, notification)
//    }
//}
