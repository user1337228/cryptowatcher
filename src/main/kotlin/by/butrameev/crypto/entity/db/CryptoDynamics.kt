package by.butrameev.crypto.entity.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("crypto_dynamics")
data class CryptoDynamics(

  @Id val id: Long?,
  @Column("crypto_id") val apisId: Long,
  @Column("crypto_price") val cryptoPrice: Double,
  @Column("date") val checkDate: LocalDate
)
