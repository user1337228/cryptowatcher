package by.butrameev.crypto.entity.dto

 class UserDto(
  val userName: String = "",
  val email: String = "",
  val cryptoSymbols: List<String> = emptyList()
): java.io.Serializable{
  constructor() : this ("", "", emptyList())
}
