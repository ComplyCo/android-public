package com.complyco.sample.compose.screens

import androidx.compose.foundation.background
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
import com.complyco.sample.compose.components.DemoUser
import com.complyco.sample.compose.ui.theme.ComplySampleTheme

@Composable
fun Step3Screen(
    modifier: Modifier = Modifier,
    user: DemoUser,
    onApplicationComplete: () -> Unit
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme) Color.Black else Color.White
            )
            .complianceTrack(
                label = "Step3Screen",
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
                text = "You're registered",
                style = MaterialTheme.typography.headlineMedium,
                color = if (isSystemInDarkTheme) Color.White else Color.Black,
                modifier = Modifier.complianceTrack(
                    label = "RegistrationCompleteTitle",
                    type = ComponentType.CONTENT,
                    value = "You're registered",
                    textStyle = MaterialTheme.typography.headlineMedium
                )
            )

            Text(
                text = "Your email has been successfully verified. Your progress will now be saved going forward. If you need to stop, you can log back in with the username and password you just created.",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                modifier = Modifier.complianceTrack(
                    label = "RegistrationCompleteDescription",
                    type = ComponentType.CONTENT,
                    value = "Your email has been successfully verified. Your progress will now be saved going forward. If you need to stop, you can log back in with the username and password you just created.",
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            )

            Text(
                text = buildString {
                    append("Username: ${user.username}\n")
                    append("Email: ${user.email}")
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSystemInDarkTheme) Color.White else Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .complianceTrack(
                        label = "UserCredentialsDisplay",
                        type = ComponentType.CONTENT,
                        value = "Username: ${user.username}\nEmail: ${user.email}",
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onApplicationComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .complianceTrack(
                        label = "ApplicationCompleteButton",
                        type = ComponentType.BUTTON,
                        value = "Complete",
                        textStyle = MaterialTheme.typography.labelLarge,
                        backgroundColor = MaterialTheme.colorScheme.primary
                    )
            ) {
                Text("Complete")
            }
        }
    }
}

@Preview
@Composable
fun Step3ScreenPreview() {
    ComplySampleTheme {
        Step3Screen(
            user = DemoUser(
                username = "testUser",
                email = "test@complyco.com"
            ),
            onApplicationComplete = {}
        )
    }
}