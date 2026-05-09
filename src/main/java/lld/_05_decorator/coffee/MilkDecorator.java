package lld._05_decorator.coffee;

public class MilkDecorator extends CoffeeDecorator {
    private static final double MILK_COST = 20.0;

    public MilkDecorator(Coffee wrapped) {
        super(wrapped);
    }

    @Override
    public double cost() {
        return wrapped.cost() + MILK_COST;
    }

    @Override
    public String description() {
        return wrapped.description() + " + milk";
    }
}
