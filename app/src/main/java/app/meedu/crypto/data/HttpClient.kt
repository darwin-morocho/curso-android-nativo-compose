package app.meedu.crypto.data

import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.lang.Exception
import java.net.URLEncoder

class HttpClient(private val okHttpClient: OkHttpClient, private val baseUrl: String) {
  suspend fun <T> send(
    path: String,
    method: HttpMethod,
    queryParameters: Map<String, String> = mapOf(),
    headers: Map<String, String> = mapOf(),
    body: Map<String, Any> = mapOf(),
    parser: (json: JSONObject) -> T,
  ): HttpResult<T> {
    try {
      lateinit var url: String

      if (path.startsWith("http://") || path.startsWith("https://")) {
        url = path
      } else {
        url = baseUrl
        url += if (path.startsWith("/")) path else "/$path"

      }

      if (queryParameters.isNotEmpty()) {
        var query = "?"
        for (e in queryParameters) {
          query += "${e.key}=${URLEncoder.encode(e.value, "UTF-8")}&"
        }
        query = query.substring(0, query.length - 1)
        url += query
      }
      val requestBuilder = Request.Builder().url(url)

      for (e in headers) {
        requestBuilder.addHeader(e.key, e.value)
      }

      when (method) {
        HttpMethod.GET -> requestBuilder.method(HttpMethod.GET.name, null)
        else -> {
          val moshi = Moshi.Builder().build()
          val json = moshi.adapter(Map::class.java).toJson(body)
          requestBuilder.method(
            method.name, json.toRequestBody(
              "application/json; charset=utf-8".toMediaType(),
            )
          )
        }
      }
      val response = okHttpClient.newCall(requestBuilder.build()).execute()
      if (response.isSuccessful) {
        return HttpResult.Success(
          response.code,
          parser(
            JSONObject(
              if (response.body != null) response.body!!.bytes().decodeToString() else "{}"
            )
          ),
        )
      }

      return HttpResult.Failure(
        response.code, response.message,
      )
    } catch (e: Exception) {
      println(e)
      return HttpResult.Failure(
        0, e
      )
    }
  }
}

enum class HttpMethod {
  GET, POST, PATCH, PUT, DELETE,
}

sealed class HttpResult<T>(open val statusCode: Int) {
  class Success<T>(override val statusCode: Int, val data: T) : HttpResult<T>(statusCode)
  class Failure<T>(override val statusCode: Int, val errorData: Any) : HttpResult<T>(statusCode)
}