package lld._05_decorator.coffee;

// The Component interface. Both concrete coffees AND decorators
// implement this -- that is the whole trick that lets a decorator
// wrap any other Coffee, including another decorator.
public interface Coffee {
    double cost();
    String description();
}
