package br.com.fooddelivery.api.controller;

import br.com.fooddelivery.api.dto.entry.PaymentEntry;
import br.com.fooddelivery.api.dto.output.PaymentOutput;
import br.com.fooddelivery.api.mapper.PaymentMapper;
import br.com.fooddelivery.domain.model.Payment;
import br.com.fooddelivery.domain.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
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
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    public PaymentController(PaymentService paymentService, PaymentMapper paymentMapper) {
        this.paymentService = paymentService;
        this.paymentMapper = paymentMapper;
    }

    @GetMapping
    public ResponseEntity<Page<PaymentOutput>> getPayments(@PageableDefault Pageable pageable) {
        Page<Payment> paymentPage = this.paymentService.getPayments(pageable);

        List<PaymentOutput> paymentOutputs = this.paymentMapper.toCollectionOutput(paymentPage.getContent());

        Page<PaymentOutput> paymentOutputPage = new PageImpl<>(paymentOutputs, pageable, paymentPage.getTotalElements());

        CacheControl cache = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.ok().cacheControl(cache).body(paymentOutputPage);
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
    public ResponseEntity<PaymentOutput> updatePayment(
            @PathVariable Integer id,
            @Valid @RequestBody PaymentEntry paymentEntry
    ) {
        var payment = this.paymentService.getPaymentById(id);

        this.paymentMapper.copyPropertiesToDomain(paymentEntry, payment);

        payment = this.paymentService.savePayment(payment);

        return ResponseEntity.ok().body(this.paymentMapper.toOutput(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer id) {
        this.paymentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}