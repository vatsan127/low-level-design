package lld._03_strategy.payment;

public class WalletPayment implements PaymentStrategy {
    private final String walletId;

    public WalletPayment(String walletId) { this.walletId = walletId; }

    @Override
    public void pay(double amount) {
        System.out.printf("Debiting %.2f from wallet %s%n", amount, walletId);
    }
}
