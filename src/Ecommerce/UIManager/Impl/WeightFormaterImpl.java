package Ecommerce.UIManager.Impl;

import Ecommerce.UIManager.WeightFormatter;

public class WeightFormaterImpl implements WeightFormatter {
    @Override
    public String format(double weight) {
        if (weight < 1) {
            return (int)(weight * 1000) + "g";
        } else {
            return weight + "kg";
        }
    }

    @Override
    public String formatTotal(double weight) {
        if (weight < 1) {
            return (int)(weight * 1000) + "g";
        } else {
            return String.format("%.1fkg", weight);
        }
    }
}
