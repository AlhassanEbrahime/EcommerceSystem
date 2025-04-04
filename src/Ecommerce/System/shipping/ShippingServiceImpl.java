package Ecommerce.System.shipping;

import Ecommerce.System.Product.Shippable;
import Ecommerce.UIManager.WeightFormatter;

import java.util.List;

public class ShippingServiceImpl implements ShippingService{

    private static final double SHIPPING_FEES_PER_KG= 30.0;
    private final WeightFormatter weightFormatter;

    public ShippingServiceImpl(WeightFormatter weightFormatter) {
        this.weightFormatter = weightFormatter;
    }


    @Override
    public void processShipment(List<Shippable> items) {

        System.out.println("** Shipment notice **");

        double totalWeight = items.stream()
                .peek(item -> {
                    System.out.print(item.getName()+"\t\t");
                    System.out.println(weightFormatter.format(item.getWeight()));
                })
                .mapToDouble(Shippable::getWeight)
                .sum();

        System.out.println("Total package weight " + weightFormatter.formatTotal(totalWeight));
        System.out.println();
    }

    @Override
    public double calculateShippingCost(List<Shippable> items) {
        double totalWeight = items.stream()
                .mapToDouble(Shippable::getWeight)
                .sum();

        return Math.floor(totalWeight)*SHIPPING_FEES_PER_KG;
    }
}
