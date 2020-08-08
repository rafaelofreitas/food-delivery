package br.com.fooddelivery.domain.service.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeSenderEmailService extends SmtpSendingEmailServiceImpl {
    @Override
    public void send(Message message) {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String body = this.processTemplateBody(message);

        log.info("[FAKE E-MAIL] For: {}\n{}", message.getRecipients(), body);
    }
}