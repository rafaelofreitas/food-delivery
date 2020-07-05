package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.BusinessException;
import br.com.fooddelivery.domain.exception.PurchaseNotFoundException;
import br.com.fooddelivery.domain.model.Purchase;
import br.com.fooddelivery.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final RestaurantService restaurantService;
    private final CityService cityService;
    private final PaymentService paymentService;
    private final UserService userService;
    private final ProductService productService;

    public PurchaseService(
            PurchaseRepository purchaseRepository,
            RestaurantService restaurantService,
            CityService cityService,
            PaymentService paymentService,
            UserService userService,
            ProductService productService
    ) {
        this.purchaseRepository = purchaseRepository;
        this.restaurantService = restaurantService;
        this.cityService = cityService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.productService = productService;
    }

    public Purchase getByPurchaseCode(UUID purchaseCode) {
        return this.purchaseRepository
                .findByPurchaseCode(purchaseCode)
                .orElseThrow(() -> new PurchaseNotFoundException(purchaseCode));
    }

    public List<Purchase> getPurchases() {
        return this.purchaseRepository.findAll();
    }

    @Transactional
    public Purchase savePurchase(Purchase purchase) {
        this.validatePurchase(purchase);
        this.validateItems(purchase);

        purchase.setShippingFee(purchase.getRestaurant().getFreightRate());
        purchase.calculateTotalValue();

        return this.purchaseRepository.save(purchase);
    }

    private void validatePurchase(Purchase purchase) {
        var city = this.cityService.getCityById(purchase.getDeliveryAddress().getCity().getId());
        var client = this.userService.getUserById(purchase.getClient().getId());
        var restaurant = this.restaurantService.getRestaurantById(purchase.getRestaurant().getId());
        var payment = this.paymentService.getPaymentById(purchase.getPayment().getId());

        purchase.getDeliveryAddress().setCity(city);
        purchase.setClient(client);
        purchase.setRestaurant(restaurant);
        purchase.setPayment(payment);

        if (restaurant.noAcceptShapePayment(payment)) {
            throw new BusinessException(
                    String.format("Form of payment '%s' it is not accepted by this restaurant.", payment.getDescription()));
        }
    }

    private void validateItems(Purchase purchase) {
        purchase.getItems().forEach(item -> {
            var product = this.productService.getProductById(purchase.getRestaurant().getId(), item.getProduct().getId());

            item.setPurchase(purchase);
            item.setProduct(product);
            item.setUnitPrice(product.getPrice());
        });
    }
}
