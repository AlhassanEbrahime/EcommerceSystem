package Ecommerce.System.User;

import Ecommerce.Exception.InsufficientBalanceException;

public class Customer {
    private double balance;

    public Customer(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void calculateBalance(double amount) throws InsufficientBalanceException{
        if (amount > balance){
            throw new InsufficientBalanceException(String.format("Insufficient balance. Required: %.2f, Available: %.2f", amount, balance)
            );
        }
        this.balance -= amount;
    }
}
