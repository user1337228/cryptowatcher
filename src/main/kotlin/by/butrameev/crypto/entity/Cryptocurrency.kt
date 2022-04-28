package by.butrameev.crypto.entity

import java.time.LocalDate

data class Cryptocurrency(

  val id: Long?,
  val name: String,
  val price: Double?,
  val date: LocalDate
)
