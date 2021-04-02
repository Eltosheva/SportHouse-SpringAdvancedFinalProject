package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.utils.AppConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Date;

import org.thymeleaf.context.Context;

@Service
public class AppMailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine htmlTemplateEngine;
    private final TemplateEngine textTemplateEngine;
    private final TemplateEngine stringTemplateEngine;

    public AppMailService(JavaMailSender mailSender, @Qualifier("emailTemplateEngine") TemplateEngine htmlTemplateEngine,@Qualifier("emailTemplateEngine") TemplateEngine textTemplateEngine,@Qualifier("emailTemplateEngine") TemplateEngine stringTemplateEngine) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
        this.textTemplateEngine = textTemplateEngine;
        this.stringTemplateEngine = stringTemplateEngine;
    }

    /*
     * Send HTML mail (simple)
     */
    public void sendSimpleMail(final String recipientEmail, final String subject, final Context ctx)
            throws MessagingException {

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, AppConstants.EMAIL_TEMPLATE_ENCODING);
        message.setSubject(subject);
        message.setFrom(AppConstants.EMAIL_SENDING_FROM);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(AppConstants.EMAIL_CONFIRM_ORDER_TEMPLATE, ctx);
        message.setText(htmlContent, true);

        // Send email
        this.mailSender.send(mimeMessage);
    }
}