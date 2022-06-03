package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Brand;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;


    @GetMapping("/brand/{brandId}")
    public String getBrandPage(@PathVariable("brandId") Long brandId, Model model) {
        Brand brand = brandService.findBrandById(brandId);
        Set<Product> productSet = brandService.findProductsByBrandId(brandId);

        if(productSet.isEmpty()) {
            throw new RuntimeException("no product was found by the brandId=" + brandId);
        }

        model.addAttribute("brand", brand);
        model.addAttribute("products", productSet);

        return "product/brand";
    }


    //toDO: sort by most sold_in progress
    @GetMapping("/brand/{brandId}/most-sold")
    public String getBrandPageSortBySale(@PathVariable("brandId") Long brandId, Model model) {
        Brand brand = brandService.findBrandById(brandId);
        List<Product> productListByPrice = brandService.findProductsByBrandIdSortByPrice(brandId);

        model.addAttribute("brand", brand);
        model.addAttribute("products", productListByPrice);

        return "product/brand";
    }


    //toDO: sort by price
    @GetMapping("/brand/{brandId}/price")
    public String getBrandPageSortByPrice(@PathVariable("brandId") Long brandId, Model model) {
        Brand brand = brandService.findBrandById(brandId);



        model.addAttribute("brand", brand);


        return "product/brand";
    }


    //toDO: sort by rating
    @GetMapping("/brand/{brandId}/rating")
    public String getBrandPageSortByRating(@PathVariable("brandId") Long brandId, Model model) {
        Brand brand = brandService.findBrandById(brandId);


        model.addAttribute("brand", brand);

        return "product/brand";
    }

}
