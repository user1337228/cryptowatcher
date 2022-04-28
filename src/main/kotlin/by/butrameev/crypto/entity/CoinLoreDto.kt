package by.butrameev.crypto.entity


data class CoinLoreDto(

  val id: String,
  val symbol: String,
  val name: String,
  val nameid: String,
  val rank: Int,
  val price_usd: String,
  val percent_change_24h: String,
  val percent_change_1h: String,
  val percent_change_7d: String,
  val market_cap_usd: String,
  val volume24: String,
  val volume24_native: String,
  val csupply: String,
  val price_btc: String,
  val tsupply: String,
  val msupply: String
)
