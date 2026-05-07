package lld._02_solid.ocp;

// Open for extension (add new DiscountPolicy classes), closed for modification
// (DiscountCalculator never changes when a new discount type appears).
public class Good {

    interface DiscountPolicy {
        double apply(double price);
    }

    static class NoDiscount implements DiscountPolicy {
        public double apply(double price) { return price; }
    }

    static class FestiveDiscount implements DiscountPolicy {
        public double apply(double price) { return price * 0.90; }
    }

    static class LoyaltyDiscount implements DiscountPolicy {
        public double apply(double price) { return price * 0.85; }
    }

    // To add Black Friday: write a new class, do NOT touch existing ones.
    static class BlackFridayDiscount implements DiscountPolicy {
        public double apply(double price) { return price * 0.50; }
    }

    static class DiscountCalculator {
        double apply(double price, DiscountPolicy policy) {
            return policy.apply(price);
        }
    }

    public static void main(String[] args) {
        DiscountCalculator calc = new DiscountCalculator();
        System.out.println(calc.apply(100, new FestiveDiscount()));
        System.out.println(calc.apply(100, new LoyaltyDiscount()));
        System.out.println(calc.apply(100, new BlackFridayDiscount()));
    }
}
