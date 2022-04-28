package by.butrameev.crypto.config

import by.butrameev.crypto.handler.DefaultCryptocurrencyHandler
import by.butrameev.crypto.handler.DefaultUserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig(
  private val userHandler: DefaultUserHandler,
  private val cryptocurrencyHandler: DefaultCryptocurrencyHandler
) {
  companion object Mappings {
    const val APP_MAPPING = "cryptowhatcher"
    const val USER_MAPPING = "/user"
    const val ID_USER_MAPPING = "/user/{id}"
    const val USERS_MAPPING = "/users"
    const val CRYPTO_MAPPING = "/crypto"
    const val ID_CRYPTO_MAPPING = "/crypto/{id}"
    const val CRYPTOS_MAPPING = "/cryptos"
  }

  @Bean
  fun apiRouter() = router {
    accept(MediaType.APPLICATION_JSON)
      .nest {
        GET(USERS_MAPPING).invoke { userHandler.getAll(it) }
        GET(CRYPTOS_MAPPING).invoke { cryptocurrencyHandler.getAll(it) }
        GET(ID_USER_MAPPING).invoke { userHandler.getById(it) }
        GET(ID_CRYPTO_MAPPING).invoke { cryptocurrencyHandler.getById(it) }
        PUT(USER_MAPPING).invoke { userHandler.add(it) }
        PUT(CRYPTO_MAPPING).invoke { cryptocurrencyHandler.add(it) }
        DELETE(ID_USER_MAPPING).invoke { userHandler.delete(it) }
        DELETE(ID_CRYPTO_MAPPING).invoke { cryptocurrencyHandler.delete(it) }
      }
  }
}

