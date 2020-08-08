package br.com.fooddelivery.core.email;

import br.com.fooddelivery.domain.service.SendingEmailService;
import br.com.fooddelivery.domain.service.impl.FakeSenderEmailService;
import br.com.fooddelivery.domain.service.impl.SandboxSendingEmailService;
import br.com.fooddelivery.domain.service.impl.SmtpSendingEmailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    private final EmailProperties emailProperties;

    public EmailConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Bean
    public SendingEmailService sendingEmailService() {
        switch (this.emailProperties.getImpl()) {
            case FAKE:
                return new FakeSenderEmailService();

            case SMTP:
                return new SmtpSendingEmailServiceImpl();

            case SANDBOX:
                return new SandboxSendingEmailService();

            default:
                return null;
        }
    }
}