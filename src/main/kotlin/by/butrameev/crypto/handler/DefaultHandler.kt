package by.butrameev.crypto.handler

import by.butrameev.crypto.entity.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DefaultHandler {

  fun index(request: ServerRequest): Mono<ServerResponse>{
    val attrs = mapOf<String, Any>(
      Pair("userDto", UserDto())
    )
    return ServerResponse
      .ok()
      .contentType(MediaType.TEXT_HTML)
      .render("index", attrs)
  }
}
