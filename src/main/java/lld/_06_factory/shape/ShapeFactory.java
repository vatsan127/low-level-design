package lld._06_factory.shape;

// Simple Factory. Owns the if/switch over shape types so callers
// don't have to. The concrete shape classes are package-private,
// so callers literally cannot instantiate them directly -- they
// must come through here.
public class ShapeFactory {

    // Private constructor: this is a static utility, not a thing to instantiate.
    private ShapeFactory() {}

    public static Shape create(String type, double size) {
        if (type == null) throw new IllegalArgumentException("type is null");
        switch (type.toLowerCase()) {
            case "circle":   return new Circle(size);
            case "square":   return new Square(size);
            case "triangle": return new Triangle(size);
            default:
                // Don't return null. Throw a clear error so the caller
                // sees the failure at the source, not as an NPE later.
                throw new IllegalArgumentException("unknown shape: " + type);
        }
    }
}
