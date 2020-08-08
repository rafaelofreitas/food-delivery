package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.core.email.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
public class SandboxSendingEmailService extends SmtpSendingEmailServiceImpl {
    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage getMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = super.getMimeMessage(message);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(this.emailProperties.getSandbox().getSender());

        return mimeMessage;
    }
}