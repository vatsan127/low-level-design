package lld._03_strategy.payment;

public class Demo {
    public static void main(String[] args) {
        Checkout checkout = new Checkout(new CreditCardPayment("1234567812345678", "Alice"));
        checkout.completeOrder(1200.00);

        // Same checkout, swap the strategy at runtime.
        checkout.setStrategy(new UpiPayment("alice@okbank"));
        checkout.completeOrder(450.00);

        checkout.setStrategy(new WalletPayment("WALLET-789"));
        checkout.completeOrder(99.00);
    }
}
