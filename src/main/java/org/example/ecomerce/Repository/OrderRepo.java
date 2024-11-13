package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Orders,Long> {
    List<Orders> findAllByUserId(Long userId);

    @Query("Select o FROM Orders o WHERE o.user.id = :userId AND o.id = :orderId")
    Optional<Orders> findByUserIdAndOrderId(@Param("userId") Long userId, @Param("orderId") Long orderId);

    void deleteAllByUserId(Long userId);
}
