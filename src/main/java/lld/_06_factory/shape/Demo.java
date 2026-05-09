package lld._06_factory.shape;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        // Caller asks for what it wants. It does NOT import Circle/Square/Triangle.
        // It only depends on Shape (the interface) and ShapeFactory (the entry point).
        List<Shape> shapes = List.of(
                ShapeFactory.create("circle", 5),
                ShapeFactory.create("square", 4),
                ShapeFactory.create("triangle", 6)
        );

        for (Shape s : shapes) {
            s.draw();
            System.out.printf("  area = %.2f%n", s.area());
        }

        // Unknown type: factory throws a clear error at the source.
        try {
            ShapeFactory.create("hexagon", 3);
        } catch (IllegalArgumentException e) {
            System.out.println("caught: " + e.getMessage());
        }
    }
}
