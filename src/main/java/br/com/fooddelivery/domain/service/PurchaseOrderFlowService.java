package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.PurchaseNotFoundException;
import br.com.fooddelivery.domain.model.OrderStatus;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PurchaseOrderFlowService {
    private final PurchaseService purchaseService;

    public PurchaseOrderFlowService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Transactional
    public Purchase confirmedPurchase(Integer id) {
        var purchase = this.purchaseService.getById(id);

        if (!purchase.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(
                    String.format(
                            "Order status %d cannot be changed %s for %s",
                            purchase.getId(),
                            purchase.getOrderStatus().getDescription(),
                            OrderStatus.CONFIRMED.getDescription()
                    )
            );
        }

        purchase.setOrderStatus(OrderStatus.CONFIRMED);
        purchase.setConfirmationDate(OffsetDateTime.now());

        return purchase;
    }

    @Transactional
    public Purchase deliveredPurchase(Integer id) {
        var purchase = this.purchaseService.getById(id);

        if (!purchase.getOrderStatus().equals(OrderStatus.CONFIRMED)) {
            throw new BusinessException(
                    String.format(
                            "Order status %d cannot be changed %s for %s",
                            purchase.getId(),
                            purchase.getOrderStatus().getDescription(),
                            OrderStatus.DELIVERED.getDescription()
                    )
            );
        }

        purchase.setOrderStatus(OrderStatus.DELIVERED);
        purchase.setDeliveryDate(OffsetDateTime.now());

        return purchase;
    }

    @Transactional
    public Purchase canceledPurchase(Integer id) {
        var purchase = this.purchaseService.getById(id);

        if (!purchase.getOrderStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(
                    String.format(
                            "Order status %d cannot be changed %s for %s",
                            purchase.getId(),
                            purchase.getOrderStatus().getDescription(),
                            OrderStatus.CANCELED.getDescription()
                    )
            );
        }

        purchase.setOrderStatus(OrderStatus.CANCELED);
        purchase.setCancellationDate(OffsetDateTime.now());

        return purchase;
    }
}
