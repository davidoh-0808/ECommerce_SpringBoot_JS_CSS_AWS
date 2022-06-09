package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Category;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductSearch;
import com.application.gentlegourmet.entity.ProductTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductTagService productTagService;
    private final HashtagService hashtagService;


    ////////////////////////////////////////////////////////////////////////////////////////////


    public List<Product> findProductsByCategoryAndKeyword(ProductSearch productSearch) {
        String categoryParam = productSearch.getCategory();
        String keyword = productSearch.getKeyword();
        //update hashtag on search
        hashtagService.updateHashtagOnSearch(keyword);

        Set<Product> products = null;
        List<Product> resultProductList = new ArrayList<>();

        //match the param category with DB categoryId
        Long categoryId = 0L;
        switch(categoryParam) {
            case("meat"):
                categoryId = 1L;
                break;
            case("dairy"):
                categoryId = 2L;
                break;
            case("snack"):
                categoryId = 3L;
                break;
            case("condiment"):
                categoryId = 4L;
                break;
            default:
                break;
        }

        //get all the products filtered with the category
        if(categoryId == 0L) {
            products = productService.findAllProducts();
        } else {
            Category category = categoryService.findCategoryById(categoryId);
            products = productService.findAllProductsByCategory(category);
        }

        /* use compare the keyword
           first, against the product name and then against its product_tag(s)
           if it matches, add the product to the resultProductList
         */
        for(Product p : products) {
            if(p.getName().contains(keyword)) {
                resultProductList.add(p);
                continue;
            }

            Set<ProductTag> productTagSet = productTagService.findProductTagsByProduct(p);
            for(ProductTag pt : productTagSet) {
                if(pt.getTag().contains(keyword)) {
                    resultProductList.add(p);
                    break;
                }
            }
        }

        return resultProductList;
    }


    public List<Product> findProductsByHashtag(String hashtag) {
        //update hashtag on search
        hashtagService.updateHashtagOnSearch(hashtag);

        List<Product> resultProductList = new ArrayList<>();

        Set<Product> products = productService.findAllProducts();

        //compare against the hashtag and add to resultProductList
        for(Product p : products) {
            if(p.getName().contains(hashtag)) {
                resultProductList.add(p);
                continue;
            }

            Set<ProductTag> productTagSet = productTagService.findProductTagsByProduct(p);
            for(ProductTag pt : productTagSet) {
                if(pt.getTag().contains(hashtag)) {
                    resultProductList.add(p);
                    break;
                }
            }
        }

        return resultProductList;
    }

}
