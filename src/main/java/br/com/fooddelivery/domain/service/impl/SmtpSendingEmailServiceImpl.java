package br.com.fooddelivery.domain.service.impl;

import br.com.fooddelivery.core.email.EmailProperties;
import br.com.fooddelivery.domain.exception.EmailException;
import br.com.fooddelivery.domain.service.SendingEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SmtpSendingEmailServiceImpl implements SendingEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freeMarkerConfig;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = this.getMimeMessage(message);

            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possível enviar e-mail", e);
        }
    }

    protected MimeMessage getMimeMessage(Message message) throws MessagingException {
        String body = this.processTemplateBody(message);

        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(this.emailProperties.getSender());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubjectMatter());
        helper.setText(body, true);

        return mimeMessage;
    }

    protected String processTemplateBody(Message message) {
        try {
            Template template = this.freeMarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariable());
        } catch (Exception e) {
            throw new EmailException("Unable to process e-mail body", e);
        }
    }
}
