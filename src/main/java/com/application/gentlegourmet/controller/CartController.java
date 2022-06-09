package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.CustomerService;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final ProductService productService;
    private final CartService cartService;
    private final CustomerService customerService;

    @GetMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, Model model, RedirectAttributes redirectAttr, HttpServletRequest request) {
        // common variable for page redirect
        String referer = request.getHeader("Referer");

        //1) check for authentication principal
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            /*2)check if cart item is present by the product_id, increase quantity
                if no cart item is present, make a new cart item entity and add via repo
                    regular login USERNAME : ex. veganlife123
                    google login USERNAME : 100851712432195957839
             */
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerService.findCustomerByUsername(username);
            Product product = productService.findProductById(productId);

            //get Customer entity by querying CART by CUSTOMER_ID, then add this Customer to CART
            Cart cart = cartService.findCartByCustomerAndProduct(customer, product);

            if(cart == null) {
                Cart newCart = new Cart();
                newCart.setCustomer(customer);
                newCart.setProduct(product);
                newCart.setQuantity(1);
                cartService.createCart(newCart);
            } else {
                cart.setQuantity(cart.getQuantity() + 1);
            }


            //3) config redirectAttr and redirect to previous page
            redirectAttr.addAttribute("testMessage", null).addFlashAttribute("cartSuccessMessage", "The Product was added successfully :)");

            return "redirect:" + referer;

        } else {
            //error message for the view to alert
            redirectAttr.addAttribute("testMessage", null).addFlashAttribute("cartFailureMessage", "Adding to cart requires login :)");
            return "redirect:" + referer;

        }

    } //end of addToCart


    @GetMapping("/subtract-from-cart/{productId}")
    public String subtractFromCart(@PathVariable("productId") Long productId) {

        if(SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
            !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerService.findCustomerByUsername(username);
            Product product = productService.findProductById(productId);

            //get Customer entity by querying CART by CUSTOMER_ID and Product_ID..
            Cart cart = cartService.findCartByCustomerAndProduct(customer, product);

            //then add this Customer to CART
            if(cart == null) {
                return "The Product does not exist in the cart :)";
            } else {
                cart.setQuantity(cart.getQuantity() - 1);
            }

            //3) toDO: finally, in view.. renew "@{/list-cart-items}"

            return "subtract-to-cart was successful";

        } else {
            //error message for the view to alert
            return "Please login before subtracting the item to cart :)";

        }

    }


    @GetMapping("/list-cart-items")
    public List<Cart> listCartItems() {

        //toDO: need to check this method ... list cart items for top-nav & checkout page
        List<Cart> carts = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //get the logged in customer entity
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerService.findCustomerByUsername(username);

            //user the customer entity to get all the customer's cart items
            carts = cartService.findCartsByCustomer(customer);

        }

        return carts;

    }



}
