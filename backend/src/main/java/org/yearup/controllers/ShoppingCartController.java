package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

// convert this class to a REST controller
// only logged-in users should have access to these actions
@RestController
@PreAuthorize("isAuthenticated()")//checks for login status
@RequestMapping("/cart")//path for cart
public class ShoppingCartController//TODO: Use Constructor injection?: (ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao)
{
    // a shopping cart requires
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;


    // each method in this controller requires a Principal object as a parameter
    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        try {
            // get the currently logged-in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added
    @PostMapping("/products/{productId}")
    public void addProductToCart(@PathVariable int productId, Principal principal) {
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            int userId = user.getId();

            //TODO: Fix "addProduct" method in ShoppingCartDao and/or MySqlShoppingCartDao
            shoppingCartDao.addProduct(userId, productId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated
    //TODO: FIX
    @PutMapping("/products/{productId}")
    public void UpdateCartItem(@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal){
        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            int quantity = item.getQuantity();
            shoppingCartDao.updateCartItems(userId,productId,quantity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart
    @DeleteMapping
    public void deleteCart(Principal principal){
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            //needed the injected field not the class ShoppingCartDao
            shoppingCartDao.deleteCart(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}