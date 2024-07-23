package com.felipepalma14.translate.model

import androidx.annotation.Keep

@Keep
data class TranslationResult(
    val status: Status,
    val translatedText: String? = null
) {
    enum class Status {
        OK,
        ERROR
    }
}
