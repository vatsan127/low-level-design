# 06 — Factory Pattern

**Category:** Creational

## Definition

The Factory pattern **encapsulates object creation in one place**, so the calling code asks for *what it wants* without needing to know *how it's built* or *which concrete class it gets*.

Instead of:

```java
Shape s = userChoice.equals("circle") ? new Circle() : new Square();
```

you write:

```java
Shape s = ShapeFactory.create(userChoice);
```

The factory owns the `if/switch`. The caller is decoupled from the concrete classes.

## Problems it solves

- **Scattered `new` calls.** Without a factory, the same `if (type == X) new A() else new B()` block gets copy-pasted across the codebase. Every new type forces edits in many places.
- **Tight coupling to concrete classes.** Every caller that does `new Circle()` directly imports `Circle`. Replacing `Circle` with `OptimisedCircle` later means editing every caller.
- **Hidden construction complexity.** Some objects need configuration, validation, or a registry lookup before they're usable. The factory hides that wiring; callers don't have to know.

## Three flavours (often confused)

People say "factory pattern" loosely. There are actually three distinct things in the GoF taxonomy.

| Name | What it is | Caller's view |
|---|---|---|
| **Simple Factory** *(not in GoF, but the most common in practice)* | A single class with a static `create(...)` method that returns one of several types based on a parameter. | `ShapeFactory.create("circle")` → `Shape` |
| **Factory Method** | An abstract method on a base class/interface that **subclasses override** to decide which concrete type to return. The pattern is the *inheritance hook*, not just a factory class. | `dialog.createButton()` — `WindowsDialog` returns `WindowsButton`, `MacDialog` returns `MacButton`. |
| **Abstract Factory** | An interface for creating **families** of related objects. One factory implementation produces a whole consistent set (e.g. `WindowsButton` + `WindowsCheckbox` + `WindowsScrollbar`). | `uiFactory.createButton()`, `uiFactory.createCheckbox()`, `...` — all from one factory. |

The runnable example below is **Simple Factory**, since that's what most real-world code calls "the factory pattern". A Factory Method note is at the bottom.

## Structure (Simple Factory)

```
+-------------+    create("circle")    +-----------------+    new Circle()    +----------+
|   Client    | ---------------------> |  ShapeFactory   | -----------------> |  Circle  |
+-------------+                        +-----------------+                    +----------+
                                              |   new Square()
                                              v                               +----------+
                                                                              |  Square  |
                                                                              +----------+
```

- **Product** — the abstraction (`Shape`).
- **ConcreteProduct** — implementations (`Circle`, `Square`, `Triangle`).
- **Factory** — class with the creation method that returns a `Product`.
- **Client** — code that asks the factory for a product and uses it via the abstraction.

## When to use

- You have a family of types that share an interface, and the choice between them depends on input/config.
- Construction is non-trivial — needs validation, lookups, or wiring you don't want callers to repeat.
- You expect the set of concrete types to grow and want one place to register them.

## When NOT to use

- Only one concrete type and likely will only ever be one. Direct `new` is simpler.
- Construction is a single line with no logic. A factory adds ceremony for nothing.
- The caller actually *needs* the concrete type (e.g. for a specific method not on the interface). Hiding it forces awkward casts.

## Pitfalls

- **God factory.** A factory that creates everything in the system tends to grow unbounded and become a maintenance burden. Keep one factory per family of products.
- **Returning `null` for unknown input.** Throw a clear exception (`IllegalArgumentException`) instead — silent `null` causes NPEs far from the source.
- **OCP violation inside the factory.** Adding a new type still requires editing the factory's `switch`. That's usually acceptable (the change is localised), but if the type list is genuinely open-ended, look at a **registry-based factory** where new types register themselves.

## Factory vs related patterns

| Pattern | Difference |
|---|---|
| **Builder** | Builder constructs *one complex object* step-by-step (many parameters, optional fields). Factory chooses *which type* of object to construct. |
| **Strategy** | Strategy swaps an *algorithm* at runtime. Factory creates an *object* and is usually called once at construction time. They often pair up — a factory builds the right Strategy. |
| **Abstract Factory** | Bigger sibling: creates *families* of related products instead of one product. |
| **Prototype** | Creates new objects by *cloning* an existing one instead of constructing fresh. Useful when construction is expensive or config-heavy. |

## Code example: shape factory

`shape/Shape.java` — the product interface.
`shape/Circle.java`, `Square.java`, `Triangle.java` — concrete products.
`shape/ShapeFactory.java` — the factory with `create(String type)`.
`shape/Demo.java` — `main` showing the factory in use, including an unknown-type error case.

## Bonus: Factory Method (sketch, not a full example)

The Factory Method variant uses **inheritance** rather than a static dispatcher. The base class declares an abstract `createX()`, and each subclass overrides it.

```java
abstract class Dialog {
    void render() {
        Button b = createButton(); // Factory Method
        b.draw();
    }
    abstract Button createButton(); // subclasses decide which Button
}

class WindowsDialog extends Dialog {
    @Override Button createButton() { return new WindowsButton(); }
}

class MacDialog extends Dialog {
    @Override Button createButton() { return new MacButton(); }
}
```

Use Factory Method when the *workflow* is the same across variants but one creation step differs per variant. The base class controls the workflow; subclasses fill in the type-specific creation.
