package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.mapper.PaymentMapper;
import br.com.fooddelivery.api.model.entry.PaymentEntry;
import br.com.fooddelivery.api.model.output.PaymentOutput;
import br.com.fooddelivery.domain.model.Payment;
import br.com.fooddelivery.domain.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;
    private PaymentMapper paymentMapper;

    @Autowired
    public PaymentController(PaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @GetMapping
    public ResponseEntity<List<PaymentOutput>> getPayments() {
        List<PaymentOutput> payments = this.paymentMapper
                .toCollectionOutput(this.paymentService.getPayments());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentOutput> getPaymentById(@PathVariable Integer id) {
        var payment = this.paymentService.getPaymentById(id);

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .ok()
                .cacheControl(cache)
                .body(this.paymentMapper.toOutput(payment));
    }

    @PostMapping
    public ResponseEntity<PaymentOutput> savePayment(@RequestBody @Valid PaymentEntry paymentEntry) {
        Payment payment = this.paymentService.savePayment(this.paymentMapper.toDomain(paymentEntry));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(payment.getId())
                .toUri();

        return ResponseEntity.created(uri).body(this.paymentMapper.toOutput(payment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentOutput> updatePayment(@PathVariable Integer id, @Valid @RequestBody PaymentEntry paymentEntry) {
        var payment = this.paymentService.getPaymentById(id);

        this.paymentMapper.copyPropertiesToDomain(paymentEntry, payment);

        payment = this.paymentService.savePayment(payment);

        return ResponseEntity.ok().body(this.paymentMapper.toOutput(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        this.paymentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

