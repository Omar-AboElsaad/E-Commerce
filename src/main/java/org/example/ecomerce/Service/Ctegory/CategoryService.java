package org.example.ecomerce.Service.Ctegory;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceAlreadyExistException;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.Category;
import org.example.ecomerce.Repository.CategoryRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@RequiredArgsConstructor
@Service
public class CategoryService implements ICategory{
    private final CategoryRepo categoryRepo;

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories= categoryRepo.findAll();

        return categories;
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Category getCategoryByCategoryName(String category) {
        return categoryRepo.findCategoryByName(category);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Category addCategory(Category category) {
        if(categoryRepo.existsByName(category.getName())){
         throw new ResourceAlreadyExistException("Category Already Exist");
        }
        return categoryRepo.save(category);

/**
 * this is another approach to add Category with using Lambda Expression Approach
 */
//        Optional.of(category)
//                .filter(c ->!categoryRepo.existsByName(c.getName()) )
//                .map(categoryRepo::save)
//                .orElseThrow(() -> new CategoryAlreadyExistException("Category Exist!"));

    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Category updateCategory(Category request,Long id) {
       return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory ->
                {
                    oldCategory.setName(request.getName());
                    return categoryRepo.save(oldCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));

    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void deleteCategory(Long Category_id) {
         categoryRepo.findById(Category_id)
                .ifPresentOrElse(categoryRepo::delete,
                        () -> {
                            throw new ResourceNotFoundException("Category Not Found!");
                });
             }
}
