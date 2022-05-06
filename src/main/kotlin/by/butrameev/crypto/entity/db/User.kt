package by.butrameev.crypto.entity.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(

  @Id val id: Long?,
  @Column("user_name") val name: String,
  @Column("email") val email: String
)
