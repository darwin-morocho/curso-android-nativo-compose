package app.meedu.crypto.data.http

import com.squareup.moshi.*
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.*
import java.lang.Exception
import java.net.URLEncoder

class HttpClient(private val baseUrl: String, private val okHttpClient: OkHttpClient) {

  suspend fun <T> send(
    path: String,
    method: HttpMethod,
    queryParameters: Map<String, String> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
    body: Map<String, Any> = emptyMap(),
    parser: (json: JSONObject) -> T,
  ): HttpResult<T> {
    return withContext(Dispatchers.IO) {
      try {

        lateinit var url: String

        if (path.startsWith("http://") || path.startsWith("https://")) {
          url = path
        } else {
          url = baseUrl;
          url += if (!path.startsWith("/")) "/$path" else path
        }

        if (queryParameters.isNotEmpty()) {
          var query = "?"
          for (e in queryParameters) {
            query += "${e.key}=${URLEncoder.encode(e.value, "UTF-8")}&"
          }
          query = query.subSequence(0, query.length - 1).toString()
          url += query
        }

        val request = Request.Builder().url(url)
        for (header in headers) {
          request.addHeader(header.key, header.value)
        }

        when (method) {
          HttpMethod.GET -> {
            request.method("GET", null)
          }

          else -> {
            val moshi = Moshi.Builder().build()
            val jsonBody = moshi.adapter(Map::class.java).toJson(body)
            request.method(
              HttpMethod.GET.name,
              jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())
            )
          }
        }

        val response = okHttpClient.newCall(request.build()).execute()
        if (response.isSuccessful) {
          return@withContext HttpResult.Success(
            statusCode = response.code,
            data = parser(
              JSONObject(
                if (response.body != null) response.body!!.bytes().decodeToString() else "{}",
              ),
            ),
          )
        }

        return@withContext HttpResult.Error(
          statusCode = response.code, errorData = response.message,
        )
      } catch (e: Exception) {
        println("👀 $e")
        return@withContext HttpResult.Error(
          statusCode = 0, errorData = e,
        )
      }
    }
  }
}

enum class HttpMethod {
  GET, POST, PATCH, PUT, DELETE,
}

sealed class HttpResult<T>(open val statusCode: Int) {
  data class Success<T>(override val statusCode: Int, val data: T) : HttpResult<T>(statusCode)
  data class Error<T>(override val statusCode: Int, val errorData: Any) : HttpResult<T>(statusCode)
}
