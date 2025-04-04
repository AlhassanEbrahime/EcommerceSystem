package Ecommerce.System.Cart;

import Ecommerce.System.Product.BaseProduct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ShoppingCart implements Cart{

    private final List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }


    private void validateProduct(BaseProduct product, int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        if(quantity > product.getAvailableQuantity()){
            throw new IllegalArgumentException("Ordered amount exceeds available quantity");
        }

        boolean productExistence = items.stream()
                .anyMatch(item -> item.getProduct().getName().equals(product.getName()));


        if(productExistence){
            throw new IllegalArgumentException("Product already in cart , update quantity instead");
        }
    }

    @Override
    public void add(BaseProduct product, int quantity){
        validateProduct(product, quantity);
        items.add(new CartItem(product, quantity));
    }

    @Override
    public void remove(BaseProduct product) {
         items.removeIf(item -> item.getProduct().getName().equals(product.getName()));
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public double getSubtotal() {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    @Override
    public void clear() {
        items.clear();
    }
}
