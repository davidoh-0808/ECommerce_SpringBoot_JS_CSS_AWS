package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductTag;
import com.application.gentlegourmet.repository.ProductTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductTagService {

    private final ProductTagRepository productTagRepository;


    /////////////////////////////////////////////////////////////////////////////////////


    public Set<ProductTag> findProductTagsByProduct(Product product) {

        return productTagRepository.findProductTagsByProduct(product);
    }


}
