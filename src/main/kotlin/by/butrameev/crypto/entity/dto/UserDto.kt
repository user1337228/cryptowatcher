package by.butrameev.crypto.entity.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty


class UserDto(
  @JsonProperty("userName") val userName: String = "",
  @JsonProperty("email") val email: String = "",
  @JsonProperty("cryptoSymbols") val cryptoSymbols: List<String> = emptyList()
): java.io.Serializable{
  @JsonCreator
  constructor() : this ("", "", emptyList())

}
