package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepo extends JpaRepository<Product,Long> {
     List<Product> getProductByCategoryName(String category);
     List<Product> getProductByBrand(String brand);
     List<Product> getProductByCategoryNameAndBrand(String category,String brand);
     Optional<Product> getProductByName(String name);
     List<Product> getProductByNameAndBrand(String name,String brand);
     Long countByBrandAndName(String brand, String name);

     boolean existsByNameAndBrand(String name, String brand);
}
