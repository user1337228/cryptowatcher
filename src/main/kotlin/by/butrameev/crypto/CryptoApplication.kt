package by.butrameev.crypto

import by.butrameev.crypto.config.R2dbcConfig
import org.springframework.boot.autoconfigure.AutoConfigurationPackage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@EnableR2dbcRepositories("by.butrameev.crypto.repository")
@AutoConfigurationPackage
class CryptoApplication

fun main(args: Array<String>) {
	runApplication<CryptoApplication>(*args)
}
