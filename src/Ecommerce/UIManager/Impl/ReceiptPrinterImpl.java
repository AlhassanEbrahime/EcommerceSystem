package Ecommerce.UIManager.Impl;

import Ecommerce.System.Cart.CartItem;
import Ecommerce.UIManager.ReceiptPrinter;

import java.util.List;

public class ReceiptPrinterImpl implements ReceiptPrinter {
    @Override
    public void printReceipt(List<CartItem> items, double subtotal, double shippingCost, double totalAmount, double remainingBalance) {

        System.out.println("** Checkout receipt **");

        items.forEach(item -> {
            System.out.print(item.getQuantity()+ "x "+ item.getProduct().getName()+ "\t\t");
            System.out.println(String.format("%.0f",item.getTotalPrice()));
        });

        System.out.println("----------------------");
        System.out.printf("%-20s%.0f%n", "Subtotal", subtotal);
        System.out.printf("%-20s%.0f%n", "Shipping", shippingCost);
        System.out.printf("%-20s%.0f%n", "Amount", totalAmount);
        System.out.printf("%-20s%.0f%n", "Remaining Balance", remainingBalance);
    }
}
