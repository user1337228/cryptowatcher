package by.butrameev.crypto.service

import by.butrameev.crypto.entity.db.CryptoDynamics
import by.butrameev.crypto.entity.dto.CoinLoreDto
import by.butrameev.crypto.entity.db.Cryptocurrency
import by.butrameev.crypto.repository.CryptoDynamicsRepository
import by.butrameev.crypto.repository.CryptocurrencyRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
@EnableScheduling
class DefaultCryptocurrencyService(
  private val cryptocurrencyRepository: CryptocurrencyRepository,
  private val cryptoDynamicsRepository: CryptoDynamicsRepository,
  private val restTemplate: RestTemplate
) {

  companion object Converter{
    fun toCryptocurrency(crypto: CoinLoreDto): Pair<Cryptocurrency, CryptoDynamics> {
      return Pair(
        Cryptocurrency(null, crypto.id.toLong(), crypto.symbol),
        CryptoDynamics(null, crypto.id.toLong(), crypto.priceUsd.toDouble(), LocalDate.now())
      )
    }
  }

  object CryptosId {
    val ids = arrayOf(90L, 80L, 48543L, 28L, 5L, 4L, 56821L, 3L)
  }

  object CoinLore{
    const val CRYPTO_DATA_BY_ID = "https://api.coinlore.net/api/ticker/?id="
  }

  fun findAllCryptos(): Flux<Cryptocurrency> {
    return cryptocurrencyRepository.findAll()
  }

  fun findCryptoById(id: Long): Mono<Cryptocurrency> {
    return isExist(id)
      .flatMap { checkResult ->
        if (checkResult){
         return@flatMap cryptocurrencyRepository.findById(id)
        }
        return@flatMap Mono.empty<Cryptocurrency>()
      }
  }

  fun saveCrypto(crypto: Cryptocurrency): Mono<Cryptocurrency> {
    return cryptocurrencyRepository.save(crypto)
  }

  fun deleteCryptoById(id: Long): Mono<Void> {
    return cryptocurrencyRepository.deleteById(id)
  }

  private fun isExist(id: Long): Mono<Boolean>{
    return cryptocurrencyRepository.existsById(id)
  }

  private fun updateAllCryptos(vararg cryptos:  Pair<Cryptocurrency, CryptoDynamics>){
    val cryptosIterable = Flux.fromArray(cryptos)
    cryptosIterable.flatMap { pair ->
      cryptocurrencyRepository.save(pair.first)
      cryptoDynamicsRepository.save(pair.second)
    }
      .subscribe()
  }

  @Scheduled(cron = "0 * * * * *")
  fun updateCryptosPrices(){
      for(id in CryptosId.ids){
        val cryptoDtos = restTemplate.getForObject(CoinLore.CRYPTO_DATA_BY_ID + id,
          Array<CoinLoreDto>::class.java)
        val dto = expandDto(cryptoDtos!!)
        updateAllCryptos(toCryptocurrency(dto))
      }
  }

  private fun expandDto(dtos: Array<CoinLoreDto>): CoinLoreDto {
    return dtos[0]
  }
}
