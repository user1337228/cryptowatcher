package by.butrameev.crypto.config

import by.butrameev.crypto.handler.DefaultCryptocurrencyHandler
import by.butrameev.crypto.handler.DefaultUserHandler
import by.butrameev.crypto.service.DefaultCryptocurrencyService
import by.butrameev.crypto.service.DefaultUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HandlerConfig(
  private val userService: DefaultUserService,
  private val cryptocurrencyService: DefaultCryptocurrencyService
) {

  @Bean
  fun userHandler(): DefaultUserHandler{
    return DefaultUserHandler(userService)
  }

  @Bean
  fun cryptocurrencyHandler(): DefaultCryptocurrencyHandler{
    return DefaultCryptocurrencyHandler(cryptocurrencyService)
  }
}
