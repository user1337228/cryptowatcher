package by.butrameev.crypto.service

import by.butrameev.crypto.entity.dto.CoinLoreDto
import by.butrameev.crypto.service.DefaultCoinLoreService.CoinLore.CRYPTO_DATA_BY_ID
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.IllegalArgumentException

@Service
class DefaultCoinLoreService(
  private val restTemplate: RestTemplate
) {

  companion object CryptosId {
    val ids = arrayOf(90L, 80L, 28L, 5L, 4L, 3L)
  }

  object CoinLore{
    const val CRYPTO_DATA_BY_ID = "https://api.coinlore.net/api/ticker/?id="
  }

  fun tickById(id: Long): CoinLoreDto {
   return restTemplate.getForObject(
     CRYPTO_DATA_BY_ID + id,
     Array<CoinLoreDto>::class.java)?.get(0)
     ?: throw IllegalArgumentException("No crypto with id: $id")
  }
}
