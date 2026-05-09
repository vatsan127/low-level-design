# 01 — LLD Overview

## HLD vs LLD

| Aspect | High-Level Design (HLD) | Low-Level Design (LLD) |
|---|---|---|
| Focus | System-wide architecture: services, databases, queues, networks | Classes and objects within a single component |
| Audience | Architects, tech leads | Developers writing the code |
| Output | Block diagrams, sequence diagrams across services | Class diagrams, method signatures, interaction within a service |
| Question it answers | *How do components communicate?* | *How is each component built internally?* |
| Examples of decisions | "Use Kafka between order service and inventory service" | "`Order` aggregates `OrderLine`s; `PricingStrategy` is injected via constructor" |

The **goal of LLD** is code that is:

- **Clean** — readable, single-purpose classes
- **Flexible** — new requirements can be added without rewriting existing code
- **Maintainable** — bugs are easy to locate and fix; tests are easy to write

Design patterns are reusable solutions to recurring LLD problems. Knowing them gives you a shared vocabulary ("this is a Strategy", "use a Decorator here") and prevents you from reinventing wheels poorly.

## Why design patterns matter

- **Common vocabulary** — saying "it's an Observer" communicates intent faster than describing the wiring.
- **Avoid known pitfalls** — patterns encode lessons learned from past mistakes (e.g., Singletons that hide global state, factories that decouple creation).
- **Reusable structure** — once you recognise the shape of a problem, the solution shape is already in your toolkit.
- **Easier collaboration** — code reviewers and future maintainers can navigate by recognising patterns.

## Categories of design patterns

LLD patterns are grouped into three categories based on what they're for. The classic catalog is the *Gang of Four* (GoF) book *Design Patterns: Elements of Reusable Object-Oriented Software* (1994), which lists 23 patterns. Tables below include a few popular non-GoF additions (Object Pool, Dependency Injection-flavoured Factory variants, etc.) since you'll meet them in real code.

### Creational — *how objects are created*

Control object construction so the calling code does not need to know the concrete type or wiring details.

| Pattern | One-line purpose |
|---|---|
| **Singleton** | Ensure exactly one instance of a class exists, with global access to it. |
| **Factory** | Defer the choice of which concrete class to instantiate to a separate method/class. |
| **Abstract Factory** | Create *families* of related objects without specifying their concrete classes. |
| **Builder** | Construct complex objects step-by-step, separating construction from representation. |
| **Prototype** | Create new objects by cloning an existing one instead of instantiating from scratch. |
| **Object Pool** | Reuse a fixed set of expensive-to-create objects (DB connections, threads). |

### Structural — *how classes and objects are composed*

Arrange classes/objects into larger structures while keeping them flexible and efficient.

| Pattern | One-line purpose |
|---|---|
| **Decorator** | Add behaviour to an object dynamically by wrapping it. |
| **Adapter** | Make an incompatible interface usable by translating calls. |
| **Proxy** | Stand in for another object to control access (lazy load, security, remote). |
| **Facade** | Provide a simple unified interface over a complex subsystem. |
| **Flyweight** | Share common state across many fine-grained objects to save memory. |
| **Composite** | Treat individual objects and compositions of them uniformly (tree structure). |

### Behavioural — *how objects communicate*

Define how responsibilities are distributed between objects and how they collaborate.

| Pattern | One-line purpose |
|---|---|
| **State** | Let an object alter its behaviour when its internal state changes. |
| **Strategy** | Define a family of algorithms and make them interchangeable at runtime. |
| **Observer** | Notify many dependent objects automatically when one object changes. |
| **Iterator** | Traverse the elements of a collection without exposing its internals. |
| **Command** | Encapsulate a request as an object so it can be queued, logged, undone. |
| **Template Method** | Define the skeleton of an algorithm in a base class, let subclasses fill in steps. |

## Object relationships: IS-A and HAS-A

Two ways one class can be related to another. Picking the right one is one of the most consequential LLD decisions.

### IS-A — inheritance

`B` IS-A `A` means `B` extends `A`. A `Dog` IS-A `Animal`. Anywhere code expects an `Animal`, a `Dog` works.

```java
class Animal { void breathe() { ... } }
class Dog extends Animal { void bark() { ... } }
```

**Use when** the subclass is genuinely a more specific kind of the superclass *and* honours the parent's contract (see Liskov Substitution Principle in topic 02).

**Pitfall:** inheritance is the tightest form of coupling. Changes to the parent ripple into every subclass. Prefer composition (HAS-A) when in doubt.

### HAS-A — composition / aggregation / association

`Car` HAS-A `Engine`. The car holds a reference to the engine; it is not an engine. There are three flavours of HAS-A, distinguished by the **lifetime relationship** between the two objects.

| Relationship | Strength | Lifetime coupling | Example |
|---|---|---|---|
| **Association** (`->`) | Loose | None — both exist independently and just refer to each other | A `Doctor` knows their `Patient`; deleting the doctor doesn't delete the patient. |
| **Aggregation** | Weak | Container holds parts, but parts can outlive the container | A `Library` has `Book`s. Close the library; the books still exist and can move to another library. |
| **Composition** | Strong | Parts cannot exist without the container | A `House` has `Room`s. Demolish the house; the rooms are gone. |

In Java, all three look syntactically the same — a field holding a reference. The difference is **how lifecycle is managed in code**:

- **Aggregation:** the container *receives* the parts (constructor parameter, setter). It does not create or destroy them.
  ```java
  class Library {
      private final List<Book> books;
      Library(List<Book> books) { this.books = books; } // books exist before, outlast after
  }
  ```
- **Composition:** the container *creates* the parts and is responsible for their lifecycle.
  ```java
  class House {
      private final List<Room> rooms = new ArrayList<>();
      House(int n) { for (int i = 0; i < n; i++) rooms.add(new Room()); } // rooms born here, die here
  }
  ```

### IS-A vs HAS-A — when to pick which

| Question | If yes → | If no → |
|---|---|---|
| Is the new class genuinely a subtype of the existing one (passes the LSP test)? | IS-A (inheritance) | HAS-A (composition) |
| Does the new class only need *some* behaviour from the existing one? | HAS-A | IS-A may be ok |
| Will the new class need to vary that behaviour at runtime? | HAS-A (Strategy pattern) | IS-A is fine |

Rule of thumb: **favour composition over inheritance**. It keeps coupling loose, makes behaviour swappable, and avoids deep inheritance chains.
