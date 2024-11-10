package org.example.ecomerce.Service.Product;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.DTO.ImageDto;
import org.example.ecomerce.DTO.ProductDto;
import org.example.ecomerce.Entity.Category;
import org.example.ecomerce.Entity.Image;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Repository.CategoryRepo;
import org.example.ecomerce.Repository.ImageRepo;
import org.example.ecomerce.Repository.ProductRepo;
import org.example.ecomerce.Requests.Product.AddProductRequest;
import org.example.ecomerce.Requests.Product.UpdateProductRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private final ImageRepo imageRepo;

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Product> getProductByCategoryName(String category) {
        return productRepo.getProductByCategoryName(category);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepo.getProductByBrand(brand);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepo.getProductByCategoryNameAndBrand(category,brand);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Product> getProductByName(String name) {
        return productRepo.getProductByName(name);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<Product> getProductByNameAndBrand(String name, String brand) {
        return productRepo.getProductByNameAndBrand(name,brand);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepo.countByBrandAndName(brand,name);
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category=createOrGetCategory(request.getCategory().getName());
       return productRepo.save(createProduct(request,category));
    }

//----------------------------------------------------------------------------------------------------------------------

    private Category createOrGetCategory(String categoryName){
        return Optional.ofNullable(categoryRepo.findCategoryByName(categoryName))
                .orElseGet(()-> categoryRepo.save(new Category(categoryName)));
    }

//----------------------------------------------------------------------------------------------------------------------

    private Product createProduct(AddProductRequest request, Category category){
      return new Product(
                request.getName(),
                request.getBrand(),
                request.getStock(),
                request.getDescription(),
                request.getPrice(),
                category

        );
    }
//----------------------------------------------------------------------------------------------------------------------

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){

              existingProduct.setName(request.getName());
              existingProduct.setBrand( request.getBrand());
              existingProduct.setStock(request.getStock());
              existingProduct.setDescription(request.getDescription());
              existingProduct.setPrice(request.getPrice());

              Category category=categoryRepo.findCategoryByName(request.getCategory().getName());
              existingProduct.setCategory(category);

              return existingProduct;

    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Product updateProduct(UpdateProductRequest request) {
      return productRepo.findById(request.getId())
              .map(existingProduct->updateExistingProduct(existingProduct,request))
              .map(productRepo::save)
              .orElseThrow(() -> new ResourceNotFoundException("Product with Id "+ request.getId() +"Is not avilable"));

    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void deleteProduct(Long Product_id) {
        productRepo.findById(Product_id).ifPresentOrElse(productRepo::delete,() -> {
            throw new ResourceNotFoundException("Product Not Found!");
        });
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found!"));
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public List<ProductDto> convertedListProducts(List<Product>products){
        return products.stream().map(this::convertToDTO).toList();
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public ProductDto convertToDTO(Product product){
        ProductDto productDTO = modelMapper.map(product, ProductDto.class);
        List<Image> images=imageRepo.findByProductId(product.getId());

        List<ImageDto> imageDtos =images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class)).toList();

        productDTO.setImages(imageDtos);
        return productDTO;
    }

}


