package app.meedu.crypto.data.repositories_impl

import app.meedu.crypto.core.*
import app.meedu.crypto.data.*
import app.meedu.crypto.domain.failures.*
import app.meedu.crypto.domain.models.*
import app.meedu.crypto.domain.repositories.*

class ExchangeRepositoryImpl(private val httpClient: HttpClient) : ExchangeRepository {
  override suspend fun getCryptoPrices(ids: String): Either<HttpFailure, List<Crypto>> {
    val query = mutableMapOf<String, String>()
    query["ids"] = ids

    val result = httpClient.send<List<Crypto>>(
      path = "/v2/assets", method = HttpMethod.GET,
      queryParameters = query
    ) {
      val array = it.getJSONArray("data")
      val list = mutableListOf<Crypto>()

      for (i in 0 until array.length()) {
        val json = array.getJSONObject(i)
        list.add(
          Crypto(
            id = json.getString("id"),
            name = json.getString("name"),
            symbol = json.getString("symbol"),
            priceUsd = json.getString("priceUsd").toDouble(),
          ),
        )
      }

      return@send list
    }

    return when (result) {
      is HttpResult.Success -> Either.Right(result.data)
      is HttpResult.Failure -> Either.Left(HttpFailure.Unhandled)
    }
  }
}