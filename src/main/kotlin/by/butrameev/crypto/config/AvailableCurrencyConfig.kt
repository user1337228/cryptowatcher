package by.butrameev.crypto.config

import by.butrameev.crypto.entity.Cryptocurrency
import by.butrameev.crypto.repository.CryptocurrencyRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.time.LocalDate

@Configuration
@ComponentScan("by.butrameev.crypto.repository")
class AvailableCurrencyConfig(
  private val cryptocurrencyRepository: CryptocurrencyRepository
) {


  fun available(): CommandLineRunner {
    return CommandLineRunner {
      cryptocurrencyRepository.saveAll(
        Flux.fromIterable(
          listOf<Cryptocurrency>(
            Cryptocurrency(90L, "BTC", null, LocalDate.now()),
            Cryptocurrency(80L, "ETH", null, LocalDate.now()),
            Cryptocurrency(48543L, "SOL", null, LocalDate.now())
          )
        )
      )
        .subscribe()
    }
  }
}
