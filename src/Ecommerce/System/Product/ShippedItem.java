package Ecommerce.System.Product;

public class ShippedItem implements Shippable{

    public final String name;

    private final double weight;

    private final int quantity ;

    public ShippedItem(String name, double weight, int quantity) {
        this.name = name;
        this.weight = weight;
        this.quantity = quantity;
    }

    @Override
    public String getName() {
        return quantity + "x "+ name;
    }

    @Override
    public double getWeight() {
        return weight*quantity;
    }

    public int getQuantity(){
        return quantity;
    }
}
