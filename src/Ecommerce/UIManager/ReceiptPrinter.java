package Ecommerce.UIManager;

import Ecommerce.System.Cart.CartItem;

import java.util.List;

public interface ReceiptPrinter {
    void printReceipt(List<CartItem> items, double subtotal, double shippingCost, double totalAmount, double remainingBalance);
}
