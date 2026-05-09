package lld._05_decorator.coffee;

public class SugarDecorator extends CoffeeDecorator {
    private static final double SUGAR_COST = 5.0;

    public SugarDecorator(Coffee wrapped) {
        super(wrapped);
    }

    @Override
    public double cost() {
        return wrapped.cost() + SUGAR_COST;
    }

    @Override
    public String description() {
        return wrapped.description() + " + sugar";
    }
}
