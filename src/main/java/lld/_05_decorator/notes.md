# 05 — Decorator Pattern

**Category:** Structural

## Definition

The Decorator pattern lets you **add new behaviour to an object dynamically by wrapping it** in another object that shares the same interface. The wrapper (the "decorator") forwards calls to the wrapped object and adds its own behaviour before, after, or around the call.

You can stack wrappers — `Whip(Sugar(Milk(Espresso)))` — to compose features at runtime, without changing the original class or creating a subclass for every combination.

## The problem it solves

Imagine a coffee menu. Customers want plain Espresso, Espresso + Milk, Espresso + Milk + Sugar, Espresso + Sugar + Whip, … Without Decorator, the obvious approach is **inheritance**:

```
Coffee
├── EspressoWithMilk
├── EspressoWithSugar
├── EspressoWithMilkAndSugar
├── EspressoWithMilkAndSugarAndWhip
├── ... (combinatorial explosion)
```

With three add-ons you'd need 2³ − 1 = 7 extra subclasses (every non-empty subset). Add a second base coffee (Latte) and the count doubles. Inheritance can't express "any combination of add-ons, chosen at runtime, applied to any base."

Decorator solves this by **composing** add-ons at runtime instead of baking them into class hierarchies. Each add-on is its own class, and you stack them like layers.

## Structure

```
+------------------------+
|     <<interface>>      |
|       Component        |  <- defines the contract (e.g. Coffee)
|     + cost()           |
|     + description()    |
+------------------------+
            ^   ^
            |   |
            |   +-------- wraps --------+
            |                           |
+--------------------+      +-------------------------+
| ConcreteComponent  |      |       Decorator         |  <- abstract base for wrappers
|  (e.g. Espresso)   |      |   - wrapped: Component  |
+--------------------+      +-------------------------+
                                        ^
                                        |
              +-------------------------+-------------------------+
              |                         |                         |
   +-------------------+    +-------------------+    +-------------------+
   |   MilkDecorator   |    |  SugarDecorator   |    |   WhipDecorator   |
   +-------------------+    +-------------------+    +-------------------+
```

- **Component** — the interface (or abstract class) defining what every concrete object and decorator must implement.
- **ConcreteComponent** — a real object you can wrap (e.g. `Espresso`).
- **Decorator** — abstract base that holds a reference to a wrapped `Component` and forwards calls to it. Each concrete decorator extends this and adds its own behaviour.
- **ConcreteDecorator** — adds one specific feature (`MilkDecorator`, `SugarDecorator`, …).

The key trick: **a Decorator IS-A Component AND HAS-A Component.** It's both the interface and a holder of one. That's what lets you stack them: a decorator wrapping a decorator wrapping the base object — every layer satisfies the same interface.

## When to use

- You need to add behaviour to individual objects at runtime without affecting other objects of the same class.
- The set of optional features is large and combinable in many ways (the inheritance explosion problem).
- You want each feature to live in its own class (Single Responsibility) instead of a giant configurable class with feature flags.

## When NOT to use

- The features always appear together in fixed combinations — a single subclass or builder is simpler.
- The Component interface is large. Every decorator must forward every method, which becomes tedious and error-prone. Java doesn't have built-in delegation; you write the boilerplate by hand.
- You need to *modify* internal state of the wrapped object, not just augment its behaviour. Decorator only works through the public interface.

## Pitfalls

- **Order can matter.** `Whip(Sugar(coffee))` and `Sugar(Whip(coffee))` may produce different results if decorators interact (e.g. a `DiscountDecorator` applied before vs after a `TaxDecorator`). Pick a consistent order and document it.
- **Identity is lost.** If callers compare with `==` or rely on `instanceof ConcreteComponent`, wrapping breaks them. Decorator hides the original behind layers.
- **Debugging is harder.** A stack trace through five decorators is harder to read than a single subclass.
- **Equals/hashCode footguns.** A decorator and its wrapped object are different references. Default `Object.equals` returns `false` between them. You might be tempted to override `equals()` on the decorator to forward to the wrapped object — but that breaks the symmetry rule of `equals` (`a.equals(b)` would be true while `b.equals(a)` is false), which corrupts hash-based collections. Best practice: don't override `equals` on decorators; treat them as distinct identities.
- **Performance.** Each call traverses every layer in the stack — 10 decorators = 10 method calls per invocation. Modern JVMs inline aggressively so this rarely matters, but it's worth knowing if you wrap hot-path objects in deep stacks.

## Real-world examples in Java

- **`java.io` streams.** `new BufferedReader(new InputStreamReader(new FileInputStream(...)))`. Each layer adds behaviour by wrapping the previous one. (Strictly: `BufferedReader` decorating `InputStreamReader` is the pure Decorator part — both are `Reader`s. `InputStreamReader` wrapping a byte-`InputStream` is closer to an Adapter, since the wrapped type's interface differs.)
- **`Collections.unmodifiableList(list)`.** Returns a wrapper that forwards reads and throws on writes — a Decorator that adds an immutability constraint.
- **Servlet filters / Spring `HandlerInterceptor`.** Each filter wraps the next one in the chain.

## Decorator vs related patterns

| Pattern | Difference |
|---|---|
| **Inheritance** | Adds behaviour at *compile time*, fixed by class hierarchy. Decorator adds it at *runtime*, composable per-instance. |
| **Adapter** | Changes the interface of an object so it can be used where a different interface is expected. Decorator keeps the interface the same and adds behaviour. |
| **Proxy** | Same shape (wrapper that implements the same interface), different intent. Proxy controls *access* (lazy load, security, remote). Decorator adds *features*. |
| **Strategy** | Strategy swaps an algorithm via a separate object passed in. Decorator wraps the whole object and intercepts calls. |
| **Composite** | Composite makes a tree of objects look like one object. Decorator makes a chain of wrappers look like one object. Often confused; Composite is "many leaves under a root", Decorator is "one core wrapped in many layers". |

## Code example: coffee shop

A `Coffee` interface with `cost()` and `description()`. `Espresso` is the base concrete coffee. `MilkDecorator`, `SugarDecorator`, `WhipDecorator` each add to the cost and append to the description. Decorators stack in any order.

- `coffee/Coffee.java` — the Component interface.
- `coffee/Espresso.java` — the base ConcreteComponent.
- `coffee/CoffeeDecorator.java` — abstract Decorator that holds a wrapped `Coffee` and forwards calls.
- `coffee/MilkDecorator.java`, `SugarDecorator.java`, `WhipDecorator.java` — concrete decorators.
- `coffee/Demo.java` — `main` showing several stacked combinations.
