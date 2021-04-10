package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.utils.AppConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;

@Service
public class AppMailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;

    public AppMailService(JavaMailSender mailSender, @Qualifier("emailTemplateEngine") TemplateEngine htmlTemplateEngine) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }
    public void sendSimpleMail(final String recipientEmail, final String subject, final Context ctx)
            throws MessagingException {

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, AppConstants.EMAIL_TEMPLATE_ENCODING);
        message.setSubject(subject);
        message.setFrom(AppConstants.EMAIL_SENDING_FROM);
        message.setTo(recipientEmail);

        final String htmlContent = this.htmlTemplateEngine.process(AppConstants.EMAIL_CONFIRM_ORDER_TEMPLATE, ctx);
        message.setText(htmlContent, true);

        this.mailSender.send(mimeMessage);
    }
}