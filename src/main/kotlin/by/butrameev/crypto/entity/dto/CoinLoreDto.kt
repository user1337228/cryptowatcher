package by.butrameev.crypto.entity.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CoinLoreDto(

  @JsonProperty("id") val id: String,
  @JsonProperty("symbol") val symbol: String,
  @JsonProperty("name") val name: String,
  @JsonProperty("nameid") val nameId: String,
  @JsonProperty("rank") val rank: Int,
  @JsonProperty("price_usd") val priceUsd: String,
  @JsonProperty("percent_change_24h") val percentChange24h: String,
  @JsonProperty("percent_change_1h") val percentChange1h: String,
  @JsonProperty("percent_change_7d") val percentChange7d: String,
  @JsonProperty("market_cap_usd") val marketCapUsd: String,
  @JsonProperty("volume24") val volume24: String,
  @JsonProperty("volume24_native") val volume24Native: String,
  @JsonProperty("csupply") val csupply: String,
  @JsonProperty("price_btc") val priceBtc: String,
  @JsonProperty("tsupply") val tsupply: String,
  @JsonProperty("msupply") val msupply: String
)
