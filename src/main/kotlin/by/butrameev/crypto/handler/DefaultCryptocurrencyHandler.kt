package by.butrameev.crypto.handler

import by.butrameev.crypto.entity.db.Cryptocurrency
import by.butrameev.crypto.service.DefaultCryptocurrencyService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

@Component
class DefaultCryptocurrencyHandler(
  private val cryptocurrencyService: DefaultCryptocurrencyService
) : Handler {

  override fun getAll(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse
      .ok()
      .body(
        cryptocurrencyService.findAllCryptos(),
        Cryptocurrency::class.java
      )
  }

  override fun getById(request: ServerRequest): Mono<ServerResponse> {
    val id = request.pathVariable("id").toLong()
    return ServerResponse
      .ok()
      .body(cryptocurrencyService.findCryptoById(id))
  }

  override fun add(request: ServerRequest): Mono<ServerResponse> {
    val cryptocurrency = request.bodyToMono<Cryptocurrency>()
    return cryptocurrency.flatMap {
      ServerResponse
        .ok()
        .body(cryptocurrencyService.saveCrypto(it))
    }
  }


  override fun delete(request: ServerRequest): Mono<ServerResponse> {
    val id = request.pathVariable("id").toLong()
    return ServerResponse
      .ok()
      .body(cryptocurrencyService.deleteCryptoById(id))
  }
}
