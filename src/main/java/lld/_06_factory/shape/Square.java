package lld._06_factory.shape;

class Square implements Shape {
    private final double side;

    Square(double side) {
        if (side <= 0) throw new IllegalArgumentException("side must be positive");
        this.side = side;
    }

    @Override
    public void draw() {
        System.out.println("drawing square side=" + side);
    }

    @Override
    public double area() {
        return side * side;
    }
}
