package by.butrameev.crypto.util

import by.butrameev.crypto.entity.db.TrackedCrypto
import by.butrameev.crypto.service.DefaultCoinLoreService
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class DefaultTackedCryptoUtil(
  private val defaultCoinLoreService: DefaultCoinLoreService
){

   fun toTrackedCrypto(userId: Long, cryptoSymbol: String): TrackedCrypto {
    val cryptoId = extractCryptoIdBySymbol(cryptoSymbol)
    val cryptoPrice = defaultCoinLoreService.tickById(cryptoId).priceUsd.toDouble()
    return TrackedCrypto(null, userId, cryptoId, cryptoPrice)
  }

  private fun extractCryptoIdBySymbol(symbol: String) : Long =
    when(symbol) {
          "BTC" -> 90L
          "XMR" -> 28L
          "FTC" -> 5L
          "VTC" -> 3L
          "PPC" -> 4L
          "ETH" -> 80L
      else -> throw IllegalArgumentException("Invalid symbol: $symbol")
  }
}
