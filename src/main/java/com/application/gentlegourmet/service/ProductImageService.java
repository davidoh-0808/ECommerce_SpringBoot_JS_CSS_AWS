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
    private final S3Service s3Service;

    private final String S3_PRODUCT_BUCKET_PATH = "https://s3-ap-northeast-2.amazonaws.com/gentle-gourmet/product/";

    /////////////////////////////////////////////////////////////////////////////


    public List<ProductImage> findImagesByProduct(Product product) {
        return productImageRepository.findImagesByProduct(product);
    }


    public void uploadImagesByProduct(Product uploadedProduct) {
        //get the multipart files from uploaded Product DTO from view
        Long productId = uploadedProduct.getId();
        List<MultipartFile> images = uploadedProduct.getMultipartFiles();


        for(MultipartFile img : images) {
            //save each image path as a S3 bucket path (DB insertion)
            String imgName = img.getOriginalFilename();
            ProductImage newProductImage = new ProductImage();
            newProductImage.setProduct(uploadedProduct);
            newProductImage.setPath(S3_PRODUCT_BUCKET_PATH + imgName);

            productImageRepository.save(newProductImage);


            //then actually save the multipart files in S3 using S3 service
            s3Service.uploadProductImage(img);
        }





    }

}
