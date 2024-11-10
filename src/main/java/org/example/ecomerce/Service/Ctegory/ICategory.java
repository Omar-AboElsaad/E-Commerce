package org.example.ecomerce.Service.Ctegory;

import org.example.ecomerce.Entity.Category;
import java.util.List;


public interface ICategory {

    public Category getCategoryById(Long id);

    public List<Category> getAllCategories();

    public Category getCategoryByCategoryName(String category);


    public Category addCategory(Category Category);

    public Category updateCategory(Category request, Long id) ;

    public void deleteCategory(Long Category_id);
}
