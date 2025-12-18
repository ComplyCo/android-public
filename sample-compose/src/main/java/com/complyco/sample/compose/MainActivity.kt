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
import com.complyco.recorder.Engine
import com.complyco.recorder.InitializationListener
import com.complyco.recorder.compose.extensions.initializeCompose
import com.complyco.recorder.compose.extensions.startCompose
import com.complyco.recorder.compose.options.ComposeOptions
import com.complyco.recorder.error.RecorderError
import com.complyco.sample.compose.components.DemoUser
import com.complyco.sample.compose.screens.Step1Screen
import com.complyco.sample.compose.screens.Step2Screen
import com.complyco.sample.compose.screens.Step3Screen
import com.complyco.sample.compose.ui.theme.ComplyCoSamplesTheme
import com.ivangarzab.bark.Bark

class MainActivity : ComponentActivity() {

    private lateinit var tokenFetcher: TokenFetcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComplyCoSamplesTheme {
                Scaffold {
                    OnboardingNavigation(
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }

        // Initialize TokenFetcher with the JWT server URL
        tokenFetcher = TokenFetcher(
            url = "http://api-example.your-bank.com/api/jwt"
        )

        // Start periodic token fetching
        tokenFetcher.start()

        // Initialize the Engine with the TokenFetcher's jwtProducer
        Engine.initializeCompose(
            context = this,
            options = ComposeOptions.default(),
            jwtProducer = tokenFetcher.getJwtProducer(),
            initializationListener = object : InitializationListener {
                override fun didInitialized() {
                    Bark.d("Engine initialized successfully")
                    // Start tracking after initialization
                    Engine.startCompose(Any())
                }

                override fun didFailedToInitialize(error: RecorderError) {
                    Bark.e("Engine failed to initialize: $error")
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the token fetcher when the activity is destroyed
        tokenFetcher.stop()
    }
}

@Composable
fun OnboardingNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    // Mock user data - replace with your actual user data source
    val mockUser = DemoUser(
        email = "user@example.com",
        username = "example-test-user"
    )

    NavHost(
        navController = navController,
        startDestination = "step1",
        modifier = modifier
    ) {
        composable("step1") {
            val mockData = remember { mutableStateOf(DemoUser.EMPTY) }
            val mockPassword = remember { mutableStateOf("") }
            val mockPasswordConfirm = remember { mutableStateOf("") }
            Step1Screen(
                modifier = Modifier.fillMaxSize(),
                user = mockData.value,
                password = mockPassword.value,
                passwordConfirm = mockPasswordConfirm.value,
                onEmailAddressUpdated = { mockData.value = mockData.value.copy(email = it) },
                onUsernameUpdated = { mockData.value = mockData.value.copy(username = it) },
                onPasswordUpdated = { mockPassword.value = it },
                onConfirmPasswordUpdated = { mockPasswordConfirm.value = it },
                onSetMockData = {
                    mockData.value = mockUser
                    mockPassword.value = "123456"
                    mockPasswordConfirm.value = "123456"
                },
                onNavigateToStep2 = {
                    navController.navigate("step2")
                }
            )
        }

        composable("step2") {
            val data = remember { mutableStateOf(Array(6) { "" }) }
            Step2Screen(
                modifier = Modifier.fillMaxSize(),
                data = data.value,
                onDataUpdate = { position, value ->
                    val newArray = data.value.copyOf()
                    newArray[position] = value
                    data.value = newArray
                },
                isDataReady = data.value.all { it.isNotEmpty() && it.matches(Regex("\\d")) },
                onSetMockData = { data.value = arrayOf("3", "4", "1", "6", "5", "9") },
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