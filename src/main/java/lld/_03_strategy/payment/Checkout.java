package lld._03_strategy.payment;

// The Context. Holds a reference to a PaymentStrategy and delegates
// to it. Knows NOTHING about credit cards, UPI, or wallets -- adding
// a new payment mode does not require editing this class.
public class Checkout {
    private PaymentStrategy strategy;

    public Checkout(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    // Strategy can be swapped at runtime, e.g. if the user changes their mind.
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void completeOrder(double amount) {
        System.out.println("--- placing order ---");
        strategy.pay(amount);
        System.out.println("--- order placed ---");
    }
}
