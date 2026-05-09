package lld._05_decorator.coffee;

// Abstract Decorator. Holds a reference to the wrapped Coffee and
// forwards calls to it by default. Concrete decorators extend this
// and override only the bits they want to augment.
//
// IS-A Coffee  (so it can be used wherever a Coffee is expected)
// HAS-A Coffee (so it can wrap any other Coffee, including another decorator)
public abstract class CoffeeDecorator implements Coffee {
    protected final Coffee wrapped;

    protected CoffeeDecorator(Coffee wrapped) {
        if (wrapped == null) throw new IllegalArgumentException("wrapped coffee is null");
        this.wrapped = wrapped;
    }

    // Default: forward to the wrapped object. Subclasses override
    // to add their own contribution on top.
    @Override
    public double cost() {
        return wrapped.cost();
    }

    @Override
    public String description() {
        return wrapped.description();
    }
}
