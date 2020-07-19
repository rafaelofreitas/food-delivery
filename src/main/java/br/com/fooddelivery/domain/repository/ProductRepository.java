package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.Product;
import br.com.fooddelivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoryQueries {
    @Query("from Product where restaurant.id = :restaurant and id = :product")
    Optional<Product> findById(@Param("restaurant") Integer restaurantId, @Param("product") Integer productId);

    List<Product> findByRestaurant(Restaurant restaurant);

    @Query("from Product p where p.active = true and p.restaurant = :restaurant")
    List<Product> findActivesByRestaurant(Restaurant restaurant);
}
