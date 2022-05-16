package by.butrameev.crypto.config

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

@Configuration
class RestTemplateConfig {

  companion object RequestParams{
    val TIMEOUT = TimeUnit.SECONDS.toMillis(10000L).toInt()
  }

  @Bean
  fun restTemplate(): RestTemplate {
    return RestTemplate(advancedRequestFactory())
  }

  private fun advancedRequestFactory(): ClientHttpRequestFactory {
    val config = RequestConfig
      .custom()
      .setSocketTimeout(TIMEOUT)
      .setConnectTimeout(TIMEOUT)
      .setConnectionRequestTimeout(TIMEOUT)
      .build()

    val client = HttpClientBuilder
      .create()
      .setDefaultRequestConfig(config)
      .build()

    return HttpComponentsClientHttpRequestFactory(client)
  }
}
