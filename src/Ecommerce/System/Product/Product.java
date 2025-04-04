package Ecommerce.System.Product;

import Ecommerce.Exception.InsufficientQuantityException;

public abstract class Product implements BaseProduct  {

    private final String name;

    private final double price;

    private int availableQuantity;

    public Product(String name, double price, int availableQuantity) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getAvailableQuantity() {
        return availableQuantity;
    }


    @Override
    public void decreaseQuantity(int quantity) throws InsufficientQuantityException{
        if(quantity > this.availableQuantity){
            throw new InsufficientQuantityException("Not enough quantity available");
        }

        this.availableQuantity -= quantity;
    }
}
