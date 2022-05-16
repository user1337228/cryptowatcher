package by.butrameev.crypto.config

import by.butrameev.crypto.entity.db.Cryptocurrency
import by.butrameev.crypto.repository.CryptocurrencyRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux

@Configuration
@ComponentScan("by.butrameev.crypto.repository")
class AvailableCurrencyConfig(
  private val cryptocurrencyRepository: CryptocurrencyRepository
) {

  @Bean
  fun available(): CommandLineRunner {
    return CommandLineRunner {
      initCryptocurrencies()
        .forEach{cryptocurrency ->
          cryptocurrencyRepository.existsByApisId(cryptocurrency.apisId)
            .filter{this.equals(false)}
            .flatMap { cryptocurrencyRepository.save(cryptocurrency) }
            .subscribe()
        }
    }
  }

  private fun initCryptocurrencies(): List<Cryptocurrency>{
    return listOf(
      Cryptocurrency(null, 90L, "BTC"),
      Cryptocurrency(null,80L, "ETH"),
      Cryptocurrency(null, 28L, "XMR"),
      Cryptocurrency(null,5L, "FTC"),
      Cryptocurrency(null,4L, "PPC"),
      Cryptocurrency(null,3L, "VTC")
    )
  }
}
