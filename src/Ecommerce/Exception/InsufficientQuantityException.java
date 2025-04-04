package Ecommerce.Exception;

public class InsufficientQuantityException extends RuntimeException {

    public InsufficientQuantityException(){
        super();
    }

    public InsufficientQuantityException(String message){
        super(message);
    }

}
