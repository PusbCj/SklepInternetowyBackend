package com.example.sklepinternetowy.services;

import com.example.sklepinternetowy.models.user.UserApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;


@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final Logger log= LoggerFactory.getLogger(MailService.class);

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isHtml){
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper message= new MimeMessageHelper(mimeMessage,true, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom("pusb@grodzisk.pl");
            message.setSubject(subject);
            message.setText(content,isHtml);
            javaMailSender.send(mimeMessage);
            log.info("Mail sended");
        }catch (MailException| MessagingException e){
            log.info("Mail not ended. Exception!");
            log.info(e.toString());
        }
    }
    @Async
    public void sendActivationMail(UserApplication user){
        String content="<p>Żeby aktywować konto kliknij </p>";
        content+="<a href=\"http://195.80.229.73/activate?user=" +
                user.getUsername() + "&key=" + user.getKeyActivation()+
        "\">Aktywuj</a>";

        sendEmail(user.getEmail(),"Activation",content,true);
    }

    @Async
    public void sendForgetPasswordMail(Long keyPassword, UserApplication user) {
        String content="<p>Żeby zmienić hasło </p>";
        content+="<a href=\"http://195.80.229.73/changeforgottenpassword?user=" +
                user.getUsername() + "&key=" + keyPassword+
                "\">kliknij tu</a>";

        sendEmail(user.getEmail(),"Activation",content,true);
    }
}
