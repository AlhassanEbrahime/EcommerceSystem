package Ecommerce.System.shipping;

import Ecommerce.System.Product.Shippable;

import java.util.List;

public interface ShippingService {
    void processShipment(List<Shippable> items);
    double calculateShippingCost(List<Shippable> items);
}
