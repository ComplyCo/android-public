package com.complyco.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.complyco.complysdk.core.api.User
import com.complyco.complysdk.networking.BuildConfig
import com.complyco.complysdk.networking.auth.models.JwtConfig
import com.complyco.complysdk.recorder.base.Comply
import com.complyco.complysdk.recorder.base.ComplyInitializationListener
import com.complyco.complysdk.recorder.base.error.RecorderError
import com.complyco.complysdk.recorder.compose.extensions.ComponentType
import com.complyco.complysdk.recorder.compose.extensions.complianceTrack
import com.complyco.complysdk.recorder.compose.extensions.initializeCompose
import com.complyco.complysdk.recorder.compose.extensions.startCompose
import com.complyco.complysdk.recorder.compose.options.ComposeComplyOptions
import com.complyco.sample.compose.ui.theme.ComplySampleTheme
import com.ivangarzab.bark.Bark
import com.ivangarzab.webview.data.rememberWebViewState
import com.ivangarzab.webview.ui.WebView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComplySampleTheme {
                Scaffold {
                    FormScreen(modifier = Modifier.padding(it))
                }
            }
        }

        // Initialize the Compose compliance engine using the unified Comply entry point
        Comply.initializeCompose(
            context = this,
            jwtConfig = JwtConfig.LocalGenerated(
                privateKeyPem = BuildConfig.COMPLY_KEY,
                user = User(
                    issuer = "OpenSSL",
                    audience = "app-zk58pzf3k6",
                    userId = "ivan-test-user",
                    email = "ivan@complyco.com",
                    accountApplicationId = "android-jetpack-compose",
                    productId = "deposit",
                    institutionId = "ivan_test"
                )
            ),
            options = ComposeComplyOptions.default(),
            initializationListener = object : ComplyInitializationListener {
                override fun complyDidInitialized() {
                    Bark.d("Engine initialized successfully")
                    // Start tracking after initialization
                    Comply.startCompose(Any())
                }

                override fun complyDidFailedToInitialize(error: RecorderError) {
                    Bark.e("Engine failed to initialize: $error")
                }
            }
        )
    }
}

@Composable
fun FormScreen(
    modifier: Modifier = Modifier
) {
    var nameValue by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    val webState = rememberWebViewState("https://github.com/ivangarzab/composable-webview")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.background)
            .complianceTrack(
                label = "FormScreen",
                type = ComponentType.CONTENT,
                backgroundColor = MaterialTheme.colorScheme.background
            )
    ) {
        Text(
            text = "Form Signature",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .complianceTrack(
                    label = "FormHeaderText",
                    type = ComponentType.CONTENT,
                    value = "Form Signature",
                    textStyle = MaterialTheme.typography.headlineLarge
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        WebView(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
                .complianceTrack(
                    label = "FormWebView",
                    type = ComponentType.WEBVIEW,
                    url = webState.lastLoadedUrl
                ),
            state = webState
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nameValue,
            onValueChange = { nameValue = it },
            label = { Text("Full Name") },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack(
                    label = "NameTextField",
                    type = ComponentType.INPUT,
                    value = nameValue,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Switch(
                modifier = Modifier
                    .weight(1f)
                    .complianceTrack(
                        label = "SwitchField",
                        type = ComponentType.SWITCH,
                        isOn = isChecked
                    ),
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(
                modifier = Modifier
                    .weight(4f)
                    .align(Alignment.CenterVertically)
                    .complianceTrack(
                        label = "SwitchText",
                        type = ComponentType.CONTENT,
                        value = "I consent to the terms and conditions",
                        textStyle = MaterialTheme.typography.bodyMedium
                    ),
                text = "I consent to the terms and conditions",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = { /* No-op */ },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack(
                    label = "SubmitButton",
                    type = ComponentType.BUTTON,
                    value = "Submit",
                    textStyle = MaterialTheme.typography.labelLarge,
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* No-op */ },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack(
                    label = "CancelButton",
                    type = ComponentType.BUTTON,
                    value = "Cancel",
                    textStyle = MaterialTheme.typography.labelLarge,
                    backgroundColor = MaterialTheme.colorScheme.background
                )
        ) {
            Text(
                text = "Cancel",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun FormScreenPreview() {
    FormScreen()
}