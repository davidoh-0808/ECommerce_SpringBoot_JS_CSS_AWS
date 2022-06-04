package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Category;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductImage;
import com.application.gentlegourmet.entity.PurchaseDetail;
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

    /* @ PersistenceContext
    private EntityManager em; */


    //////////////////////////////////////////////////////////////////////

    public List<Product> findTopFiveBestsellingProducts() {
        Set<Product> productSet = productRepository.findAllProductsAndCategory();

        //prep the Product Set for merge sort method
        List<Product> productList = new ArrayList<>(productSet);
        Object[] object = productList.toArray();
        Product[] productArray = Arrays.copyOf(object, object.length, Product[].class);

        //refer to private merge sorting methods below
        mergeSort(productArray, productArray.length);

        //convert the merge sorted array back into ArrayList / retain only the top 5 results
        List<Product> topFiveProductList = Arrays.asList(productArray).subList(0,5);

        //attach product_image to each product entity before return
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


    //////////////////////////////////////////////////////////////////////////////////////////////////

    //merge-sort 1)
    private void mergeSort(Product[] a, int n) {
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
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        //(2) finally send the prepped arrays to merge() sorter method
        merge(a, l, r, mid, n - mid);
    }

    //merge-sort 2)
    private void merge(Product[] a, Product[] l, Product[] r, int left, int right) {
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
