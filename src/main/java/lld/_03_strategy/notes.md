# 03 — Strategy Pattern

**Category:** Behavioural

## Definition

The Strategy pattern defines a **family of algorithms**, encapsulates each in its own class, and makes them **interchangeable at runtime**. The client holds a reference to the abstract strategy and delegates the work to whichever concrete implementation is plugged in.

## Problems it solves

- **Code duplication.** Without Strategy, each algorithm variant tends to be copy-pasted into a giant `if/else` or `switch`.
- **Tight coupling.** The caller knows every variant and has to be edited whenever a new one appears.
- **Hard to test.** A monolithic class with every algorithm baked in is hard to unit-test in isolation.

It is essentially the **runtime, composition-based** answer to the same problem OCP solves: pick behaviour by swapping objects, not by editing code.

## Structure

```
+----------+   picks &    +----------+   delegates to   +-------------------+
|  Client  | -----------> | Context  | ---------------> |  <<interface>>    |
|          |   injects    |          |                  |     Strategy      |
+----------+              +----------+                  +-------------------+
                                                                 ^
                                                                 |
                                          +----------------------+----------------------+
                                          |                      |                      |
                                 +-----------------+   +-----------------+   +-----------------+
                                 | ConcreteStratA  |   | ConcreteStratB  |   | ConcreteStratC  |
                                 +-----------------+   +-----------------+   +-----------------+
```

- **Strategy** — interface defining the algorithm.
- **ConcreteStrategy** — each implementation of the algorithm.
- **Context** — the class that holds a reference to a Strategy and delegates work to it. Does not care which concrete strategy is plugged in.
- **Client** — the code that picks a concrete strategy and hands it to the Context (constructor injection, setter, factory, etc.).

## When to use

- You have multiple ways to perform the same task (sorting orders by price/date/relevance, calculating shipping by carrier, validating data by region).
- You want the choice of algorithm to be **switchable at runtime** based on user input, configuration, or context.
- You're tempted to write `if (type == X) ... else if (type == Y) ...` over a small set of behaviours that share a signature.

## When NOT to use

- There's only one algorithm and likely will only ever be one.
- The algorithms differ so wildly they don't share a meaningful interface.
- A simple `enum` with a method is sufficient (Java enums can carry behaviour and are often a lighter alternative).

## Strategy vs related patterns

| Pattern | Difference |
|---|---|
| **State** | Same shape, different intent. State changes its strategy *itself* based on internal events; Strategy is chosen *from outside* by the client. |
| **Template Method** | Inheritance-based: parent defines the algorithm skeleton, children fill in steps. Strategy is composition-based and swappable at runtime. |
| **Command** | Encapsulates a *request* to be executed/undone. Strategy encapsulates an *algorithm* selected for a single operation. |

## Code example: payment modes

`payment/PaymentStrategy.java` — strategy interface.
`payment/CreditCardPayment.java`, `UpiPayment.java`, `WalletPayment.java` — concrete strategies.
`payment/Checkout.java` — the context that delegates to whatever strategy is supplied.
`payment/Demo.java` — `main` showing the same checkout running with three different strategies.
