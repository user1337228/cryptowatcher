package by.butrameev.crypto.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("cryptocurrency")
data class Cryptocurrency(

  @Id val id: Long?,
  @Column("crypto_id") val cryptoId: Long,
  @Column("name") val name: String,
  @Column("price") val price: Double?,
  @Column("date") val date: LocalDate
)
