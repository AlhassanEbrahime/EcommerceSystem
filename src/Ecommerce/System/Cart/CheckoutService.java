package Ecommerce.System.Cart;

import Ecommerce.Exception.CheckoutException;
import Ecommerce.Exception.InsufficientBalanceException;
import Ecommerce.Exception.InsufficientQuantityException;
import Ecommerce.System.Payment.PaymentManger;
import Ecommerce.System.Product.BaseProduct;
import Ecommerce.System.Product.Shippable;
import Ecommerce.System.User.Customer;
import Ecommerce.System.shipping.ShippableItemFactory;
import Ecommerce.System.shipping.ShippingService;
import Ecommerce.UIManager.ReceiptPrinter;

import java.util.List;

public class CheckoutService {

    private final ShippingService shippingService;

    private final PaymentManger paymentManger;

    private final ReceiptPrinter receiptPrinter;

    private final ShippableItemFactory shippableItemFactory;


    public CheckoutService(ShippingService shippingService, PaymentManger paymentManger, ReceiptPrinter receiptPrinter, ShippableItemFactory shippableItemFactory) {
        this.shippingService = shippingService;
        this.paymentManger = paymentManger;
        this.receiptPrinter = receiptPrinter;
        this.shippableItemFactory = shippableItemFactory;
    }

    private void validateCheckout(Cart cart)throws CheckoutException{
        if(cart.isEmpty()){
            throw new CheckoutException("The cart is empty");
        }

        List<CartItem> items = cart.getItems();

        items.stream()
                .map(CartItem::getProduct)
                .filter(BaseProduct::isExpired)
                .findFirst()
                .ifPresent(product -> {
                    throw new CheckoutException("Product "+ product.getName() + "has expired");
                });

        items.stream()
                .filter(item -> item.getQuantity() > item.getProduct().getAvailableQuantity())
                .findFirst()
                .ifPresent(item -> {
                    throw  new CheckoutException("Product "+ item.getProduct().getName() + " is out of stock");
                });

    }


    public void checkout(Customer customer, Cart cart) throws CheckoutException {

        try{
            validateCheckout(cart);

            List<CartItem> items = cart.getItems();
            double subtotal = cart.getSubtotal();

            List<Shippable> shippableItems = shippableItemFactory.createFromCartItems(items);


            double shippingCost = shippableItems.isEmpty() ? 0 :
                    shippingService.calculateShippingCost(shippableItems);

            double totalAmount = subtotal + shippingCost;

            try{
                paymentManger.processPayment(customer, totalAmount);
            }catch (InsufficientBalanceException e){
                throw new CheckoutException("payment failed: " + e.getMessage());
            }

            if(!shippableItems.isEmpty()){
                shippingService.processShipment(shippableItems);
            }

            receiptPrinter.printReceipt(items, subtotal, shippingCost, totalAmount, customer.getBalance());

            updateProductQuantities(items);

            cart.clear();

        }catch (Exception e){
            throw new CheckoutException("Checkout failed: "+ e.getMessage());
        }


    }

    private void updateProductQuantities(List<CartItem>items) throws CheckoutException{

        try {
            for (CartItem item : items){
                item.getProduct().decreaseQuantity(item.getQuantity());
            }
        }catch (InsufficientQuantityException e){
            throw new CheckoutException("Failed to update quantities: "+ e.getMessage());
        }

    }


}
