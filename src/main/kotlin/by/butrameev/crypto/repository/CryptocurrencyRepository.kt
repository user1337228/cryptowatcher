package by.butrameev.crypto.repository

import by.butrameev.crypto.entity.db.Cryptocurrency
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface CryptocurrencyRepository: ReactiveCrudRepository<Cryptocurrency, Long> {

  override fun findAll(): Flux<Cryptocurrency>

  override fun findById(id: Long): Mono<Cryptocurrency>

  override fun existsById(id: Long): Mono<Boolean>

  override fun <S : Cryptocurrency> save(entity: S): Mono<S>

  override fun <S : Cryptocurrency?> saveAll(entities: MutableIterable<S>): Flux<S>

  override fun deleteById(id: Long): Mono<Void>

  fun findByApisId(apisId: Long): Mono<Cryptocurrency>
}
