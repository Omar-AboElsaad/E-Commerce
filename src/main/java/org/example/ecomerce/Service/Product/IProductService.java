package org.example.ecomerce.Service.Product;

import org.example.ecomerce.DTO.ProductDto;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Requests.Product.AddProductRequest;
import org.example.ecomerce.Requests.Product.UpdateProductRequest;

import java.util.List;

 public interface IProductService {

     Product getProductById(Long id);

     List<Product> getAllProducts();

     List<Product> getProductByCategoryName(String category);

     List<Product> getProductByBrand(String brand);

     List<Product> getProductByCategoryAndBrand(String category,String brand);

     Product getProductByName(String name);

     List<Product> getProductByNameAndBrand(String name,String brand);

     Long countProductsByBrandAndName(String brand,String name);

     Product addProduct(AddProductRequest product);

     Product updateProduct(UpdateProductRequest request) ;

     void deleteProduct(Long Product_id);


    List<ProductDto> convertedListProducts(List<Product> products);

    ProductDto convertToDTO(Product product);
}
