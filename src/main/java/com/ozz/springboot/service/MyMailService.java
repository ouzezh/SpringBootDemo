package com.ozz.springboot.service;

import com.ozz.springboot.exception.ErrorException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyMailService {
  @Autowired
  JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String mailFrom;
  @Value("${ozz.mail.mailTo}")
  private String[] mailTo;

  String getSubject(String subject) {
    return String.format("[MyTest] %s", subject);
  }

  public void sendErrorMail(String subject, String content, Throwable e) {
    try (ByteArrayOutputStream bo = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(
        bo, true, "utf-8");) {
      if(Strings.isNotBlank(content)) {
        ps.print(content);
        ps.print("\n\n");
      }
      if(e != null) {
        e.printStackTrace(ps);
      }
      sendMail(subject, bo.toString());
    } catch (Exception e2) {
      if(e != null) {
        e.addSuppressed(e2);
      } else {
        log.error(subject + "\n" + content, e2);
      }
    }
  }

  public void sendMail(String subject, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(mailFrom);
    message.setTo(mailTo);
//    message.setCc("");// 抄送
//    message.setBcc("");// 秘密
    message.setSubject(getSubject(subject));
    message.setText(content);
    javaMailSender.send(message);
  }

  public void sendMimeMail(String subject, String content, List<Pair<String, File>> attachments) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(mailFrom);
      helper.setTo(mailTo);
      helper.setSubject(getSubject(subject));
      helper.setText(content);
      for (Pair<String, File> attachment : attachments) {
        helper.addAttachment(attachment.getKey(), attachment.getValue());
      }
      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
