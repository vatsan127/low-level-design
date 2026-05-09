package lld._06_factory.shape;

// The Product interface. Callers code against this, not against
// the concrete shape classes -- that is what makes the factory
// useful.
public interface Shape {
    void draw();
    double area();
}
