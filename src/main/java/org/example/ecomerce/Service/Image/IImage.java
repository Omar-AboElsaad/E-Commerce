package org.example.ecomerce.Service.Image;

import org.example.ecomerce.Entity.Image;
import org.example.ecomerce.DTO.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImage {

    public Image getImageById(Long id);

    public List<ImageDto> addImages(List<MultipartFile> file, Long productId);

    public void updateImage(MultipartFile file, Long imageId) ;

    public void deleteImage(Long imageId);
}
