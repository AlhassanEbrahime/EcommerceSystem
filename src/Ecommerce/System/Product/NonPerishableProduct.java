package Ecommerce.System.Product;

public class NonPerishableProduct extends Product implements WeightedProduct {

    private final double weight;

    private final boolean requiredShipping;

    public NonPerishableProduct(String name, double price, int availableQuantity ,double weight, boolean requiredShipping) {
        super(name, price, availableQuantity);
        this.weight = weight;
        this.requiredShipping = requiredShipping;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public boolean requiredShipping() {
        return requiredShipping;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}
