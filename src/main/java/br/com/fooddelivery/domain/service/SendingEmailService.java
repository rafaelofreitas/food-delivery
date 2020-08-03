package br.com.fooddelivery.domain.service;

import lombok.*;

import java.util.Map;
import java.util.Set;

public interface SendingEmailService {
    void send(Message message);

    @Builder
    @Getter
    @Setter
    class Message {
        @Singular
        private Set<String> recipients;

        @NonNull
        private String subjectMatter;

        @NonNull
        private String body;

        @Singular("variable")
        private Map<String, Object> variable;
    }
}
