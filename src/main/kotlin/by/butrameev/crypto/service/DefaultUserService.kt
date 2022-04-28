package by.butrameev.crypto.service

import by.butrameev.crypto.entity.Cryptocurrency
import by.butrameev.crypto.entity.User
import by.butrameev.crypto.repository.CryptocurrencyRepository
import by.butrameev.crypto.repository.UserRepository
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@EnableAsync
@EnableScheduling
class DefaultUserService(
  private val userRepository: UserRepository,
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
  fun createOrUpdateUser(user: User): Mono<User> {
    return userRepository.save(user)
  }

  fun findAllUsers(): Flux<User> {
    return userRepository.findAll()
  }

  fun findUserById(id: Long): Mono<User> {
    return userRepository.findById(id)
  }

  fun deleteUser(id: Long): Mono<Void> {
    return isExist(id)
      .filter { it != null }
      .flatMap { checkResult ->
        if (checkResult){
          return@flatMap userRepository.deleteById(id)
        }
        return@flatMap Mono.empty()
      }
  }

  private fun isExist(id: Long): Mono<Boolean> {
    return userRepository.existsById(id)
  }

  @Scheduled(cron = "10 * * * * *") // TODO: 28.04.2022 analyze and cleanup method 
  fun checkDifferenceOfPrices(){
     findAllUsers()
      .flatMap { user ->
        val crypto = cryptocurrencyRepository.findById(user.cryptoId)
        return@flatMap Mono.just(Pair<User, Mono<Cryptocurrency>>(user, crypto))
      }
      .flatMap { pair ->
        pair.second
          .flatMap {crypto ->
            val difference = defineDifferencePrices(pair.first.registerCryptoPrice, crypto.price!!)
             Mono.just(Pair<Cryptocurrency, Double>(crypto, difference))
          }.map { notificationPair ->
              if (isNeedToNotify(notificationPair.second)){
                emailService
                  .sendNotification(
                    pair.first.email,
                    SUBJECT_PATTERN,
                    mailMessageBuilder(
                      notificationPair.first.name,
                      notificationPair.second,
                      pair.first.registerCryptoPrice,
                      notificationPair.first.price!!
                    )
                )
            }
          }
      }
       .subscribe()
  }

  private fun defineDifferencePrices(registerPrice: Double, actualPrice: Double): Double{
    if (registerPrice > actualPrice){
      return registerPrice / actualPrice
    }
    return actualPrice / registerPrice
  }

  private fun isNeedToNotify(percentOfChange: Double): Boolean{
    return percentOfChange > 1.0
  }

  private fun mailMessageBuilder(
    cryptoSymbol: String,
    percent: Double, registerPrice: Double,
    actualPrice: Double): String {
    return TEXT_PATTERN +
        TEXT_CRYPTO_PATTERN + cryptoSymbol +
        TEXT_PERCENT_PATTERN + percent +
        TEXT_REG_PRICE_PATTERN + registerPrice +
        TEXT_ACT_PRICE_PATTERN + actualPrice
  }
}
