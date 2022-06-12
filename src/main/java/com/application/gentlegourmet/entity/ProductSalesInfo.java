package com.application.gentlegourmet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//not an entity, but a DTO between a payment library AJAX and CheckoutController method
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesInfo {

    private String productName;
    private int productSalesAmount;

}
