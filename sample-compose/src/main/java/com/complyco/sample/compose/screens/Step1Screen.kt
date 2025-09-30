package com.complyco.sample.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.complyco.complysdk.recorder.compose.extensions.ComponentType
import com.complyco.complysdk.recorder.compose.extensions.complianceTrack
import com.complyco.sample.compose.components.CustomTextField
import com.complyco.sample.compose.components.DemoUser
import com.complyco.sample.compose.ui.theme.ComplySampleTheme

@Composable
fun Step1Screen(
    modifier: Modifier = Modifier,
    user: DemoUser,
    password: String,
    onSetData: () -> Unit,
    onNavigateToStep2: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = {},
                onLongClick = { onSetData() }
            )
            .background(Color.White)
            .complianceTrack(
                label = "Step1Screen",
                type = ComponentType.SCREEN,
                backgroundColor = Color.White
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val title = "Welcome to your [Bank]"
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.complianceTrack(
                    label = "WelcomeTitleText",
                    type = ComponentType.CONTENT,
                    value = title,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    textAlignment = TextAlign.Left
                )
            )

            val subtitle = "Please create a username and password"
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.complianceTrack(
                    label = "WelcomeSubtitleText",
                    type = ComponentType.CONTENT,
                    value = subtitle,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    textAlignment = TextAlign.Left
                )
            )

            val emailPlaceholder = "Email address"
            CustomTextField(
                label = emailPlaceholder,
                value = user.email,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier.complianceTrack(
                    label = "EmailTextField",
                    type = ComponentType.INPUT,
                    placeholder = emailPlaceholder,
                    value = user.email,
                    borderColor = Color.Black,
                    borderWidth = 1f,
                    textAlignment = TextAlign.Left
                )
            )

            val usernamePlaceholder = "Username"
            CustomTextField(
                label = usernamePlaceholder,
                value = user.username,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier.complianceTrack(
                    label = "UsernameTextField",
                    type = ComponentType.INPUT,
                    placeholder = usernamePlaceholder,
                    value = user.username,
                    borderColor = Color.Black,
                    borderWidth = 1f,
                    textAlignment = TextAlign.Left
                )
            )

            val passwordPlaceholder = "Password"
            CustomTextField(
                label = passwordPlaceholder,
                value = password,
                onValueChange = { },
                readOnly = true,
                isPassword = true,
                modifier = Modifier.complianceTrack(
                    label = "PasswordTextField",
                    type = ComponentType.INPUT,
                    placeholder = passwordPlaceholder,
                    value = password,
                    isSecureTextEntry = true,
                    masked = true,
                    borderColor = Color.Black,
                    borderWidth = 1f,
                    textAlignment = TextAlign.Left
                )
            )

            val confirmPasswordPlaceholder = "Confirm Password"
            CustomTextField(
                label = confirmPasswordPlaceholder,
                value = password,
                onValueChange = { },
                readOnly = true,
                isPassword = true,
                modifier = Modifier.complianceTrack(
                    label = "ConfirmPasswordTextField",
                    type = ComponentType.INPUT,
                    placeholder = confirmPasswordPlaceholder,
                    value = password,
                    isSecureTextEntry = true,
                    masked = true,
                    borderColor = Color.Black,
                    borderWidth = 1f,
                    textAlignment = TextAlign.Left
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            val buttonText = "Next"
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .complianceTrack(
                        label = "NextButton",
                        type = ComponentType.BUTTON,
                        value = buttonText,
                        textStyle = MaterialTheme.typography.labelLarge,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        cornerRadius = 8f
                    ),
                onClick = onNavigateToStep2
            ) {
                Text(buttonText)
            }
        }
    }
}

@Preview
@Composable
fun Step1ScreenPreview() {
    ComplySampleTheme {
        Step1Screen(
            user = DemoUser(
                email = "test@complyco.com",
                username = "testUser"
            ),
            password = "123456",
            onSetData = {},
            onNavigateToStep2 = {}
        )
    }
}