package lld._06_factory.shape;

// Equilateral triangle, just to keep the example small.
class Triangle implements Shape {
    private final double side;

    Triangle(double side) {
        if (side <= 0) throw new IllegalArgumentException("side must be positive");
        this.side = side;
    }

    @Override
    public void draw() {
        System.out.println("drawing triangle side=" + side);
    }

    @Override
    public double area() {
        return (Math.sqrt(3) / 4) * side * side;
    }
}
