package br.com.fooddelivery.domain.service;

import br.com.fooddelivery.domain.exception.EntityInUseException;
import br.com.fooddelivery.domain.exception.ProductNotFoundException;
import br.com.fooddelivery.domain.model.Product;
import br.com.fooddelivery.domain.model.Restaurant;
import br.com.fooddelivery.domain.repository.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByRestaurantActive(Restaurant restaurant) {
        return this.productRepository.findActivesByRestaurant(restaurant);
    }

    public List<Product> getAllProductsByRestaurant(Restaurant restaurant) {
        return this.productRepository.findByRestaurant(restaurant);
    }

    public Product getProductById(Integer restaurantId, Integer productId) {
        return this.productRepository
                .findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Transactional
    public void deleteById(Integer id) {
        try {
            this.productRepository.deleteById(id);
            this.productRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format("Product cannot be removed as it is in use: %s", id));
        }
    }
}
