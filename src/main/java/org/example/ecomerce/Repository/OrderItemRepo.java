package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.OrderItem;
import org.example.ecomerce.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
//    List<OrderItem> getAllByOrdersId(Long orderId);

//    Optional<OrderItem> findByUserId(Long userId);


}
