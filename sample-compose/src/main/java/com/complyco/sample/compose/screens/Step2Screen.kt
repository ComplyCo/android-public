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
    
    val backgroundColor = if (isSystemInDarkTheme) Color.Black else Color.White

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .combinedClickable(
                onClick = {},
                onLongClick = { onSetData() }
            )
            .complianceTrack(
                label = "Step2Screen",
                type = ComponentType.SCREEN,
                backgroundColor = backgroundColor
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val title = "Please verify your email"
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = if (isSystemInDarkTheme) Color.White else Color.Black,
                modifier = Modifier.complianceTrack(
                    label = "VerifyEmailTitleText",
                    type = ComponentType.CONTENT,
                    value = title,
                    textStyle = MaterialTheme.typography.headlineMedium,
                    textAlignment = TextAlign.Left
                )
            )

            val subtitle = "Please enter the one-time 6 digit code that we have emailed to you"
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSystemInDarkTheme) Color.LightGray else Color.DarkGray,
                modifier = Modifier.complianceTrack(
                    label = "VerifyEmailSubtitleText",
                    type = ComponentType.CONTENT,
                    value = subtitle,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    textAlignment = TextAlign.Left
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
                                value = digit,
                                borderColor = Color.Black,
                                borderWidth = 1f,
                                textAlignment = TextAlign.Center
                            ),
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        singleLine = true,
                        readOnly = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val buttonText = "Verify"
            Button(
                onClick = onNavigateToStep3,
                modifier = Modifier
                    .fillMaxWidth()
                    .complianceTrack(
                        label = "VerifyButton",
                        type = ComponentType.BUTTON,
                        value = buttonText,
                        textStyle = MaterialTheme.typography.labelLarge,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        cornerRadius = 8f
                    )
            ) {
                Text(buttonText)
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