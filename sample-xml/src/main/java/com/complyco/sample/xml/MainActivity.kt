package com.complyco.sample.xml

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import com.complyco.complysdk.core.api.User
import com.complyco.complysdk.networking.BuildConfig
import com.complyco.complysdk.networking.auth.models.JwtConfig
import com.complyco.complysdk.recorder.base.Comply
import com.complyco.complysdk.recorder.base.ComplyInitializationListener
import com.complyco.complysdk.recorder.base.error.RecorderError
import com.complyco.complysdk.recorder.xml.extensions.initializeView
import com.complyco.complysdk.recorder.xml.extensions.startView
import com.complyco.complysdk.recorder.xml.open.ViewComplyOptions
import com.ivangarzab.bark.Bark

class MainActivity : Activity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the WebView from the XML layout
        val webView: WebView = findViewById(R.id.formWebView)
        webView.getSettings().javaScriptEnabled = true
        webView.loadUrl("https://github.com/ivangarzab/composable-webview")

        Comply.initializeView(
            context = this,
            jwtConfig = JwtConfig.LocalGenerated(
                privateKeyPem = BuildConfig.COMPLY_KEY,
                user = User(
                    issuer = "OpenSSL",
                    audience = "app-zk58pzf3k6",
                    userId = "ivan-test-user",
                    email = "ivan@complyco.com",
                    accountApplicationId = "android-xml",
                    productId = "withdrawal",
                    institutionId = "ivan_test"
                )
            ),
            options = ViewComplyOptions.default(),
            initializationListener = object : ComplyInitializationListener {
                override fun complyDidInitialized() {
                    // Start tracking after successful initialization
                    Comply.startView(window)
                }
                override fun complyDidFailedToInitialize(error: RecorderError) {
                    Bark.e("Initialization failed: $error")
                }
            }
        )
    }
}