package lld._02_solid.lsp;

// Model the real abstraction: both Rectangle and Square are Shapes.
// Neither inherits from the other, so neither lies about its contract.
// Code written against Shape works for both without surprise.
public class Good {

    interface Shape {
        int area();
    }

    static class Rectangle implements Shape {
        private final int width, height;
        Rectangle(int width, int height) { this.width = width; this.height = height; }
        public int area() { return width * height; }
    }

    static class Square implements Shape {
        private final int side;
        Square(int side) { this.side = side; }
        public int area() { return side * side; }
    }

    static void printArea(Shape s) {
        System.out.println("area = " + s.area());
    }

    public static void main(String[] args) {
        printArea(new Rectangle(5, 4)); // 20
        printArea(new Square(4));       // 16
    }
}
