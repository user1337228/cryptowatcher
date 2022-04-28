package by.butrameev.crypto.repository

import by.butrameev.crypto.entity.Cryptocurrency
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class DefaultCryptocurrencyRepository(
  private val template: R2dbcEntityTemplate
) {

  fun save(cryptocurrency: Cryptocurrency): Mono<Int> {
    return template.update(Cryptocurrency::class.java)
      .matching(Query.query(where("id").`is`(cryptocurrency.id!!)))
      .apply(Update.update("price", cryptocurrency.price))
  }
}


