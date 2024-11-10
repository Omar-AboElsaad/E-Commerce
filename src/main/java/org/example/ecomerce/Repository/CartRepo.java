package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {


    Optional<Cart> findByUserId(Long userId);
}
