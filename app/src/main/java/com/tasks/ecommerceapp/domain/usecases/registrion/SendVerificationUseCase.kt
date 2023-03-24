package com.tasks.ecommerceapp.domain.usecases.registrion

import android.util.Log
import com.tasks.ecommerceapp.common.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendVerificationUseCase @Inject constructor() {

    suspend operator fun invoke(toEmail: String, code: Int){
        withContext(Dispatchers.IO) {
            try {
                val host = "smtp.gmail.com"
                val port = "587"

                val props = Properties()
                props["mail.smtp.auth"] = "true"
                props["mail.smtp.starttls.enable"] = "true"
                props["mail.smtp.host"] = host
                props["mail.smtp.port"] = port

                val session = Session.getDefaultInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Constants.APP_EMAIL, Constants.APP_PASSWORD)
                    }
                })

                val message = MimeMessage(session)
                message.setFrom(InternetAddress(Constants.APP_EMAIL))
                message.setRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
                message.subject = "Verification Code"
                message.setText("Your verification code is $code")

                Transport.send(message)

            } catch (e: Exception) {
                Log.d("TAG",e.message.toString())
            }
        }

    }
}