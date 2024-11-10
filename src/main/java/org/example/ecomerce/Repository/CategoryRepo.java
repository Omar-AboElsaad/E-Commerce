package org.example.ecomerce.Repository;

import org.example.ecomerce.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    Category findCategoryByName(String category);

    boolean existsByName(String name);
}
