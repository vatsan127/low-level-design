package lld._05_decorator.coffee;

public class Demo {
    public static void main(String[] args) {
        // Plain espresso, no decorators.
        Coffee plain = new Espresso();
        print(plain);

        // Espresso + milk. One decorator wraps the base.
        Coffee withMilk = new MilkDecorator(new Espresso());
        print(withMilk);

        // Espresso + milk + sugar. Stack two decorators.
        Coffee withMilkSugar = new SugarDecorator(new MilkDecorator(new Espresso()));
        print(withMilkSugar);

        // The full works. Decorators stack in any order.
        Coffee fullWorks = new WhipDecorator(
                                new SugarDecorator(
                                    new MilkDecorator(
                                        new Espresso())));
        print(fullWorks);

        // Same add-ons, different order. Cost is the same; description
        // reflects the stacking order. If decorators ever interact
        // (e.g. a DiscountDecorator), order would matter for cost too.
        Coffee differentOrder = new MilkDecorator(
                                    new WhipDecorator(
                                        new SugarDecorator(
                                            new Espresso())));
        print(differentOrder);
    }

    private static void print(Coffee c) {
        System.out.printf("%-45s  cost = %.2f%n", c.description(), c.cost());
    }
}
