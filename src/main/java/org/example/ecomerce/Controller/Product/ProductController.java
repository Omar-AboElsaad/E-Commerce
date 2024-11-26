package org.example.ecomerce.Controller.Product;
import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceAlreadyExistException;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.ProductDto;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Requests.Product.AddProductRequest;
import org.example.ecomerce.Requests.Product.UpdateProductRequest;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.Service.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController{
    private final ProductService productService;

//-------------------------------------------------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product= productService.getProductById(id);
            ProductDto convertedProduct=productService.convertToDTO(product);
            return ResponseEntity.ok().body(new ApiResponse("Found!",convertedProduct));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products= productService.getAllProducts();
            List<ProductDto> convertedProducts=productService.convertedListProducts(products);
            return ResponseEntity.ok().body(new ApiResponse("All Product Get Successfully!",convertedProducts));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/productbycategory")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@RequestParam String categoryName) {
        try {
            List<Product>  products= productService.getProductByCategoryName(categoryName);
            List<ProductDto> convertedProducts=productService.convertedListProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("There is no Product with Category "+categoryName, null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Found!",convertedProducts));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

//-------------------------------------------------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/productbybrand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName) {
        try {
            List<Product>  products= productService.getProductByBrand(brandName);
            List<ProductDto> convertedProducts=productService.convertedListProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("There is no Product with Brand "+brandName, null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Found!",convertedProducts));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/getby/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brand) {
        try {
            List<Product> products =productService.getProductByCategoryAndBrand(category,brand);
            List<ProductDto> convertedProducts=productService.convertedListProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("There is no Product with Category "+category+" and Brand "+brand, null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Found!", convertedProducts));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/get-by-name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name) {
        try {
            Product products= productService.getProductByName(name);
            ProductDto convertedProducts=productService.convertToDTO(products);
            return ResponseEntity.ok().body(new ApiResponse("Found!",convertedProducts));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/getby/name-and-brand")
    public ResponseEntity<ApiResponse> getProductByNameAndBrand(@RequestParam String name,@RequestParam String brand) {

        try {
            List<Product> products =productService.getProductByNameAndBrand(name,brand);
            List<ProductDto> convertedProducts=productService.convertedListProducts(products);

            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("There is no Product with Category "+name+" and Brand "+brand, null));
            }
            return ResponseEntity.ok().body(new ApiResponse("Found!", convertedProducts));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
               var count= productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok().body(new ApiResponse("Found!", count));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

//-------------------------------------------------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest newProduct) {
      try {
          Product product= productService.addProduct(newProduct);
         return ResponseEntity.ok().body(new ApiResponse("Add product success!",product));
      }catch (ResourceAlreadyExistException e){
        return    ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
      }

    }

//-------------------------------------------------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        try {
            Product product= productService.updateProduct(request);
            return ResponseEntity.ok().body(new ApiResponse("Product Updated successfully!",product));
        }catch (ResourceNotFoundException e){
            return    ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

//-------------------------------------------------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body(new ApiResponse("Product Deleted Successfully!",id));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
