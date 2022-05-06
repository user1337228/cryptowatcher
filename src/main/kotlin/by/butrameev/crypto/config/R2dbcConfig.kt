package by.butrameev.crypto.config

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration
import dev.miku.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.Connection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import java.time.ZoneId

@Configuration
class R2dbcConfig {

  @Bean
  fun connectionFactory(): MySqlConnectionFactory {
    return MySqlConnectionFactory.from(
      MySqlConnectionConfiguration.builder()
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
  fun databaseClient(connectionFactory: MySqlConnectionFactory): Mono<Connection> {
    return Mono.from(connectionFactory.create())
  }
}
