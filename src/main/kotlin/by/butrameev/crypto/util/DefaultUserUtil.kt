package by.butrameev.crypto.util

import by.butrameev.crypto.entity.db.User
import by.butrameev.crypto.entity.dto.UserDto
import org.springframework.stereotype.Component

@Component
class DefaultUserUtil {

  fun toUser(userDto: UserDto): User{
    return User(null, userDto.userName, userDto.userName)
  }
}
