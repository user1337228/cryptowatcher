package by.butrameev.crypto.entity.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("tracked_cryptos")
data class TrackedCrypto(

  @Id val id: Long?,
  @Column("user_id") val userId: Long,
  @Column("crypto_id") val cryptoId: Long,
  @Column("register_price") val registerPrice: Double
)
