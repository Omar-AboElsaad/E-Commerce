package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByemail(String email);

    boolean existsByemail(String email);
}
