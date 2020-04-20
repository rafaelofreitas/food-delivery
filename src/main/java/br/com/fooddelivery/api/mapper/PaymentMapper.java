package br.com.fooddelivery.api.mapper;

import br.com.fooddelivery.api.model.entry.PaymentEntry;
import br.com.fooddelivery.api.model.output.PaymentOutput;
import br.com.fooddelivery.domain.model.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PaymentOutput toOutput(Payment payment) {
        return this.modelMapper.map(payment, PaymentOutput.class);
    }

    public List<PaymentOutput> toCollectionOutput(Collection<Payment> payments) {
        return payments
                .stream()
                .map(this::toOutput)
                .collect(Collectors.toList());
    }

    public Payment toDomain(PaymentEntry paymentEntry) {
        return this.modelMapper.map(paymentEntry, Payment.class);
    }

    public void copyPropertiesToDomain(PaymentEntry paymentEntry, Payment payment) {
        this.modelMapper.map(paymentEntry, payment);
    }
}
