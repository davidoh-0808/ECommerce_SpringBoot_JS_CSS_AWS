package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Brand;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductImage;
import com.application.gentlegourmet.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductImageService productImageService;


    public Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId).get();
    }

    public Set<Product> findProductsByBrandId(Long brandId) {
        Brand brand = brandRepository.findProductsByBrandId(brandId);
        Set<Product> productSet = brand.getProducts();

        for(Product p : productSet) {
            List<ProductImage> productImageList = productImageService.findImagesByProduct(p);
            String productThumbnailPath = productImageList.get(0).getPath();

            p.setProductThumbnailPath(productThumbnailPath);
            p.setCategory(p.getCategory()); //fetch category manually (due to lazy mode)
        }

        return productSet;
    }

}

