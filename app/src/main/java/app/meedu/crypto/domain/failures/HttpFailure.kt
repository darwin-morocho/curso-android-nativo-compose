package app.meedu.crypto.domain.failures

sealed class HttpFailure {
  object Network : HttpFailure()
  object Unhandled : HttpFailure()
}