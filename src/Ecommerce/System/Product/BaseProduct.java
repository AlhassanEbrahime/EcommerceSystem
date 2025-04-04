package Ecommerce.System.Product;
import Ecommerce.Exception.InsufficientQuantityException;
public interface BaseProduct {

    String getName();
    double getPrice();
    int getAvailableQuantity();
    void decreaseQuantity(int quantity) throws InsufficientQuantityException;
    boolean isExpired ();
    boolean requiredShipping();

}
