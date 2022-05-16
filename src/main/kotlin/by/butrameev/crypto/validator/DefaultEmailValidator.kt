package by.butrameev.crypto.validator

import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class DefaultEmailValidator {

  companion object Patterns {
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile("[a-zA-Z\\d._-]+@[a-z]+\\\\.+[a-z]+")
  }

  fun isValidString(email: String): Boolean{
    return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
  }
}
