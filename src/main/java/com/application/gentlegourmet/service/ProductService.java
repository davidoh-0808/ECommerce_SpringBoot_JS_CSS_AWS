package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Category;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductImage;
import com.application.gentlegourmet.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

    /* @ PersistenceContext
    private EntityManager em; */


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

        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product is found by "+id));
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


}
