package lld._03_strategy.payment;

public class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;
    private final String holder;

    public CreditCardPayment(String cardNumber, String holder) {
        this.cardNumber = cardNumber;
        this.holder = holder;
    }

    @Override
    public void pay(double amount) {
        String masked = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        System.out.printf("Charging %.2f to credit card %s (%s)%n", amount, masked, holder);
    }
}
