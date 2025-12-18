package com.complyco.sample.xml

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import com.complyco.recorder.Engine
import com.complyco.recorder.InitializationListener
import com.complyco.recorder.error.RecorderError
import com.complyco.recorder.xml.extensions.initializeView
import com.complyco.recorder.xml.extensions.startView
import com.complyco.recorder.xml.open.ViewOptions
import com.ivangarzab.bark.Bark

class MainActivity : Activity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the WebView from the XML layout
        val webView: WebView = findViewById(R.id.formWebView)
        webView.getSettings().javaScriptEnabled = true
        webView.loadUrl("https://your-bank.example.com/")

        val jwtProducer = { "example-token" }

        Engine.initializeView(
            context = this,
            options = ViewOptions.default(),
            jwtProducer = jwtProducer,
            initializationListener = object : InitializationListener {
                override fun didInitialized() {
                    // Start tracking after successful initialization
                    Engine.startView(window)
                }
                override fun didFailedToInitialize(error: RecorderError) {
                    Bark.e("Initialization failed: $error")
                }
            }
        )
    }
}