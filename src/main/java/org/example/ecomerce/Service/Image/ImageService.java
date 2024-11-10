package org.example.ecomerce.Service.Image;

import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.Image;
import org.example.ecomerce.Entity.Product;
import org.example.ecomerce.Repository.ImageRepo;
import org.example.ecomerce.Repository.ProductRepo;
import org.example.ecomerce.DTO.ImageDto;
import org.example.ecomerce.Service.Product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ImageService implements IImage {
    private final ImageRepo imageRepo;
    private final ProductRepo productRepo;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image Not Found!"));
    }

    @Override
    public List<ImageDto> addImages(List<MultipartFile> files, Long productId) {

        Product product=productService.getProductById(productId);

         List<ImageDto> savedImagesDTO=new ArrayList<>();
         String buildDownloadUrl="/api/v1/images/image/download/";
         for (MultipartFile file:files){
             try {
                 Image image=new Image();
                 image.setFileName(file.getOriginalFilename());
                 image.setFileType(file.getContentType());

                 //here we refactor image bytes to blob(Binary Large Object)
                 //this binary object is what we save in database
                 image.setImage(new SerialBlob(file.getBytes()));

                 image.setProduct(product);


                 //initial save for image to generate id
                 Image savedImage=imageRepo.save(image);

                 //add new generated Id to download Url and put it to saved image
                 savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());

                 //final save image including the id and DownloadUrl
                 //this will update the current image with new one with correct Download url
                 imageRepo.save(savedImage);

                 //this Image DTO is what user will see as return
                 //user mustn't see all image details so ,
                 //we create it to customize what user can see
                 ImageDto imageDTO=new ImageDto();
                 imageDTO.setId(savedImage.getId());
                 imageDTO.setFileName(savedImage.getFileName());
                 imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
                 savedImagesDTO.add(imageDTO);

             }catch (IOException | SQLException e) {
                 throw new RuntimeException(e.getMessage());

             }

         }
        //this is Array list of Image DTO that will appear to user with custom output
        //not all image details will appear
        return savedImagesDTO;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepo.save(image);

        }catch (IOException|SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteImage(Long imageId) {
//       if(imageRepo.existsById(imageId)){
//           imageRepo.deleteById(imageId);
//       }else throw new ResourceNotFoundException("Image Not Fount!");
//    }
        imageRepo.findById(imageId)
                .ifPresentOrElse(imageRepo::delete,
                () -> {
                    throw new ResourceNotFoundException("Image Not Found!");
                });
    }
}
