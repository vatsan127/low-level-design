package lld._02_solid.lsp;

// LSP violation: Square IS-A Rectangle by inheritance, but it cannot
// honour Rectangle's contract that width and height are independent.
// Any code written against Rectangle that relies on that independence
// breaks when handed a Square.
public class Bad {

    static class Rectangle {
        protected int width, height;
        void setWidth(int w)  { this.width = w; }
        void setHeight(int h) { this.height = h; }
        int area() { return width * height; }
    }

    static class Square extends Rectangle {
        // Square forces width == height, breaking the parent's contract.
        @Override void setWidth(int w)  { this.width = w; this.height = w; }
        @Override void setHeight(int h) { this.width = h; this.height = h; }
    }

    // Caller written against the Rectangle contract.
    static void resizeAndCheck(Rectangle r) {
        r.setWidth(5);
        r.setHeight(4);
        // Expectation: 5 * 4 = 20.  With a Square, you actually get 16. Surprise.
        System.out.println("expected 20, got " + r.area());
    }

    public static void main(String[] args) {
        resizeAndCheck(new Rectangle()); // 20  - ok
        resizeAndCheck(new Square());    // 16  - LSP broken
    }
}
