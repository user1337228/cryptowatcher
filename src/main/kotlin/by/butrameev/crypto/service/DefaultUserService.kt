package by.butrameev.crypto.service

import by.butrameev.crypto.entity.db.User
import by.butrameev.crypto.repository.UserRepository
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@EnableAsync
class DefaultUserService(
  private val userRepository: UserRepository
) {

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
  fun isExistByEmail(email: String): Mono<Boolean>{
    return userRepository.existsUserByEmail(email)
  }
  private fun isExist(id: Long): Mono<Boolean> {
    return userRepository.existsById(id)
  }
}
