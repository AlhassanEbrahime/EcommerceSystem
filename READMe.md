# E-Commerce System

This repository contains a Java-based e-commerce system with support for product management, shopping cart, checkout process, and shipping functionality. The system follows SOLID principles and utilizes object-oriented design patterns.

## System Overview

The e-commerce system allows users to:
- Add various types of products to a shopping cart
- Process checkout operations
- Handle shipping for physical products
- Print receipts for purchases

## Class Diagram

```mermaid
classDiagram
    %% Interfaces
    class Product {
        <<interface>>
        +getName() String
        +getPrice() double
        +getAvailableQuantity() int
        +decreaseQuantity(int) void
        +isExpired() boolean
        +requiresShipping() boolean
    }
    
    class Weightable {
        <<interface>>
        +getWeight() double
    }
    
    class Shippable {
        <<interface>>
        +getName() String
        +getWeight() double
    }
    
    class Cart {
        <<interface>>
        +add(Product, int) void
        +remove(Product) void
        +isEmpty() boolean
        +getItems() List~CartItem~
        +getSubtotal() double
        +clear() void
    }
    
    class PaymentProcessor {
        <<interface>>
        +processPayment(Customer, double) void
    }
    
    class WeightFormatter {
        <<interface>>
        +format(double) String
        +formatTotal(double) String
    }
    
    class ShippingService {
        <<interface>>
        +processShipment(List~Shippable~) void
        +calculateShippingCost(List~Shippable~) double
    }
    
    class ReceiptPrinter {
        <<interface>>
        +printReceipt(List~CartItem~, double, double, double, double) void
    }
    
    %% Abstract Classes
    class AbstractProduct {
        <<abstract>>
        -name: String
        -price: double
        -availableQuantity: int
        +getName() String
        +getPrice() double
        +getAvailableQuantity() int
        +decreaseQuantity(int) void
    }
    
    %% Concrete Classes
    class PerishableProduct {
        -expiryDate: LocalDate
        -weight: double
        -requiresShipping: boolean
        +isExpired() boolean
        +requiresShipping() boolean
        +getWeight() double
        +getExpiryDate() LocalDate
    }
    
    class NonPerishableProduct {
        -weight: double
        -requiresShipping: boolean
        +isExpired() boolean
        +requiresShipping() boolean
        +getWeight() double
    }
    
    class ShippableItem {
        -name: String
        -weight: double
        -quantity: int
        +getName() String
        +getWeight() double
        +getQuantity() int
    }
    
    class CartItem {
        -product: Product
        -quantity: int
        +getProduct() Product
        +getQuantity() int
        +getTotalPrice() double
    }
    
    class ShoppingCart {
        -items: List~CartItem~
        -validateAddToCart(Product, int) void
    }
    
    class Customer {
        -name: String
        -balance: double
        +getName() String
        +getBalance() double
        +deductBalance(double) void
    }
    
    class SimplePaymentProcessor {
    }
    
    class StandardWeightFormatter {
    }
    
    class StandardShippingService {
        -SHIPPING_RATE_PER_KG: double
        -weightFormatter: WeightFormatter
    }
    
    class ConsoleReceiptPrinter {
    }
    
    class ShippableItemFactory {
        +createFromCartItems(List~CartItem~) List~Shippable~
        -createShippableItem(CartItem) Shippable
    }
    
    class CheckoutService {
        -shippingService: ShippingService
        -paymentProcessor: PaymentProcessor
        -receiptPrinter: ReceiptPrinter
        -shippableItemFactory: ShippableItemFactory
        +checkout(Customer, Cart) void
        -validateCheckout(Cart) void
        -updateProductQuantities(List~CartItem~) void
    }
    
    %% Exception Classes
    class InsufficientQuantityException {
    }
    
    class InsufficientBalanceException {
    }
    
    class CheckoutException {
    }
    
    %% Relationships
    Product <|.. AbstractProduct
    AbstractProduct <|-- PerishableProduct
    AbstractProduct <|-- NonPerishableProduct
    Weightable <|.. PerishableProduct
    Weightable <|.. NonPerishableProduct
    Shippable <|.. ShippableItem
    Cart <|.. ShoppingCart
    PaymentProcessor <|.. SimplePaymentProcessor
    WeightFormatter <|.. StandardWeightFormatter
    ShippingService <|.. StandardShippingService
    ReceiptPrinter <|.. ConsoleReceiptPrinter
    
    ShoppingCart --> CartItem
    CartItem --> Product
    StandardShippingService --> WeightFormatter
    CheckoutService --> ShippingService
    CheckoutService --> PaymentProcessor
    CheckoutService --> ReceiptPrinter
    CheckoutService --> ShippableItemFactory
    ShippableItemFactory --> Shippable
```


## Key Components

### Product Management
- `Product` interface defines the core behavior for all products
- `AbstractProduct` provides a skeletal implementation
- `PerishableProduct` and `NonPerishableProduct` implement specific behaviors

### Shopping Cart
- `Cart` interface defines cart operations
- `ShoppingCart` implements cart operations with validation
- `CartItem` encapsulates product and quantity information

### Checkout Process
- `CheckoutService` orchestrates the checkout process
- `PaymentProcessor` handles payment operations
- `Customer` represents the user with a balance

### Shipping System
- `ShippingService` handles shipping calculations and processing
- `Shippable` defines items that can be shipped
- `ShippableItemFactory` creates shipping items from cart items
- `WeightFormatter` formats weight values for display

### Receipt Generation
- `ReceiptPrinter` defines receipt printing behavior
- `ConsoleReceiptPrinter` outputs receipts to the console

## Usage Example

```java
// Create dependencies
WeightFormatter weightFormatter = new StandardWeightFormatter();
ShippingService shippingService = new StandardShippingService(weightFormatter);
PaymentProcessor paymentProcessor = new SimplePaymentProcessor();
ReceiptPrinter receiptPrinter = new ConsoleReceiptPrinter();
ShippableItemFactory shippableItemFactory = new ShippableItemFactory();

// Create checkout service
CheckoutService checkoutService = new CheckoutService(
    shippingService, paymentProcessor, receiptPrinter, shippableItemFactory
);

// Create products
Product cheese = new PerishableProduct("Cheese", 100, 5, 
        LocalDate.now().plusDays(7), 0.2, true);
Product tv = new NonPerishableProduct("TV", 5000, 3, 20.0, true);

// Create customer
Customer customer = new Customer("John", 10000);

// Create shopping cart and add products
Cart cart = new ShoppingCart();
cart.add(cheese, 2);
cart.add(tv, 1);

// Checkout
try {
    checkoutService.checkout(customer, cart);
} catch (CheckoutException e) {
    System.out.println("Error: " + e.getMessage());
}
```

## Exception Handling

The system uses specific exceptions to handle various error cases:
- `InsufficientQuantityException`: When requested quantity exceeds available quantity
- `InsufficientBalanceException`: When customer balance is insufficient for payment
- `CheckoutException`: Generic checkout errors including expired products and empty carts

## Design Principles Applied

1. **Single Responsibility Principle**: Each class has one responsibility
2. **Open/Closed Principle**: System is open for extension but closed for modification
3. **Liskov Substitution Principle**: Subtypes can be substituted for base types
4. **Interface Segregation Principle**: Clients aren't forced to depend on interfaces they don't use
5. **Dependency Inversion Principle**: High-level modules depend on abstractions


## Running the Demo

The `Main` class contains several test cases demonstrating different aspects of the system:
1. Successful checkout with multiple products
2. Error handling for expired products
3. Error handling for insufficient customer balance
4. Error handling for empty cart

### output:

![image](https://github.com/user-attachments/assets/fcdc371a-e1ca-4463-a34b-92bae395375e)



