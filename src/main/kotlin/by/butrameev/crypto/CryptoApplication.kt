package by.butrameev.crypto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@RequestMapping("cryptowhatcher/*")
class CryptoApplication

fun main(args: Array<String>) {
	runApplication<CryptoApplication>(*args)
}
