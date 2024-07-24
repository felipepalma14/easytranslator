package com.felipepalma14.easytranslator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipepalma14.easytranslator.R
import com.felipepalma14.easytranslator.domain.TranslateViewModel
import com.felipepalma14.easytranslator.ui.theme.EasyTranslatorTheme
import com.felipepalma14.translate.model.TranslationResult

@Composable
fun TranslateScreen(
    viewModel: TranslateViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                viewModel.clearText()
            },
            label = { Text(stringResource(R.string.text_field_label)) },
            placeholder = {
                Text(stringResource(R.string.text_field_placeholder))
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.getTranslatedText(text)
            },
            modifier = Modifier.padding(horizontal = 2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Blue)
        ) {
            Text(
                text = stringResource(R.string.button_do_translate),
                style = MaterialTheme.typography.bodyMedium,
                color = White
            )
        }

        if (uiState.value.isLoading){
            CircularProgressIndicator()
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            when(uiState.value.translation.status){
                TranslationResult.Status.OK -> {
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(24.dp),
                        text = uiState.value.translation.translatedText.toString(),
                        fontSize = 18.sp,
                    )
                }
                TranslationResult.Status.ERROR -> {
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(24.dp),
                        text = stringResource(R.string.error_on_translate_text),
                        color = Red
                    )
                }
                else -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TranslatePreview () {
    EasyTranslatorTheme {
        TranslateScreen(TranslateViewModel())
    }
}
