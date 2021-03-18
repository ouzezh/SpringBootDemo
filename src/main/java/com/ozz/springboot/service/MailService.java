package com.ozz.springboot.service;

import com.sun.tools.javac.util.Pair;
import java.io.File;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String mailFrom;

  public void sendSimpleMail(String mailTo, String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(mailFrom);
    message.setTo(mailTo);
//    message.setCc("");// 抄送
//    message.setBcc("");// 秘密
    message.setSubject(subject);
    message.setText(content);
    javaMailSender.send(message);
  }

  public void sendMimeMail(String mailTo, String subject, String content, List<Pair<String, File>> attachments) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(mailFrom);
      helper.setTo(mailTo);
      helper.setSubject(subject);
      helper.setText(content);
      for (Pair<String, File> attachment : attachments) {
        helper.addAttachment(attachment.fst, attachment.snd);
      }
      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
