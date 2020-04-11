package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.PaymentNotFoundException;
import br.com.fooddelivery.domain.model.Payment;
import br.com.fooddelivery.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getPayments() {
        return this.paymentRepository.findAll();
    }

    public Payment getPaymentById(Integer id) {
        return this.paymentRepository
                .findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Transactional
    public Payment savePayment(Payment payment) {
        return this.paymentRepository.save(payment);
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.paymentRepository.deleteById(id);
            this.paymentRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Payment cannot be removed as it is in use: %s", id));
        }
    }
}
