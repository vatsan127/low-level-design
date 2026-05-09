package lld._06_factory.shape;

// Package-private: callers should never instantiate Circle directly.
// They go through ShapeFactory, which owns the choice.
class Circle implements Shape {
    private final double radius;

    Circle(double radius) {
        if (radius <= 0) throw new IllegalArgumentException("radius must be positive");
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("drawing circle r=" + radius);
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
