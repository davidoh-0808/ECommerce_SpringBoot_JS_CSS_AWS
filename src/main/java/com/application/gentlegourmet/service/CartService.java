package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    //////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////////////

    public List<Cart> findAllCarts() {
        return cartRepository.findAll();
    }

    public void createCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart findCartByCustomerAndProduct(Customer customer, Product product) {
        return cartRepository.findCartByCustomerAndProduct(customer, product);
    }
}
