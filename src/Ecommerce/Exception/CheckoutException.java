package Ecommerce.Exception;

public class CheckoutException extends RuntimeException {

    public CheckoutException(){
        super();
    }

    public CheckoutException(String message){
        super(message);
    }
}
