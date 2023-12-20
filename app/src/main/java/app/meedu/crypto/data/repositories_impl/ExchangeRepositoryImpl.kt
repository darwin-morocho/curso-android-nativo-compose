package app.meedu.crypto.data.repositories_impl

import app.meedu.crypto.core.*
import app.meedu.crypto.data.http.*
import app.meedu.crypto.domain.failures.*
import app.meedu.crypto.domain.models.*
import app.meedu.crypto.domain.repositories.*
import com.squareup.moshi.Moshi
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class ExchangeRepositoryImpl(private val httpClient: HttpClient) : ExchangeRepository {
  override suspend fun getCryptos(ids: String): Either<HttpFailure, List<Crypto>> {
    val result = httpClient.send<List<Crypto>>(
      path = "/v2/assets?ids=$ids", method = HttpMethod.GET
    ) {
      val list = mutableListOf<Crypto>()
      val array = it.getJSONArray("data")
      for (i in 0 until array.length()) {
        val json = array.getJSONObject(i)
        list.add(
          Crypto(
            id = json["id"] as String,
            name = json["name"] as String,
            symbol = json["symbol"] as String,
            priceUsd = (json["priceUsd"] as String).toDouble(),
          ),
        )
      }
      return@send list
    }
    return when (result) {
      is HttpResult.Success -> Either.Right<HttpFailure, List<Crypto>>(result.data)
      is HttpResult.Error -> Either.Left<HttpFailure, List<Crypto>>(HttpFailure.Unhandled)
    }
  }
}