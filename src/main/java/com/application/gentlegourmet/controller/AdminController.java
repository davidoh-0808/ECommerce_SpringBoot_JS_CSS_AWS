package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.*;
import com.application.gentlegourmet.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final PurchaseDetailService purchaseDetailService;
    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final BrandService brandService;


///////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping("/admin-signin")
    public String getAdminPage(Model model) {
        ////////////////////////////////// main process /////////////////////////////
        Map<String, Integer> totalSalesMap = purchaseDetailService.findTotalSalesQuantityAndAmount();
        Map<String, Date> purchaseDateMap = purchaseService.findFirstAndLastPurchaseDate();
        List<ProductSalesInfo> rankedSalesInfoList = productService.findAllProductsAndSalesAmountRanked();

        //////////////////////  sending models for view rendering ///////////////////
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        model.addAttribute("productSearch", productSearch);
        model.addAttribute("totalSalesQuantity", totalSalesMap.get("totalSalesQuantity"));
        model.addAttribute("totalSalesAmount",totalSalesMap.get("totalSalesAmount"));
        model.addAttribute("firstPurchaseDate", purchaseDateMap.get("firstPurchaseDate"));
        model.addAttribute("lastPurchaseDate", purchaseDateMap.get("lastPurchaseDate"));
        model.addAttribute("rankedSalesInfoList", rankedSalesInfoList);

        return "admin/sales";
    }


    @GetMapping("/add-product")
    public String addProduct(Model model) {

        //send Product entity(dto in this case) for uploading a new Product
        Product product = new Product();
        ProductSearch productSearch = new ProductSearch();
        model.addAttribute("product", product);
        model.addAttribute("productSearch", productSearch);

        return "admin/add-product";
    }


    /* with the uploaded info (details, category, brand, images), update DB and S3 bucket
        omitted dropzone.js after multiple failures ..
        reference : https://howtodoinjava.com/spring-mvc/spring-mvc-multi-file-upload-example/
    */
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product, Model model) {
        ////////////////////////////////// main process (1) and (2) /////////////////////////////

        // dissect DTO from view for further processing.. also contains name, desc, price.. AND THE IMAGES
        String categoryName = product.getCategoryName();
        Long brandId = 41L; //hardcoded.. forgot the add brand select tag in add-product.html
        List<MultipartFile> multipartFiles = product.getMultipartFiles();
        String filename = multipartFiles.get(0).getOriginalFilename();

        System.out.println(multipartFiles);
        System.out.println(multipartFiles.get(0).getOriginalFilename());

        //(1) first upload a new Product with only info
        //match the param category with DB categoryId
        Long categoryId = 0L;
        switch(categoryName) {
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
        Category newProductCategory = categoryService.findCategoryById(categoryId);
        Brand newProductBrand = brandService.findBrandById(brandId);
        product.setCategory(newProductCategory);
        product.setBrand(newProductBrand);

        Product uploadedProduct = productService.uploadNewProduct(product);

        //(2) then upload the Product images - ** the uploadedProduct contains List<MultipartFile> **
        productImageService.uploadImagesByProduct(uploadedProduct);

        //////////////////////  sending models for view rendering  ///////////////////
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();
        model.addAttribute("productSearch", productSearch);

        return "admin/add-product";
    }


}
