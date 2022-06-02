package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Brand;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;


    public Brand findBrandById(Long brandId) {
        return brandRepository.findById(brandId).get();
    }

    public Set<Product> findProductsByBrandId(Long brandId) {
        Brand brand = brandRepository.findById(brandId).get();

        return brand.getProducts();
    }

}

