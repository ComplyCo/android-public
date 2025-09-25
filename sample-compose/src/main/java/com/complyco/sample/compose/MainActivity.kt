package com.complyco.sample.compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.complyco.complysdk.core.api.User
import com.complyco.complysdk.networking.BuildConfig
import com.complyco.complysdk.networking.auth.models.JwtConfig
import com.complyco.complysdk.recorder.base.Comply
import com.complyco.complysdk.recorder.base.ComplyInitializationListener
import com.complyco.complysdk.recorder.base.error.RecorderError
import com.complyco.complysdk.recorder.compose.extensions.initializeCompose
import com.complyco.complysdk.recorder.compose.extensions.startCompose
import com.complyco.complysdk.recorder.compose.options.ComposeComplyOptions
import com.complyco.sample.compose.components.DemoUser
import com.complyco.sample.compose.screens.Step1Screen
import com.complyco.sample.compose.screens.Step2Screen
import com.complyco.sample.compose.screens.Step3Screen
import com.complyco.sample.compose.ui.theme.ComplySampleTheme
import com.ivangarzab.bark.Bark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComplySampleTheme {
                Scaffold {
                    OnboardingNavigation(
                        modifier = Modifier.padding(it)
                    )
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
fun OnboardingNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // Mock user data - replace with your actual user data source
    val mockUser = DemoUser(
        email = "ivan@complyco.com",
        username = "ivan-test-user"
    )

    NavHost(
        navController = navController,
        startDestination = "step1",
        modifier = modifier
    ) {
        composable("step1") {
            val mockData = remember { mutableStateOf(DemoUser.EMPTY) }
            val mockPassword = remember { mutableStateOf("") }
            Step1Screen(
                modifier = Modifier.fillMaxSize(),
                user = mockData.value,
                password = mockPassword.value,
                onSetData = {
                    mockData.value = mockUser
                    mockPassword.value = "123456"
                },
                onNavigateToStep2 = {
                    navController.navigate("step2")
                }
            )
        }

        composable("step2") {
            val mockData = remember { mutableStateOf<List<String>>(emptyList()) }
            Step2Screen(
                modifier = Modifier.fillMaxSize(),
                data = mockData.value,
                onSetData = { mockData.value = listOf("3", "4", "1", "6", "5", "9") },
                onNavigateToStep3 = {
                    navController.navigate("step3")
                }
            )
        }

        composable("step3") {
            val context = LocalContext.current
            Step3Screen(
                modifier = Modifier.fillMaxSize(),
                user = mockUser,
                onApplicationComplete = {
                    Toast.makeText(
                        context,
                        "Flow Completed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}