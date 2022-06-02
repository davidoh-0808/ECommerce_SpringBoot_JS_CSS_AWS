package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Brand;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    //toDO: sort by most sold


    //toDO: sort by price


    //toDO: sort by rating


}
