package by.butrameev.crypto.entity

data class User(

  val id: Long?,
  val cryptoId: Long,
  val name: String,
  val email: String,
  val registerCryptoPrice: Double,
  val requiredPercentChanging: Double
)
