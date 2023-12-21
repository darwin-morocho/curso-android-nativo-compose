package app.meedu.crypto.domain.repositories

import app.meedu.crypto.core.*
import app.meedu.crypto.domain.failures.*
import app.meedu.crypto.domain.models.*

interface ExchangeRepository {
  suspend fun getCryptoPrices(ids: String): Either<HttpFailure, List<Crypto>>
}