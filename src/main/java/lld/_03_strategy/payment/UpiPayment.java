package lld._03_strategy.payment;

public class UpiPayment implements PaymentStrategy {
    private final String vpa;

    public UpiPayment(String vpa) { this.vpa = vpa; }

    @Override
    public void pay(double amount) {
        System.out.printf("Collecting %.2f via UPI from %s%n", amount, vpa);
    }
}
