package by.butrameev.crypto.entity.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("cryptocurrency")
data class Cryptocurrency(

  @Id val id: Long?,
  @Column("apis_id") val apisId: Long,
  @Column("name") val name: String
)
