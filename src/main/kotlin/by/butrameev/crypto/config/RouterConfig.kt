package by.butrameev.crypto.config

import by.butrameev.crypto.handler.DefaultCryptocurrencyHandler
import by.butrameev.crypto.handler.DefaultHandler
import by.butrameev.crypto.handler.DefaultUserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
@EnableWebFlux
class RouterConfig(
  private val userHandler: DefaultUserHandler,
  private val defaultHandler: DefaultHandler,
  private val cryptocurrencyHandler: DefaultCryptocurrencyHandler
) {
  companion object Mappings {
    const val INDEX_MAPPING = "/"
    const val USER_MAPPING = "/user"
    const val ID_USER_MAPPING = "/user/{id}"
    const val USERS_MAPPING = "/users"
    const val CRYPTO_MAPPING = "/crypto"
    const val ID_CRYPTO_MAPPING = "/crypto/{id}"
    const val CRYPTOS_MAPPING = "/cryptos"
  }

  @Bean
  fun apiRouter() = router {
    accept(MediaType.TEXT_HTML)
      .nest {
        GET(USERS_MAPPING).invoke { userHandler.getAll(it) }
        GET(CRYPTOS_MAPPING).invoke { cryptocurrencyHandler.getAll(it) }
        GET(ID_USER_MAPPING).invoke { userHandler.getById(it) }
        GET(ID_CRYPTO_MAPPING).invoke { cryptocurrencyHandler.getById(it) }
        POST(USER_MAPPING).invoke { userHandler.add(it) }
        PUT(CRYPTO_MAPPING).invoke { cryptocurrencyHandler.add(it) }
        DELETE(ID_USER_MAPPING).invoke { userHandler.delete(it) }
        DELETE(ID_CRYPTO_MAPPING).invoke { cryptocurrencyHandler.delete(it) }
      }
  }

  @Bean
  fun indexRouter() = router {
    accept(MediaType.TEXT_HTML)
      .nest {
        GET(INDEX_MAPPING).invoke { defaultHandler.index(it) }
      }
    resources("resources/**", ClassPathResource("resources/templates/"))
  }
}

