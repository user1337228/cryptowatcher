package by.butrameev.crypto.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class DefaultEmailService(
  private val emailSender: JavaMailSender
) {

  fun sendNotification(to: String, subject: String, text: String){
    val mailMessage = SimpleMailMessage()
    mailMessage.setFrom("noreply@cryptowatcher.com")
    mailMessage.setTo(to)
    mailMessage.setSubject(subject)
    mailMessage.setText(text)
    emailSender.send(mailMessage)
  }
}
