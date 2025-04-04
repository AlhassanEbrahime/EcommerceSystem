package Ecommerce.System.Product;

import java.time.LocalDate;

public class PerishableProduct extends Product implements WeightedProduct {

    private final LocalDate expiryDate;

    private final double weight;

    private final boolean requiredShipping;


    public PerishableProduct(String name, double price, int availableQuantity,
                             LocalDate expiryDate, double weight, boolean requiredShipping) {
        super(name, price, availableQuantity);

        this.expiryDate = expiryDate;
        this.weight = weight;
        this.requiredShipping = requiredShipping;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public boolean requiredShipping() {
        return requiredShipping;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public LocalDate getExpiryDate(){
        return expiryDate;
    }
}
