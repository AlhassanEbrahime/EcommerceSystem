package Ecommerce.System.shipping;

import Ecommerce.System.Cart.CartItem;
import Ecommerce.System.Product.*;
import Ecommerce.UIManager.WeightFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class ShippableItemFactory {


    private Shippable createShippableItem(CartItem item){
        BaseProduct product = item.getProduct();
        double weight = 0;

        if(product instanceof WeightedProduct){
            weight = ((WeightedProduct) product).getWeight();
        }

        return new ShippedItem(product.getName(),weight, item.getQuantity());
    }



    public List<Shippable> createFromCartItems(List<CartItem> items){
        return items.stream()
                .filter(item -> item.getProduct().requiredShipping())
                .map(this::createShippableItem)
                .collect(Collectors.toList());
    }



}
