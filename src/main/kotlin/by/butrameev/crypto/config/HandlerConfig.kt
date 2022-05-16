package by.butrameev.crypto.config

import by.butrameev.crypto.handler.DefaultCryptocurrencyHandler
import by.butrameev.crypto.handler.DefaultUserHandler
import by.butrameev.crypto.repository.TrackedCryptoRepository
import by.butrameev.crypto.service.DefaultCryptocurrencyService
import by.butrameev.crypto.service.DefaultUserService
import by.butrameev.crypto.util.DefaultTackedCryptoUtil
import by.butrameev.crypto.util.DefaultUserUtil
import by.butrameev.crypto.validator.DefaultEmailValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HandlerConfig(
  private val trackedCryptoRepository: TrackedCryptoRepository,
  private val userService: DefaultUserService,
  private val emailValidator: DefaultEmailValidator,
  private val defaultUserUtil: DefaultUserUtil,
  private val defaultTrackedCryptoUtil: DefaultTackedCryptoUtil,
  private val cryptocurrencyService: DefaultCryptocurrencyService
) {

  @Bean
  fun userHandler(): DefaultUserHandler{
    return DefaultUserHandler(
      trackedCryptoRepository,
      userService,
      emailValidator,
      defaultUserUtil,
      defaultTrackedCryptoUtil
    )
  }

  @Bean
  fun cryptocurrencyHandler(): DefaultCryptocurrencyHandler{
    return DefaultCryptocurrencyHandler(cryptocurrencyService)
  }
}
