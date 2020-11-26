package com.example.cs4704.service;
import com.example.cs4704.dao_user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailSendingService {

    public void sendmail(String email, int vCode) throws MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("chengenli1107@outlook.com", "Tigerace1995@");
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("chengenli1107@outlook.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Password reset case");
        msg.setContent("Your one time verify code is: <b>" + vCode + "</b>, " +
                "\nthis code will expire within 10 minutes.", "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
    }
}
