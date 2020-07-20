package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.core.email.EmailProperties;
import br.com.fooddelivery.domain.exception.EmailException;
import br.com.fooddelivery.domain.service.SendingEmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SendingEmailServiceImpl implements SendingEmailService {
    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public SendingEmailServiceImpl(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(this.emailProperties.getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubjectMatter());
            helper.setText(message.getBody(), true);

            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Could not send email", e);
        }
    }
}
