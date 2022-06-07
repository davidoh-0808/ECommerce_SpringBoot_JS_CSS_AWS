package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 ** applied EntityGraph on Repository Layer, rather than on this service layer **
*/
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final PurchaseDetailService purchaseDetailService;
    private final ProductReviewService productReviewService;


    //////////////////////////////////////////////////////////////////////////////////////////////


    public List<Product> findTopFiveBestsellingProducts() {
        Set<Product> productSet = productRepository.findAllProductsAndCategory();

        //prep the Product Set for merge sort method
        List<Product> productList = new ArrayList<>(productSet);
        Object[] object = productList.toArray();
        Product[] productArray = Arrays.copyOf(object, object.length, Product[].class);

        //refer to private merge sorting methods below
        bestsellerMergeSort(productArray, productArray.length);

        //convert the merge sorted array back into ArrayList / retain only the top 5 results
        List<Product> topFiveProductList = Arrays.asList(productArray).subList(0,5);

        //attach product_image to each product entity before return
        return attachProductImages(topFiveProductList);
    }


    public List<Product> findTopFiveRecommendedProducts() {
        Set<Product> productSet = productRepository.findAllProductsAndCategory();

        List<Product> productList = new ArrayList<>(productSet);
        Object[] object = productList.toArray();
        Product[] productArray = Arrays.copyOf(object, object.length, Product[].class);

        recommendationMergeSort(productArray, productArray.length);

        List<Product> topFiveProductList = Arrays.asList(productArray).subList(0,5);

        return attachProductImages(topFiveProductList);
    }


    public Product findProductById(Long id) {
        /* ** apply EntityGraph on Repository Layer instead **
        System.out.println("********************* EntityManager em -> " + em);
        EntityGraph<?> entityGraph = em.createEntityGraph("product-graph.category");
        System.out.println("********************* entityGraph -> " + entityGraph);
        Product product = null;

        return product;
        */

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product is found by "+id));

        Set<ProductReview> productReviews = product.getProductReviews();
        Set<ProductReview> productReviewPrepped = productReviewService.attachProductReviewWriters(productReviews);
        product.setProductReviews(productReviewPrepped);

        //also generate ratingMap for view
        Map<String, Integer> ratingMap = makeRatingMap(productReviews);
        product.setRatingMap(ratingMap);

        //prep product description for view
        return convertProductDescriptionFormat(product);
    }


    //////////////////////////////////////// Private Methods /////////////////////////////////////


    //bestseller merge-sort 1)
    private void bestsellerMergeSort(Product[] a, int n) {
        if (n < 2) {
            return;
        }
        //(1) prep two separate arrays from Product ArrayList (divide and conquer)
        int mid = n / 2;
        Product[] l = new Product[mid];
        Product[] r = new Product[n - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        bestsellerMergeSort(l, mid);
        bestsellerMergeSort(r, n - mid);

        //(2) finally send the prepped arrays to merge() sorter method
        bestsellerMerge(a, l, r, mid, n - mid);
    }

    //bestseller merge-sort 2)
    private void bestsellerMerge(Product[] a, Product[] l, Product[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            Product lProduct = l[i];
            Product rProduct = r[j];
            int lSalesSum = purchaseDetailService.findSaleQuantitySumByProduct(lProduct);
            int rSalesSum = purchaseDetailService.findSaleQuantitySumByProduct(rProduct);

            //(3) compare and swap based on the sum of sales quantity of the products
            if (lSalesSum <= rSalesSum) {
                a[k++] = r[j++];
            }
            else {
                a[k++] = l[i++];
            }
        }

        //(4) finish the array by filling in the un-swapped parts of left/right arrays
        while (j < right) {
            a[k++] = r[j++];
        }
        while (i < left) {
            a[k++] = l[i++];
        }
    }


    //recommendation merge-sort
    private void recommendationMergeSort(Product[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Product[] l = new Product[mid];
        Product[] r = new Product[n - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        recommendationMergeSort(l, mid);
        recommendationMergeSort(r, n - mid);

        recommendationMerge(a, l, r, mid, n - mid);
    }

    private void recommendationMerge(Product[] a, Product[] l, Product[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            Product lProduct = l[i];
            Product rProduct = r[j];
            int lRatingSum = productReviewService.findProductRatingSumByProduct(lProduct);
            int rRatingSum = productReviewService.findProductRatingSumByProduct(rProduct);

            if (lRatingSum <= rRatingSum) {
                a[k++] = r[j++];
            }
            else {
                a[k++] = l[i++];
            }
        }
        while (j < right) {
            a[k++] = r[j++];
        }
        while (i < left) {
            a[k++] = l[i++];
        }
    }


    //attach product_image to each product entity
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


    private Product attachProductImage(Product product) {
        List<ProductImage> productImageList = productImageService.findImagesByProduct(product);
        String productThumbnailPath = productImageList.get(0).getPath();

        product.setProductThumbnailPath(productThumbnailPath);

        return product;
    }


    private Product convertProductDescriptionFormat(Product product) {
        String description = product.getDescription();
        List<String> descriptionList = Arrays.asList( description.split("/") );
        product.setProductDescriptions(descriptionList);

        return product;
    }


    private Map<String, Integer> makeRatingMap(Set<ProductReview> productReviews) {
        Map<String, Integer> ratingMap = new HashMap<>();

        int ratingOne = 0;
        int ratingTwo = 0;
        int ratingThree = 0;
        int ratingFour = 0;
        int ratingFive = 0;
        ratingMap.put("ratingOne", ratingOne);
        ratingMap.put("ratingTwo", ratingTwo);
        ratingMap.put("ratingThree", ratingThree);
        ratingMap.put("ratingFour", ratingFour);
        ratingMap.put("ratingFive", ratingFive);

        for(ProductReview pr : productReviews) {
            int rating = pr.getRating();
            switch(rating) {
                case 1: ratingMap.put("ratingOne", ++ratingOne); break;
                case 2: ratingMap.put("ratingTwo", ++ratingTwo); break;
                case 3: ratingMap.put("ratingThree", ++ratingThree); break;
                case 4: ratingMap.put("ratingFour", ++ratingFour); break;
                case 5: ratingMap.put("ratingFive", ++ratingFive); break;
                default:
                    ratingMap.put("huh?", 1);
                    break;
            }
        }

        return ratingMap;
    }


}
