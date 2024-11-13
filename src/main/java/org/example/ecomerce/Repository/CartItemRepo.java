package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    void deleteAllByCart_id(Long cartId);

   Optional<CartItem> findByProductIdAndCartId(Long productId, Long cartId );

    List<CartItem> findAllByCart_id(Long cartId);
}
