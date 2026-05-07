package lld._03_strategy.payment;

// The Strategy interface. Every payment mode implements this.
public interface PaymentStrategy {
    void pay(double amount);
}
