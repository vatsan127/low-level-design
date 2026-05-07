package lld._02_solid.ocp;

// OCP violation: every time a NEW discount type is introduced
// (BLACK_FRIDAY, STUDENT, EMPLOYEE, ...), this switch must be edited.
// That edit risks breaking the existing, already-tested branches.
public class Bad {

    enum DiscountType { NONE, FESTIVE, LOYALTY }

    static class DiscountCalculator {
        double apply(double price, DiscountType type) {
            switch (type) {
                case NONE:    return price;
                case FESTIVE: return price * 0.90;   // 10% off
                case LOYALTY: return price * 0.85;   // 15% off
                default:      throw new IllegalArgumentException("Unknown: " + type);
            }
        }
    }

    public static void main(String[] args) {
        DiscountCalculator calc = new DiscountCalculator();
        System.out.println(calc.apply(100, DiscountType.FESTIVE));
        System.out.println(calc.apply(100, DiscountType.LOYALTY));
    }
}
