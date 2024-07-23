package com.felipepalma14.translate

import com.felipepalma14.translate.model.TranslationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class TranslateAPI {
    private val baseUrl: String = "https://translate.googleapis.com/translate_a/single?"
    private val url: StringBuilder = StringBuilder(baseUrl)

    suspend fun getTranslation(
        langFrom: String?,
        langTo: String?,
        words: String?
    ): TranslationResult {
        return withContext(Dispatchers.IO) {
            var result: TranslationResult
            try {
                url
                    .append("client=gtx&")
                    .append("sl=")
                    .append(langFrom)
                    .append("&tl=")
                    .append(langTo)
                    .append("&dt=t&q=")
                    .append(
                        URLEncoder.encode(words, "UTF-8")
                    )

                val obj = URL(url.toString())
                val con = obj.openConnection() as HttpURLConnection
                con.setRequestProperty("User-Agent", "Mozilla/5.0")
                val `in` = BufferedReader(InputStreamReader(con.inputStream))
                var inputLine: String?
                val response = StringBuffer()
                while ((`in`.readLine().also { inputLine = it }) != null) {
                    response.append(inputLine)
                }
                `in`.close()
                val respJsonArray = Json.parseToJsonElement(response.toString()).jsonArray

                val firstElementOnTheArray = respJsonArray[0]
                // Nothing was translated: Example, if you input ""
                if (firstElementOnTheArray is JsonNull)
                    return@withContext TranslationResult(TranslationResult.Status.ERROR)

                val output = StringBuilder()
                firstElementOnTheArray.jsonArray.forEach {
                    val innerArray = it.jsonArray

                    val translated = innerArray[0].jsonPrimitive.content
                    val source = innerArray[1].jsonPrimitive.content

                    output.append(translated)
                }

                result = if (output.length > 2) {
                    TranslationResult(TranslationResult.Status.OK, output.toString())
                } else {
                    TranslationResult(TranslationResult.Status.ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result = TranslationResult(TranslationResult.Status.ERROR)
            }
            result
        }
    }
}
