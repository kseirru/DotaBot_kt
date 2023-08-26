package core

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HTTPClient {
    private val okHttpClient: OkHttpClient = OkHttpClient()
    private val logger: Logger = LoggerFactory.getLogger("HTTPClient")

    fun sendGetRequest(url: String) : JsonObject? {
        val request = Request.Builder()
            .url(url)
            .build()

        return try {
            val response = this.okHttpClient.newCall(request).execute()
            val result: JsonObject = JsonParser.parseString(response.body?.string()).asJsonObject
            response.close()
            result
        } catch (e: Exception) {
            this.logger.error("Error when trying to send a request!")
            this.logger.error(e.message)
            null
        }
    }

    fun sendGetRequest(url: String, args: Map<String, Any>) : JsonObject? {
        return this.sendGetRequest(url + "?" + parseArgs(args))
    }

    companion object {
        fun parseArgs(args: Map<String, Any>) : String {
            val stringBuilder = StringBuilder()
            for ((k,v) in args.entries) {
                stringBuilder.append(k).append("=").append(v).append("&")
            }
            return stringBuilder.substring(0, stringBuilder.length - 1).replace(" ", "%20")
        }
    }

}