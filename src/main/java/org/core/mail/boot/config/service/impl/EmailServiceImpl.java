package org.core.mail.boot.config.service.impl;

import javafx.util.Pair;
import org.core.mail.boot.config.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(String sendTo, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(sendTo);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendAttachmentsMail(String sendTo, String title, String content, List<Pair<String, File>> attachments) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(emailFrom);
        helper.setTo(sendTo);
        helper.setSubject(title);
        helper.setText(content);

        for (Pair<String, File> pair : attachments) {
            helper.addAttachment(pair.getKey(), new FileSystemResource(pair.getValue()));
        }

        mailSender.send(mimeMessage);
    }

//    public void sendInlineMail() throws MessagingException {
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setFrom(emailFrom);
//        helper.setTo("286352250@163.com");
//        helper.setSubject("主题：嵌入静态资源");
//        helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);
//
//        FileSystemResource file = new FileSystemResource(new File("weixin.jpg"));
//        helper.addInline("weixin", file);
//        mailSender.send(mimeMessage);
//    }

    public void sendTemplateMail(String sendTo, String title, Map<String, Object> content, List<Pair<String, File>> attachments) throws MessagingException {

//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//        helper.setFrom(emailFrom);
//        helper.setTo(sendTo);
//        helper.setSubject(title);
//
//        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template.vm", "UTF-8", content);
//        helper.setText(text, true);
//
//        for (Pair<String, File> pair : attachments) {
//            helper.addAttachment(pair.getKey(), new FileSystemResource(pair.getValue()));
//        }
//
//
//        mailSender.send(mimeMessage);
    }
}
