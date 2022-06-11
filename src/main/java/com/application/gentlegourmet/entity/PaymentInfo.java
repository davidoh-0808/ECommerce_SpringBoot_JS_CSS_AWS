package com.application.gentlegourmet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//not an entity, but a DTO between a payment library AJAX and CheckoutController method @RequestBody
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    private String imp_uid;
    private String merchant_uid;
    private String buyer_name;
    private String amount;

}
