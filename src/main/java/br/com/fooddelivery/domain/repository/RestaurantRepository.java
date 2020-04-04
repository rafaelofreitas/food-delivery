package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("from Restaurant restaurant join restaurant.kitchen left join fetch restaurant.payment")
    List<Restaurant> findAll();
}
