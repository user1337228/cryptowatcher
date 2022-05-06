package by.butrameev.crypto.handler

import by.butrameev.crypto.entity.db.User
import by.butrameev.crypto.service.DefaultUserService
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono

class DefaultUserHandler(
  private val userService: DefaultUserService
) : Handler {

  override fun getAll(request: ServerRequest): Mono<ServerResponse> {
    return ServerResponse.ok()
      .body(userService.findAllUsers(), User::class.java)
  }

  override fun getById(request: ServerRequest): Mono<ServerResponse> {
    val id = request.pathVariable("id").toLong()
    return ServerResponse
      .ok()
      .body(userService.findUserById(id))
  }

  override fun add(request: ServerRequest): Mono<ServerResponse> {
    val user = request.bodyToMono<User>()
    return user.flatMap {
      ServerResponse
        .ok()
        .body(userService.createOrUpdateUser(it))
    }
  }

  override fun delete(request: ServerRequest): Mono<ServerResponse> {
    val id = request.pathVariable("id").toLong()
    return ServerResponse
      .ok()
      .body(userService.deleteUser(id))
  }
}
