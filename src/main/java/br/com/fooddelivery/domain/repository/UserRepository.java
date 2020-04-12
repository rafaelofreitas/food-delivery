package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}