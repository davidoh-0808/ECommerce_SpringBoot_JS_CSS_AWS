package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 ** applied EntityGraph on Repository Layer, rather than on this service layer **
*/
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /* @ PersistenceContext
    private EntityManager em; */


    //////////////////////////////////////////////////////////////////////


    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }


    public Product findProductById(Long id) {
        /* ** apply EntityGraph on Repository Layer instead **
        System.out.println("********************* EntityManager em -> " + em);
        EntityGraph<?> entityGraph = em.createEntityGraph("product-graph.category");
        System.out.println("********************* entityGraph -> " + entityGraph);
        Product product = null;

        return product;
        */

        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product is found by "+id));
    }



}
