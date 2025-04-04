package Ecommerce.System.Cart;

import Ecommerce.System.Product.BaseProduct;

public class CartItem {

    private final BaseProduct product;
    private final int quantity;


    public CartItem(BaseProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BaseProduct getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice(){
        return product.getPrice() * quantity;
    }
}
