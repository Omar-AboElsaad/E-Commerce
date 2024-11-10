package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image,Long> {
    List<Image> findByProductId(Long id);
}
