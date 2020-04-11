package br.com.fooddelivery.domain.repository;

import br.com.fooddelivery.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}