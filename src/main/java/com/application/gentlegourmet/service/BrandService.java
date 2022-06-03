package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.*;
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
    private final ProductReviewService productReviewService;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////


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
    public List<Product> findProductsByBrandIdSortBySaleQuantity(Long brandId) {
        Brand brand = brandRepository.findProductsByBrandId(brandId);
        Set<Product> productSet = brand.getProducts();
        List<Product> productList = new ArrayList<>(productSet);

        int listSize = productList.size();
        for(int i=0; i<listSize-1; i++) {
            for(int curr=i+1; curr<listSize; curr++) {
                Product prevProduct = productList.get(i);
                Product currProduct = productList.get(curr);
                int prevQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct( prevProduct );
                int currQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct( currProduct );

                //swap
                if(prevQuantitySum < currQuantitySum) {
                    Product temp = prevProduct;
                    productList.set(i, currProduct);
                    productList.set(curr, temp);
                }
            }
            //review sorting process
            System.out.println("********* productListByPrice after cycle i = " + i);
            System.out.println("productListPrice[0] : " + productList.get(0).getName());
            System.out.println("productListPrice[1] : " + productList.get(1).getName());
            System.out.println("productListPrice[2] : " + productList.get(2).getName());
            System.out.println();
        }

        //attach image thumbnail to each product before return
        return attachProductImages(productList);
    }

    //apply bubble sorting method (lowest price first)
    public List<Product> findProductsByBrandIdSortByPrice(Long brandId) {
        Brand brand = brandRepository.findProductsByBrandId(brandId);
        Set<Product> productSet = brand.getProducts();
        List<Product> productList = new ArrayList<>(productSet);

        int listSize = productList.size();
        for(int i=0; i<listSize-1; i++) {
            for(int curr=i+1; curr<listSize; curr++) {
                Product prevProduct = productList.get(i);
                Product currProduct = productList.get(curr);
                int prevPrice = prevProduct.getPrice();
                int currPrice = currProduct.getPrice();

                //swap
                if(prevPrice > currPrice) {
                    Product temp = prevProduct;
                    productList.set(i, currProduct);
                    productList.set(curr, temp);
                }
            }
        }

        //attach image thumbnail to each product before return
        return attachProductImages(productList);
    }


    //apply bubble sorting method(highest rating first)
    public List<Product> findProductsByBrandIdSortByRating(Long brandId) {
        Brand brand = brandRepository.findProductsByBrandId(brandId);
        Set<Product> productSet = brand.getProducts();
        List<Product> productList = new ArrayList<>(productSet);

        int listSize = productList.size();
        for(int i=0; i<listSize-1; i++) {
            for (int curr = i + 1; curr < listSize; curr++) {
                Product prevProduct = productList.get(i);
                Product currProduct = productList.get(curr);
                int prevRatingSum = productReviewService.findProductRatingSumByProduct(prevProduct);
                int currRatingSum = productReviewService.findProductRatingSumByProduct(currProduct);

                //swap
                if (prevRatingSum < currRatingSum) {
                    Product temp = prevProduct;
                    productList.set(i, currProduct);
                    productList.set(curr, temp);
                }
            }
        }

        return attachProductImages(productList);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    private List<Product> attachProductImages(List<Product> productList) {
        //attach image thumbnail to each product
        for(Product p : productList) {
            List<ProductImage> productImageList = productImageService.findImagesByProduct(p);
            String productThumbnailPath = productImageList.get(0).getPath();

            p.setProductThumbnailPath(productThumbnailPath);
            p.setCategory(p.getCategory()); //fetch category manually (due to lazy mode)
        }

        return productList;
    }


}

