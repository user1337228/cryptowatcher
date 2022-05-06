package by.butrameev.crypto.service

import by.butrameev.crypto.entity.db.CryptoDynamics
import by.butrameev.crypto.entity.db.TrackedCrypto
import by.butrameev.crypto.entity.db.User
import by.butrameev.crypto.repository.CryptoDynamicsRepository
import by.butrameev.crypto.repository.CryptocurrencyRepository
import by.butrameev.crypto.repository.TrackedCryptoRepository
import by.butrameev.crypto.repository.UserRepository
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@EnableAsync
@EnableScheduling
class NotifyService(
  private val userRepository: UserRepository,
  private val trackedCryptoRepository: TrackedCryptoRepository,
  private val cryptoDynamicsRepository: CryptoDynamicsRepository,
  private val cryptocurrencyRepository: CryptocurrencyRepository,
  private val emailService: DefaultEmailService
) {

  companion object MailPattern {
    const val SUBJECT_PATTERN = "Cryptocurrency Price Change Alert"
    const val TEXT_PATTERN = "Cryptocurrency price has changed:"
    const val TEXT_CRYPTO_PATTERN = "\n Cryptocurrency symbol - "
    const val TEXT_PERCENT_PATTERN = "\n Percent change - "
    const val TEXT_REG_PRICE_PATTERN = "\n Registration price - "
    const val TEXT_ACT_PRICE_PATTERN = "\n Actual price - "
  }

  @Scheduled(cron = "10 * * * * *")
  fun checkDifferenceOfPrices() {
    trackedCryptoRepository.findAll()
      .mapNotNull { trackedCrypto ->
        val usersForNotify = userRepository.findById(trackedCrypto.userId)
        val cryptoDynamics = cryptoDynamicsRepository
          .findByApisIdOrderByCheckDateDesc(trackedCrypto.cryptoId)
        return@mapNotNull Triple(usersForNotify, cryptoDynamics, trackedCrypto)
      }
      .flatMap { triple ->
        triple.second.map {
          val registerPrice = triple.third.registerPrice
          val actualPrice = it.cryptoPrice
          if (isNeedToNotify(defineDifferencePrices(registerPrice, actualPrice))) {
            expandUserMonoToNotification(triple.first, it, triple.third)
          }
        }
      }
      .subscribe()
  }

  private fun defineDifferencePrices(registerPrice: Double, actualPrice: Double): Double {
    if (registerPrice > actualPrice) {
      return registerPrice / actualPrice
    }
    return actualPrice / registerPrice
  }

  private fun isNeedToNotify(percentOfChange: Double): Boolean {
    return percentOfChange > 1.0
  }

  private fun expandUserMonoToNotification(
    user: Mono<User>,
    cryptoDynamics: CryptoDynamics,
    trackedCrypto: TrackedCrypto)
  {
    user
      .flatMap {
        val email = it.email
        notifyUser(email, cryptoDynamics, trackedCrypto)
        return@flatMap Mono.empty<User>()
      }
      .subscribe()
  }

  private fun notifyUser(userEmail: String, cryptoDynamics: CryptoDynamics, trackedCrypto: TrackedCrypto) {
    defineCryptoSymbol(trackedCrypto)
      .flatMap {
        val registerPrice = trackedCrypto.registerPrice
        val actualPrice = cryptoDynamics.cryptoPrice
        val percentOfChange = defineDifferencePrices(registerPrice, actualPrice)
        val text = mailMessageBuilder(it, percentOfChange, registerPrice, actualPrice)
        emailService.sendNotification(userEmail, SUBJECT_PATTERN, text)
        return@flatMap Mono.empty<String>()
      }
      .subscribe()
  }

  private fun defineCryptoSymbol(trackedCrypto: TrackedCrypto): Mono<String> {
    return cryptocurrencyRepository
      .findByApisId(trackedCrypto.cryptoId)
      .flatMap { Mono.just(it.name) }
  }

  private fun mailMessageBuilder(
    cryptoSymbol: String,
    percent: Double,
    registerPrice: Double,
    actualPrice: Double
  ): String {
    return TEXT_PATTERN +
        TEXT_CRYPTO_PATTERN + cryptoSymbol +
        TEXT_PERCENT_PATTERN + percent +
        TEXT_REG_PRICE_PATTERN + registerPrice +
        TEXT_ACT_PRICE_PATTERN + actualPrice
  }
}
