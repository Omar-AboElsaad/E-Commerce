package org.example.ecomerce.Controller.Image;


import lombok.RequiredArgsConstructor;
import org.example.ecomerce.CustomExceptions.ResourceNotFoundException;
import org.example.ecomerce.Entity.Image;
import org.example.ecomerce.Response.ApiResponse;
import org.example.ecomerce.DTO.ImageDto;
import org.example.ecomerce.Service.Image.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
        public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files ,@RequestParam Long productId){
        try {
            List<ImageDto> imageDtos =imageService.addImages(files,productId);
            return ResponseEntity.ok(new ApiResponse("Uploaded success!", imageDtos));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Uploaded Faild!",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
      public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image=imageService.getImageById(imageId);
        ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName()+"\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
     public ResponseEntity<ApiResponse> updateImage(@RequestBody MultipartFile file,@PathVariable Long imageId) throws SQLException {
        Image image=imageService.getImageById(imageId);
        try {
            if(image!=null){
                imageService.updateImage(file,imageId);
                  return ResponseEntity.ok(new ApiResponse("Update success!",null));
            }
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

}
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update Faild!",INTERNAL_SERVER_ERROR));
     }

    @DeleteMapping("/image/{imageId}/delete")
     public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        Image image=imageService.getImageById(imageId);
         try {
             if(image!=null){
                 imageService.deleteImage(imageId);
                 return ResponseEntity.ok(new ApiResponse("Delete success!",null));
             }
         }catch (ResourceNotFoundException e){
             return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

         }
         return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                 .body(new ApiResponse("Delete Faild!",INTERNAL_SERVER_ERROR));
     }

}



