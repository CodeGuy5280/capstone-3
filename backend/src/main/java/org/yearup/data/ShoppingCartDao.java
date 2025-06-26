package org.yearup.data;

import org.springframework.stereotype.Component;
import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    //add product
    void addProduct(int userId, int productId);

    //delete cart
    void deleteCart(int userId);

    //update cart items
    void updateCartItems(int userId, int productId, int quantity);
}
