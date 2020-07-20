package br.com.fooddelivery.domain.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public interface SendingEmailService {
    void send(Message message);

    @Getter
    @Setter
    class Message {
        private Set<String> recipients;
        private String subjectMatter;
        private String body;
    }
}
