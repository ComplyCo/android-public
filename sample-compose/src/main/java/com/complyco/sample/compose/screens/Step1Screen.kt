package com.complyco.sample.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
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
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme) Color.Black else Color.White)
            .combinedClickable(
                onClick = {},
                onLongClick = { onSetData() }
            )
            .complianceTrack(
                label = "Step1Screen",
                type = ComponentType.SCREEN,
                backgroundColor = if (isSystemInDarkTheme) Color.Black else Color.White
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to your [Bank]",
                style = MaterialTheme.typography.headlineMedium,
                color = if (isSystemInDarkTheme) Color.White else Color.Black,
                modifier = Modifier.complianceTrack(
                    label = "WelcomeTitle",
                    type = ComponentType.CONTENT,
                    value = "Welcome to your [Bank]",
                    textStyle = MaterialTheme.typography.headlineMedium
                )
            )

            Text(
                text = "Please create a username and password",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                modifier = Modifier.complianceTrack(
                    label = "WelcomeSubtitle",
                    type = ComponentType.CONTENT,
                    value = "Please create a username and password",
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            )

            CustomTextField(
                label = "Email address",
                value = user.email,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier.complianceTrack(
                    label = "EmailTextField",
                    type = ComponentType.INPUT,
                    value = user.email
                )
            )

            CustomTextField(
                label = "Username",
                value = user.username,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier.complianceTrack(
                    label = "UsernameTextField",
                    type = ComponentType.INPUT,
                    value = user.username
                )
            )

            CustomTextField(
                label = "Password",
                value = password,
                onValueChange = { },
                readOnly = true,
                isPassword = true,
                modifier = Modifier.complianceTrack(
                    label = "PasswordTextField",
                    type = ComponentType.INPUT,
                    value = password
                )
            )

            CustomTextField(
                label = "Confirm Password",
                value = password,
                onValueChange = { },
                readOnly = true,
                isPassword = true,
                modifier = Modifier.complianceTrack(
                    label = "ConfirmPasswordTextField",
                    type = ComponentType.INPUT,
                    value = password
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .complianceTrack(
                        label = "NextButton",
                        type = ComponentType.BUTTON,
                        value = "Next",
                        textStyle = MaterialTheme.typography.labelLarge,
                        backgroundColor = MaterialTheme.colorScheme.primary
                    ),
                onClick = onNavigateToStep2
            ) {
                Text("Next")
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