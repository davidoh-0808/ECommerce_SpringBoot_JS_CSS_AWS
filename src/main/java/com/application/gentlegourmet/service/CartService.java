package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductImage;
import com.application.gentlegourmet.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductImageService productImageService;


    ////////////////////////////////////////////////////////////////////////////////


    public void addToCart(Long productId) {
        /*  if cart item is present by the customer and the product, increase quantity
            if no cart item is present, make a new cart item entity and add via repo
         */
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findCustomerByUsername(username);
        Product product = productService.findProductById(productId);

        //get Customer entity by querying CART by CUSTOMER_ID, then add this Customer to CART
        Cart cart = this.findCartByCustomerAndProduct(customer, product);

        if(cart == null) {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setProduct(product);
            newCart.setQuantity(1);
            this.createCart(newCart);
        } else {
            int previousQuantity = cart.getQuantity();
            cart.setQuantity(previousQuantity + 1);
        }
    }


    public void emptyCart() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerService.findCustomerByUsername(username);

        cartRepository.removeAllCartsByCustomer(customer);
    }


    public Map<String, Object> listCartItems() {
        Map<String, Object> cartsMap = new HashMap<>();
        List<Cart> carts = null;
        int cartsTotalPrice = 0;

        //get customer info only if the customer is surely logged in (no null)
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //get the logged in customer entity
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerService.findCustomerByUsername(username);

            //user the customer entity to get all the customer's cart items
            carts = this.findCartsByCustomer(customer);
            cartsMap.put("carts", carts);

            if( !carts.isEmpty() ) {
                //attach product thumbnails & calculate total price of the carts
                for(Cart cart : carts) {
                    Product p = cart.getProduct();
                    List<ProductImage> productImageList = productImageService.findImagesByProduct(p);
                    String productThumbnailPath = productImageList.get(0).getPath();
                    p.setProductThumbnailPath(productThumbnailPath);

                    cartsTotalPrice += p.getPrice();
                }
            }
            cartsMap.put("cartsTotalPrice", cartsTotalPrice);
        }

        return cartsMap;
    }


    //find the current user's cart items with category and brand attached on the products
    public Map<String, Object> findCartItemsWithCategoryAndBrand() {
        Map<String, Object> cartsMap = this.listCartItems();
        List<Cart> carts = (List<Cart>) cartsMap.get("carts");

        //need to fetch category and brand manually (due to lazy fetch type)
        if( !carts.isEmpty() ) {
            for(Cart c : carts) {
                c.getProduct().getCategory();
                c.getProduct().getBrand();
            }
        }

        return cartsMap;
    }


    //@Transactional
    public void removeAllCartsByCustomerUsername(String customerUsername) {
        Customer customer = customerService.findCustomerByUsername(customerUsername);

        cartRepository.removeAllCartsByCustomer(customer);
    }




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


    public List<Cart> findCartsByCustomer(Customer customer) {
        return cartRepository.findCartsByCustomer(customer);
    }


    ////////////////////////////////////////////////////////////////////////////////





}
