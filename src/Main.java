import Ecommerce.Exception.CheckoutException;
import Ecommerce.System.Cart.Cart;
import Ecommerce.System.Cart.CheckoutService;
import Ecommerce.System.Cart.ShoppingCart;
import Ecommerce.System.Payment.PaymentManger;
import Ecommerce.System.Payment.PaymentProcessor;
import Ecommerce.System.Product.NonPerishableProduct;
import Ecommerce.System.Product.PerishableProduct;
import Ecommerce.System.User.Customer;
import Ecommerce.System.shipping.ShippableItemFactory;
import Ecommerce.System.shipping.ShippingService;
import Ecommerce.System.shipping.ShippingServiceImpl;
import Ecommerce.UIManager.Impl.ReceiptPrinterImpl;
import Ecommerce.UIManager.Impl.WeightFormaterImpl;
import Ecommerce.UIManager.ReceiptPrinter;
import Ecommerce.UIManager.WeightFormatter;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try{
            driverCode();
        }catch (Exception ex){
            System.err.println("Fatal error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private static void driverCode() {

        WeightFormatter weightFormatter = new WeightFormaterImpl();
        ShippingService shippingService = new ShippingServiceImpl(weightFormatter);
        PaymentManger paymentManger = new PaymentProcessor();
        ReceiptPrinter receiptPrinter = new ReceiptPrinterImpl();
        ShippableItemFactory shippableItemFactory = new ShippableItemFactory();

        // create checkout
        CheckoutService checkoutService = new CheckoutService(
                shippingService, paymentManger, receiptPrinter, shippableItemFactory
        );

        // create products
        PerishableProduct cheese = new PerishableProduct("Cheese", 100, 5,
                LocalDate.now().plusDays(7), 0.2, true);

        PerishableProduct biscuits = new PerishableProduct("Biscuits", 150, 10,
                LocalDate.now().plusDays(30), 0.7, true);

        NonPerishableProduct tv = new NonPerishableProduct("TV", 5000, 3, 20.0, true);

        NonPerishableProduct scratchCard = new NonPerishableProduct("Scratch Card", 50, 100, 0.0, false);

        // Create customer
        Customer customer = new Customer(10000);


        System.out.println("\n=== Test Case 1: Successful Checkout ===");
        Cart cart1 = new ShoppingCart();
        cart1.add(cheese, 2);
        cart1.add(tv, 1);
        cart1.add(scratchCard, 1);
        cart1.add(biscuits, 1);

        try {
            checkoutService.checkout(customer, cart1);
        } catch (CheckoutException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test case 2: Expired product
        System.out.println("\n=== Test Case 2: Expired Product ===");
        PerishableProduct expiredCheese = new PerishableProduct("Expired Cheese", 100, 5,
                LocalDate.now().minusDays(1), 0.2, true);

        Cart cart2 = new ShoppingCart();
        cart2.add(expiredCheese, 1);

        try {
            checkoutService.checkout(customer, cart2);
        } catch (CheckoutException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test case 3: Insufficient balance
        System.out.println("\n=== Test Case 3: Insufficient Balance ===");
        Customer poorCustomer = new Customer( 100);
        Cart cart3 = new ShoppingCart();
        cart3.add(tv, 1);

        try {
            checkoutService.checkout(poorCustomer, cart3);
        } catch (CheckoutException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test case 4: Empty cart
        System.out.println("\n=== Test Case 4: Empty Cart ===");
        Cart cart4 = new ShoppingCart();

        try {
            checkoutService.checkout(customer, cart4);
        } catch (CheckoutException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

}