package by.butrameev.crypto.config

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration
import dev.miku.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.Connection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import reactor.core.publisher.Mono
import java.time.ZoneId

@Configuration
class R2DBCConfig {

  @Bean
  fun r2dbcTemplate(): R2dbcEntityTemplate {
    return R2dbcEntityTemplate(connectionFactory())
  }

  @Bean
  fun connectionFactory(): MySqlConnectionFactory {
    return MySqlConnectionFactory.from(MySqlConnectionConfiguration.builder()
      .host("localhost")
      .port(3306)
      .serverZoneId(ZoneId.systemDefault())
      .database("cryptowatch")
      .user("root")
      .password("xkmjr3m3")
      .build()
    )
  }

  @Bean
  fun databaseClient(connectionFactory: MySqlConnectionFactory): Mono<Connection>{
    return Mono.from(connectionFactory.create())
  }
}
