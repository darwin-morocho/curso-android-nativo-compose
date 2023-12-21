package app.meedu.crypto.domain.models

data class Crypto(
  val id: String,
  val symbol: String,
  val name: String,
  val priceUsd: Double,
)