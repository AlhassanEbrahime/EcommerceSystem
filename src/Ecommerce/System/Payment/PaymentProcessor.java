package Ecommerce.System.Payment;

import Ecommerce.Exception.InsufficientBalanceException;
import Ecommerce.System.User.Customer;

public class PaymentProcessor implements PaymentManger{
    @Override
    public void processPayment(Customer customer, double amount) throws InsufficientBalanceException {
        customer.calculateBalance(amount);
    }
}
