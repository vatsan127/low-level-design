package lld._05_decorator.coffee;

// The base ConcreteComponent. A plain coffee with no add-ons.
public class Espresso implements Coffee {
    @Override
    public double cost() {
        return 80.0;
    }

    @Override
    public String description() {
        return "Espresso";
    }
}
