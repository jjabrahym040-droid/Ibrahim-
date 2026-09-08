package com.androidify.smoke

import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

/** Minimal network layer wired to the real backend endpoint. */
object ApiClient {
    fun get(path: String): String = request("GET", path, null)

    fun post(path: String, jsonBody: String): String = request("POST", path, jsonBody)

    private fun request(method: String, path: String, jsonBody: String?): String {
        val connection = URL(ApiConfig.endpoint(path)).openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = method
            connection.connectTimeout = 15000
            connection.readTimeout = 20000
            connection.setRequestProperty("Accept", "application/json")
            if (jsonBody != null) {
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")
                connection.outputStream.use { it.write(jsonBody.toByteArray()) }
            }
            val stream = if (connection.responseCode in 200..299) connection.inputStream else connection.errorStream
            stream?.bufferedReader()?.use(BufferedReader::readText) ?: ""
        } finally {
            connection.disconnect()
        }
    }
}
