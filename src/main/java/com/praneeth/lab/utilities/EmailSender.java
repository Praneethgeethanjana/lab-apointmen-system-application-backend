package com.praneeth.lab.utilities;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailSender {

    @Value("${mail.from}")
    private String mailFrom;

    private final Environment environment;

    private final JavaMailSender javaMailSender;


    public void sendHtmlEmail(MimeMessage message) {
        try {
            log.info("Start function sendHtmlEmail");

            javaMailSender.send(message);

            log.info("E-mail Sent.!");
        } catch (Exception e) {
            log.error("Failed to send HTML email: " + e.getMessage(), e);
            throw e;
        }
    }

    public void sendSimpleEmail(String recipient, String subject, String content) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom(mailFrom, "ancybtrading.com");

//            message.setFrom(mailFrom);

            log.info("recipient : " + recipient);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));


            message.setSubject(subject);

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=utf-8");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            sendHtmlEmail(message);
            log.info("Email successfully dispatched.");

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Function : sendSimpleEmail " + e.getMessage(), e);
            try {
                throw e;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void sendInquiryEmail(String subject, String content) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom(mailFrom, "ancybtrading.com");

//            message.setFrom(mailFrom);

            String recipient = environment.getRequiredProperty("inquiry.to.email");
            log.info("recipient : " + recipient);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));

            // set BCC recipients
            message.addRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(environment.getRequiredProperty("inquiry.bcc.email1")));
            message.addRecipients(Message.RecipientType.CC,
                    InternetAddress.parse(environment.getRequiredProperty("inquiry.bcc.email2")));

            message.setSubject(subject);

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=utf-8");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            sendHtmlEmail(message);
            log.info("Email successfully dispatched.");

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Function : sendInquiryEmail " + e.getMessage(), e);
            try {
                throw e;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }


    public void sendEmailWithAttachment(String [] emailToAddresses, String subject, String text, Optional<FileSystemResource> resource) {

        try {
            Date utcNowForTransDate = DateGenerator.getUtcNowForTransDate(new Date());
            Date previousDateTime =  DateGenerator.getPreviousDateTime(utcNowForTransDate);

            MimeMessage msg = javaMailSender.createMimeMessage();

            // true = multipart message
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            log.info("email send to : " + Arrays.toString(emailToAddresses));
            helper.setTo(environment.getRequiredProperty("report.email-n1"));

            helper.setCc(emailToAddresses);

            helper.setSubject(subject);
            if (resource.isPresent()){
                helper.addAttachment(
                        "ancybtrading-SLIPS-" + new SimpleDateFormat("dd-MM-yyyy").format(previousDateTime) + ".csv",
                        resource.get());
            }
            helper.setText(text, true);
            helper.setFrom(mailFrom, "ancybtrading.lk");
            javaMailSender.send(msg);
            log.info("Method sendEmailWithAttachment : mail successfully dispatched");
        } catch (Exception e) {
            log.error("Method sendEmailWithAttachment : mail sending failed - " + e.getMessage());
        }
    }

    public void sendEmailWithList(String [] emailToAddresses, String subject, String text) {

        try {

            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false);
            log.info("bbc email list => :: " + Arrays.toString(emailToAddresses));
            helper.setBcc(emailToAddresses);
            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setFrom(mailFrom, "ancybtrading.com");
            javaMailSender.send(msg);
            log.info("Method sendEmailWithList : mail successfully dispatched");
        } catch (Exception e) {
            log.error("Method sendEmailWithList : mail sending failed - " + e.getMessage());
        }
    }



}
