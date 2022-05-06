package by.butrameev.crypto.repository

import by.butrameev.crypto.entity.db.CryptoDynamics
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface CryptoDynamicsRepository: ReactiveCrudRepository<CryptoDynamics, Long>{

  fun findByApisIdOrderByCheckDateDesc(apisId: Long): Mono<CryptoDynamics>
}
