package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Brand;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductImage;
import com.application.gentlegourmet.entity.PurchaseDetail;
import com.application.gentlegourmet.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductImageService productImageService;
    private final PurchaseDetailService purchaseDetailService;


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

    //apply bubble sorting method
    public List<Product> findProductsByBrandIdSortByPrice(Long brandId) {
        Brand brand = brandRepository.findProductsByBrandId(brandId);
        Set<Product> productSet = brand.getProducts();
        List<Product> productListByPrice = new ArrayList<>(productSet);

        int listSize = productListByPrice.size();
        for(int i=0; i<listSize-1; i++) {
            System.out.println("hello??????????????????????????????");

            for(int curr=i+1; curr<listSize; curr++) {
                System.out.println("anybody there??????????????????????????????");
                Product prevProduct = productListByPrice.get(i);
                Product currProduct = productListByPrice.get(curr);
                int prevQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct( prevProduct );
                int currQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct( currProduct );

                //swap
                System.out.println("********* prevQuantitySum : " + prevQuantitySum);
                System.out.println("********* currQuantitySum : " + currQuantitySum);
                if(prevQuantitySum < currQuantitySum) {
                    Product temp = prevProduct;
                    productListByPrice.set( i, currProduct);
                    productListByPrice.set( curr, temp);
                }
            }

            //review sorting process
            System.out.println("********* productListByPrice after cycle i = " + i);
            System.out.println("productListPrice[0] : " + productListByPrice.get(0).getName());
            System.out.println("productListPrice[1] : " + productListByPrice.get(1).getName());
            System.out.println("productListPrice[2] : " + productListByPrice.get(2).getName());
            System.out.println();

        }

        System.out.println("*********** sorted productListByPrice : " + productListByPrice);

        return productListByPrice;
    }






}

