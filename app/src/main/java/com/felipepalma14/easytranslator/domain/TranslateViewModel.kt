package com.felipepalma14.easytranslator.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipepalma14.translate.Language
import com.felipepalma14.translate.TranslateAPI
import com.felipepalma14.translate.model.TranslationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TranslateUiState(
    val isLoading: Boolean = false,
    val translation: TranslationResult = TranslationResult()
)

class TranslateViewModel : ViewModel() {
    private val translateAPI = TranslateAPI()
    private val _uiState = MutableStateFlow(TranslateUiState())
    val uiState = _uiState.asStateFlow()

    fun getTranslatedText(text: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = currentState.isLoading.not()
                )
            }
            val translatedText = translateAPI.getTranslation(
                langFrom = Language.ENGLISH,
                langTo = Language.PORTUGUESE,
                words = text
            )
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = currentState.isLoading.not(),
                    translation = translatedText
                )
            }
        }
    }

    fun clearText() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoading = false,
                translation = TranslationResult()
            )
        }
    }
}