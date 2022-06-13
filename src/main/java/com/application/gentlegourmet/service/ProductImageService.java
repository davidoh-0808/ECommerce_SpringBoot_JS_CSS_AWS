package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductImage;
import com.application.gentlegourmet.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;


    /////////////////////////////////////////////////////////////////////////////


    public List<ProductImage> findImagesByProduct(Product product) {
        return productImageRepository.findImagesByProduct(product);
    }


    public void uploadImagesByProduct(Product uploadedProduct) {
        Long productId = uploadedProduct.getId();
        List<MultipartFile> images = uploadedProduct.getMultipartFiles();

        //save each image path
        for(MultipartFile img : images) {
            String imageFilename = img.getOriginalFilename();
            ProductImage newProductImage =

            productImageRepository.save(newProductImage);
        }

    }

}
