# 02 — SOLID Principles

Five class-design principles coined by Robert C. Martin (Uncle Bob). They guide you toward code that is easy to change as requirements grow.

| Letter | Principle | One-liner |
|---|---|---|
| **S** | Single Responsibility | A class should have only one reason to change. |
| **O** | Open / Closed | Open for extension, closed for modification. |
| **L** | Liskov Substitution | Subtypes must be usable wherever their base type is expected. |
| **I** | Interface Segregation | Don't force clients to depend on methods they don't use. |
| **D** | Dependency Inversion | Depend on abstractions, not concretions. |

## Why bother?

- Helps you write better code that ages well.
- Avoids duplication — changes happen in one place.
- Easier to maintain — bugs are localised.
- Easier to understand — each class has a clear job.
- Flexible — new features slot in without rewrites.
- Reduces complexity — small, focused units beat large tangled ones.

---

## S — Single Responsibility Principle (SRP)

> A class should have only **one reason to change**.

Each class should do one thing and do it well. If you find yourself describing a class with the word "and" ("it parses invoices **and** emails them **and** saves them to disk"), it has too many responsibilities.

A "reason to change" is a stakeholder concern: persistence, formatting, business rules, transport, etc. Each concern should live in its own class.

**Code example:** `srp/Bad.java`, `srp/Good.java`. Bad mixes invoice calculation, persistence, and email into one class; Good splits them into `Invoice`, `InvoiceRepository`, `InvoiceMailer`.

---

## O — Open / Closed Principle (OCP)

> A class should be **open for extension but closed for modification**.

You should be able to add new behaviour without editing existing, tested, deployed code. The mechanism is usually polymorphism: define an abstraction, and add new behaviour by adding new implementations rather than by editing a long `if/else` or `switch`.

**Why it matters:** existing code is already in production. Modifying it introduces regression risk and forces re-testing. Extending — adding a new class — leaves working code untouched.

**Code example:** `ocp/Bad.java`, `ocp/Good.java`. Bad uses a `switch` over discount types inside a single class; Good defines a `DiscountPolicy` interface so new discount types can be added by writing a new class.

---

## L — Liskov Substitution Principle (LSP)

> Objects of a superclass should be **replaceable** with objects of any subclass without breaking the program.

If `B` extends `A`, code written against `A` should continue to work when handed a `B`. Subclasses must honour the contract of the parent — same expected inputs, same kinds of outputs, same invariants.

A subclass can **extend** behaviour but should not **narrow** it. Throwing `UnsupportedOperationException` from an overridden method, tightening preconditions, or weakening postconditions all violate LSP.

Classic violation: `Square extends Rectangle`. A `Rectangle` lets you set width and height independently; a `Square` cannot. Code that sets width and expects height unchanged breaks when handed a `Square`.

**Code example:** `lsp/Bad.java`, `lsp/Good.java`. Bad has `Square extends Rectangle` and mutates both sides on each setter; Good models `Shape` as an abstraction with `Rectangle` and `Square` as separate implementations.

---

## I — Interface Segregation Principle (ISP)

> Clients should not be **forced to depend on methods they do not use**.

Many small, focused interfaces beat one big "fat" interface. When an interface bundles unrelated methods, every implementation has to provide all of them — usually with empty bodies or `throw new UnsupportedOperationException()`.

Smell signs of an ISP violation:
- Implementations with empty methods.
- Implementations that throw "not supported" exceptions.
- Mocks/test doubles that have to stub out half the interface they don't care about.

**Code example:** `isp/Bad.java`, `isp/Good.java`. Bad has a single `Worker` interface with `work()`, `eat()`, `sleep()`, forcing a `RobotWorker` to throw on `eat()`; Good splits them into `Workable`, `Eatable`, `Sleepable`.

---

## D — Dependency Inversion Principle (DIP)

> High-level modules should not depend on low-level modules. **Both should depend on abstractions.**

The "inversion" is in the direction of the arrow. Without DIP, your business logic (high-level) directly imports concrete infrastructure (low-level: a specific DB driver, a specific email library). With DIP, both sides agree on an interface, and the concrete implementation is **injected** at runtime.

Benefits:
- **Testability** — swap the real DB for an in-memory fake in tests.
- **Flexibility** — replace MySQL with Postgres without touching business logic.
- **Decoupling** — business code does not know or care which concrete class it talks to.

**DIP vs DI (Dependency Injection):** DIP is the *principle* (depend on abstractions). DI is one common *technique* for applying it (pass dependencies in via constructor/setter rather than `new`-ing them inside). Note: DI alone doesn't satisfy DIP — you can inject a concrete class without an interface, which is DI but still couples high-level code to a specific implementation. The principle requires the **abstraction** (the interface), not just the injection mechanism.

**Code example:** `dip/Bad.java`, `dip/Good.java`. Bad has `NotificationService` that directly creates an `EmailSender`; Good defines a `MessageSender` interface and the concrete `EmailSender` is injected into the constructor.
