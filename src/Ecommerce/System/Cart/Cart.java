package Ecommerce.System.Cart;

import Ecommerce.System.Product.BaseProduct;
import Ecommerce.System.Product.Product;

import java.util.List;

public interface Cart {

    void add(BaseProduct product, int quantity) throws IllegalArgumentException;
    void remove(BaseProduct product);
    boolean isEmpty();
    List<CartItem> getItems();
    double getSubtotal();
    void clear();
}
