package by.butrameev.crypto.handler

import by.butrameev.crypto.entity.db.TrackedCrypto
import by.butrameev.crypto.entity.db.User
import by.butrameev.crypto.entity.dto.UserDto
import by.butrameev.crypto.repository.TrackedCryptoRepository
import by.butrameev.crypto.service.DefaultUserService
import by.butrameev.crypto.util.DefaultTackedCryptoUtil
import by.butrameev.crypto.util.DefaultUserUtil
import by.butrameev.crypto.validator.DefaultEmailValidator
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class DefaultUserHandler(
  private val trackedCryptoRepository: TrackedCryptoRepository,
  private val userService: DefaultUserService,
  private val emailValidator: DefaultEmailValidator,
  private val defaultUserUtil: DefaultUserUtil,
  private val defaultTrackedCryptoUtil: DefaultTackedCryptoUtil
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
    val userDto = request.bodyToMono<UserDto>()
    userDto
      .filter { emailValidator.isValidString(it.email) } // Валидация email
      .flatMap {dto ->
        userService.isExistByEmail(dto.email) // Проверка на существование email в БД
          .flatMap { existing ->
            if (existing){
              val user = defaultUserUtil.toUser(dto)
               userService.createOrUpdateUser(user)
                 .flatMap { userDb ->
                   saveAllTrackedCryptos(dto, userDb)
                 }
                 .subscribe()
            }
            return@flatMap Mono.empty<UserDto>()
          }
      }.subscribe()

    return ServerResponse
      .ok()
      .render("redirect:/index", UserDto())
  }

  override fun delete(request: ServerRequest): Mono<ServerResponse> {
    val id = request.pathVariable("id").toLong()
    return ServerResponse
      .ok()
      .body(userService.deleteUser(id))
  }

  private fun saveAllTrackedCryptos(userDto: UserDto, user: User): Mono<TrackedCrypto>{
    var result: Mono<TrackedCrypto> = Mono.empty()
    for (symbol in userDto.cryptoSymbols) {
      val trackedCrypto = defaultTrackedCryptoUtil.toTrackedCrypto(user.id!!, symbol)
      result =  trackedCryptoRepository.save(trackedCrypto)
    }
    return result
  }
}
