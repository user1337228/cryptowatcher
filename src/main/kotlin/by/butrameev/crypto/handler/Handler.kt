package by.butrameev.crypto.handler

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface Handler {

  fun getAll(request: ServerRequest): Mono<ServerResponse>
  fun getById(request: ServerRequest): Mono<ServerResponse>
  fun add(request: ServerRequest): Mono<ServerResponse>
  fun delete(request: ServerRequest): Mono<ServerResponse>
}
