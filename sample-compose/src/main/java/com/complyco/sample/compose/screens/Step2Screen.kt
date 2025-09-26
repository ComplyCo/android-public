package com.complyco.sample.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.complyco.complysdk.recorder.compose.extensions.ComponentType
import com.complyco.complysdk.recorder.compose.extensions.complianceTrack
import com.complyco.sample.compose.ui.theme.ComplySampleTheme

@Composable
fun Step2Screen(
    modifier: Modifier = Modifier,
    data: List<String>,
    onSetData: () -> Unit,
    onNavigateToStep3: () -> Unit
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme) Color.Black else Color.White
            )
            .combinedClickable(
                onClick = {},
                onLongClick = { onSetData() }
            )
            .complianceTrack(
                label = "Step2Screen",
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
                text = "Please verify your email",
                style = MaterialTheme.typography.headlineMedium,
                color = if (isSystemInDarkTheme) Color.White else Color.Black,
                modifier = Modifier.complianceTrack(
                    label = "VerifyEmailTitle",
                    type = ComponentType.CONTENT,
                    value = "Please verify your email",
                    textStyle = MaterialTheme.typography.headlineMedium
                )
            )

            Text(
                text = "Please enter the one-time 6 digit code that we have emailed to you",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                modifier = Modifier.complianceTrack(
                    label = "VerifyEmailSubtitle",
                    type = ComponentType.CONTENT,
                    value = "Please enter the one-time 6 digit code that we have emailed to you",
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(6) { index ->
                    val digit = data.getOrNull(index) ?: ""
                    OutlinedTextField(
                        value = digit,
                        onValueChange = { },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .complianceTrack(
                                label = "CodeDigit${index + 1}Text",
                                type = ComponentType.INPUT,
                                value = digit
                            ),
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true,
                        readOnly = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToStep3,
                modifier = Modifier
                    .fillMaxWidth()
                    .complianceTrack(
                        label = "VerifyButton",
                        type = ComponentType.BUTTON,
                        value = "Verify",
                        textStyle = MaterialTheme.typography.labelLarge,
                        backgroundColor = MaterialTheme.colorScheme.primary
                    )
            ) {
                Text("Verify")
            }
        }
    }
}

@Preview
@Composable
fun Step2ScreenPreview() {
    ComplySampleTheme {
        Step2Screen(
            modifier = Modifier.fillMaxSize(),
            data = listOf("3", "4", "1", "6", "5", "9"),
            onSetData = {},
            onNavigateToStep3 = {}
        )
    }
}