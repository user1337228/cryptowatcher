package by.butrameev.crypto.repository

import by.butrameev.crypto.entity.User
import org.reactivestreams.Publisher
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, Long> {

  override fun findAll(): Flux<User>

  override fun findById(id: Long): Mono<User>

  override fun existsById(id: Long): Mono<Boolean>

  override fun deleteById(id: Publisher<Long>): Mono<Void>

  override fun <S : User> save(entity: S): Mono<S>
}
