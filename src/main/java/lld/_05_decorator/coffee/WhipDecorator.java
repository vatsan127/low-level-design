package lld._05_decorator.coffee;

public class WhipDecorator extends CoffeeDecorator {
    private static final double WHIP_COST = 30.0;

    public WhipDecorator(Coffee wrapped) {
        super(wrapped);
    }

    @Override
    public double cost() {
        return wrapped.cost() + WHIP_COST;
    }

    @Override
    public String description() {
        return wrapped.description() + " + whip";
    }
}
