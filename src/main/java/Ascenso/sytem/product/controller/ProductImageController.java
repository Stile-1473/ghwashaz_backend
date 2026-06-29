package Ascenso.sytem.product.controller;

import Ascenso.sytem.common.response.ApiResponse;
import Ascenso.sytem.product.entity.Product;
import Ascenso.sytem.product.repository.ProductRepository;
import Ascenso.sytem.product.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductImageController {

    //folder where the images will be stored when uploaded
    private static final String UPLOAD_DIR = "uploads/products";
    private  final ProductValidator productValidator ;
    private  final ProductRepository productRepository;
    //Uploading a product image
    @PostMapping(value = "/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImage(@PathVariable UUID productId, @RequestParam("file") MultipartFile file) {

        //if the image is not uploaded
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(false)
                            .message("File is empty")
                            .build()
            );
        }

        //if file type is not im
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.<String>builder()
                            .success(false)
                            .message("Only image files are allowed")
                            .build()
            );
        }
        
        try {
            //Creates folder if it does not exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            //Building a unique name
            String extension = getFileExtension(contentType);
            String filename = productId + "-" + UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(filename);

            //this takes the uploaded file and copies it to the folder
            Files.copy(file.getInputStream(), filePath);

            //The image url will be
            String imageUrl = "/api/v1/products/" + productId + "/image";

            Product product = productValidator.getValidatedProduct(productId);

            product.setImageUrl(imageUrl);

            productRepository.save(product);

            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .success(true)
                    .message("Image uploaded successfully")
                    .data(imageUrl)
                    .build());
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponse.<String>builder()
                            .success(false)
                            .message("Failed to upload image: " + e.getMessage())
                            .build()
            );
        }
    }

    //Get product image
    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID productId) {

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                return ResponseEntity.notFound().build();
            }

            //searching for the file
            var files = Files.list(uploadPath)
                    .filter(f -> f.getFileName().toString().startsWith(productId.toString()))
                    .toList();

            //if file does not exist
            if (files.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            //reading file
            byte[] imageBytes = Files.readAllBytes(files.get(0));
            String contentType = getContentType(files.get(0).getFileName().toString());
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
            
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //deleting product image
    @DeleteMapping("/{productId}/image")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable UUID productId) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                return ResponseEntity.ok(ApiResponse.<Void>builder()
                        .success(true)
                        .message("No images to delete")
                        .build());
            }
            
            var files = Files.list(uploadPath)
                    .filter(f -> f.getFileName().toString().startsWith(productId.toString()))
                    .toList();
            
            for (var file : files) {
                Files.delete(file);
            }
            
            return ResponseEntity.ok(ApiResponse.<Void>builder()
                    .success(true)
                    .message("Image deleted successfully")
                    .build());
            
        } catch (IOException e) {
            return ResponseEntity.ok(ApiResponse.<Void>builder()
                    .success(false)
                    .message("Failed to delete image")
                    .build());
        }
    }

    //getting file extensions
    private String getFileExtension(String contentType) {
        return switch (contentType) {
            case "image/jpeg", "image/jpg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            case "image/gif" -> ".gif";
            default -> ".jpg";
        };
    }

    //getting content type
    private String getContentType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".webp")) return "image/webp";
        if (lower.endsWith(".gif")) return "image/gif";
        return "image/jpeg";
    }
}
