package com.leszczynski.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * Created by Krystian L on 2018-02-25.
 */

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.jpg", new ClassPathResource("memorynotfound-logo.jpg"));

        Context context = new Context();
        Set<String> keys = mail.getModel().keySet();
        Map<String, Object> values = mail.getModel();
//        for (String key : keys) {
//            context.getVariables().put(key, values.get(key).toString());
//        }
        //context.setVariables(mail.getModel());
        String html = templateEngine.process("registerMailNotification", context);
        for (String key : keys) {
            String template = "\\$\\{" + key + "\\}";
            html = html.replaceAll(template, values.get(key).toString());
        }
        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}
