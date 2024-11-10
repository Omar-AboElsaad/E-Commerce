package org.example.ecomerce.Controller.Category;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceAlreadyExistException;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.Category;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Ctegory.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories/category")
public class CategoryController {
    private final CategoryService  categoryService;

    @GetMapping("/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
       try {
           Category  category= categoryService.getCategoryById(id);
           return ResponseEntity.ok().body(new ApiResponse("Found!",category));
       }catch (ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND)
                   .body(new ApiResponse(e.getMessage(), null));
       }

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories= categoryService.getAllCategories();
            return ResponseEntity.ok().body(new ApiResponse("Founded!",categories));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",INTERNAL_SERVER_ERROR));
        }

    }

    @GetMapping("/{categoryName}/category")
    public ResponseEntity<ApiResponse> getCategoryByCategoryName(@PathVariable String categoryName) {
        try {
            Category  category= categoryService.getCategoryByCategoryName(categoryName);
            return ResponseEntity.ok().body(new ApiResponse("Found!",category));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/add/category")
    public ResponseEntity<ApiResponse> addCategory(Category newCategory) {
        try {
            Category category=categoryService.addCategory(newCategory);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Added Successfully!",category));
        }catch (ResourceAlreadyExistException e){
          return   ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category request,@PathVariable Long id) {

        try {
            Category category=categoryService.updateCategory(request,id);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Update Success!",category));
        }catch (ResourceNotFoundException e){
            return   ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{Category_id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long Category_id) {
        try {
            categoryService.deleteCategory(Category_id);
            return ResponseEntity.ok()
                    .body(new ApiResponse("deleteed Successfully!",null));
        }catch (ResourceNotFoundException e){
            return   ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }
}
