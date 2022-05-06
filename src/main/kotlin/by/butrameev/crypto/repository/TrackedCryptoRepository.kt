package by.butrameev.crypto.repository

import by.butrameev.crypto.entity.db.TrackedCrypto
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface TrackedCryptoRepository: ReactiveCrudRepository<TrackedCrypto, Long>{

  fun findTrackedCryptoByCryptoId(cryptoId: Long): Mono<TrackedCrypto>
}
