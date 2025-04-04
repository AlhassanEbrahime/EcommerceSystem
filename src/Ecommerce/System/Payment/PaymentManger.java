package Ecommerce.System.Payment;

import Ecommerce.Exception.InsufficientBalanceException;
import Ecommerce.System.User.Customer;

public interface PaymentManger {
    void processPayment(Customer customer, double amount) throws InsufficientBalanceException;
}
