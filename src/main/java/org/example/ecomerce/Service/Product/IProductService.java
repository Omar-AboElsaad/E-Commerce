package org.example.ecomerce.Service.Product;

import org.example.ecomerce.DTO.ProductDto;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Requests.Product.AddProductRequest;
import org.example.ecomerce.Requests.Product.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    public Product getProductById(Long id);

    public List<Product> getAllProducts();

    public List<Product> getProductByCategoryName(String category);

    public List<Product> getProductByBrand(String brand);

    public List<Product> getProductByCategoryAndBrand(String category,String brand);

    public List<Product> getProductByName(String name);

    public List<Product> getProductByNameAndBrand(String name,String brand);

    public Long countProductsByBrandAndName(String brand,String name);

    public Product addProduct(AddProductRequest product);

    public Product updateProduct(UpdateProductRequest request) ;

    public void deleteProduct(Long Product_id);


    List<ProductDto> convertedListProducts(List<Product> products);

    ProductDto convertToDTO(Product product);
}
