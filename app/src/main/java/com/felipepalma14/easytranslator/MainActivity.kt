package com.felipepalma14.easytranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.felipepalma14.easytranslator.domain.TranslateViewModel
import com.felipepalma14.easytranslator.presentation.TranslateScreen
import com.felipepalma14.easytranslator.ui.theme.EasyTranslatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyTranslatorTheme {
                TranslateScreen(TranslateViewModel())
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
