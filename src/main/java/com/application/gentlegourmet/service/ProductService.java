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


    ///////////////////////////////////////// Public Methods //////////////////////////////////////


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


    public List<ProductSalesInfo> findAllProductsAndSalesAmountRanked() {
        List<ProductSalesInfo> rankedSalesInfoList = new ArrayList<>();
        Set<Product> productSet = productRepository.findAllProductsAndCategory();

        //prep the Product Set for merge sort method
        List<Product> productList = new ArrayList<>(productSet);
        Object[] object = productList.toArray();
        Product[] productArray = Arrays.copyOf(object, object.length, Product[].class);

        //refer to private merge sorting methods below -> sorts by product's sales amount rank
        salesAmountMergeSort(productArray, productArray.length);

        //convert the merge sorted array back into ArrayList
        List<Product> rankedProductList = Arrays.asList(productArray);

        //for each product, package productName and its total sales amount as a map
        for(int i=0; i<rankedProductList.size(); i++) {
            Product p = rankedProductList.get(i);
            int pSalesQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct(p);
            int pSalesAmount = p.getPrice() * pSalesQuantitySum;

            ProductSalesInfo newProductSalesInfo = new ProductSalesInfo(p.getName(), pSalesAmount);
            rankedSalesInfoList.add(newProductSalesInfo);
        }

        return rankedSalesInfoList;
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


    public Set<Product> findAllProducts() {
        Set<Product> productSet = productRepository.findAllProductsAndCategory();

        return attachProductImages(productSet);
    }


    public Set<Product> findAllProductsByCategory(Category category) {
        Set<Product> productSet = productRepository.findAllProductsByCategory(category);

        return attachProductImages(productSet);
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


    //bestsellerBySalesAmount merge sort 1)
    private void salesAmountMergeSort(Product[] a, int n) {
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
        salesAmountMergeSort(l, mid);
        salesAmountMergeSort(r, n - mid);

        //(2) finally send the prepped arrays to merge() sorter method
        salesAmountMerge(a, l, r, mid, n - mid);
    }

    //bestsellerBySalesAmount merge sort 2)
    private void salesAmountMerge(Product[] a, Product[] l, Product[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            Product lProduct = l[i];
            Product rProduct = r[j];

            int lSalesQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct(lProduct);
            int lSalesAmount = lProduct.getPrice() * lSalesQuantitySum;

            int rSalesQuantitySum = purchaseDetailService.findSaleQuantitySumByProduct(rProduct);
            int rSalesAmount = rProduct.getPrice() * rSalesQuantitySum;

            //(3) compare and swap based on the sum of sales quantity of the products
            if (lSalesAmount <= rSalesAmount) {
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


    //recommendation merge-sort 1)
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

    //recommendation merge-sort 2)
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


    private Set<Product> attachProductImages(Set<Product> productSet) {
        //attach image thumbnail to each product
        for(Product p : productSet) {
            List<ProductImage> productImageList = productImageService.findImagesByProduct(p);
            String productThumbnailPath = productImageList.get(0).getPath();

            p.setProductThumbnailPath(productThumbnailPath);
            p.setCategory(p.getCategory()); //fetch category manually (due to lazy mode)
        }

        return productSet;
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
