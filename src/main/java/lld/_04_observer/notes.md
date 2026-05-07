# 04 — Observer Pattern

**Category:** Behavioural

## Definition

The Observer pattern defines a **one-to-many** dependency between objects. When one object (the **Observable** / **Subject** / **Publisher**) changes state, all its registered dependants (the **Observers** / **Subscribers**) are automatically notified.

It is the core of every "publish/subscribe" mechanism: GUI event listeners, message buses, model-view bindings, social media feeds, RxJava, JavaScript event emitters.

## Why use it

- **Decoupling.** The subject doesn't know who its observers are or what they do — only that they implement the observer contract. New observers can be added without touching the subject.
- **Broadcast.** A single state change can fan out to many interested parties.
- **Open / Closed.** New kinds of observers (a new logger, a new analytics sink) can subscribe without modifying the subject.

## Structure

```
+----------------------+   register/unregister  +----------------------+
|     Subject          | <--------------------- |   Observer (iface)   |
|  (Observable)        |                        +----------------------+
|  + register(o)       |                                ^
|  + unregister(o)     |          notifies              |
|  + notifyAll()       | ------------------>  +---------+---------+
+----------------------+                      |                   |
                                       ConcreteObserverA   ConcreteObserverB
```

- **Subject** maintains the list of observers and exposes register/unregister/notify.
- **Observer** is the interface every subscriber implements (typically one method, e.g. `update(...)`).
- **ConcreteSubject** holds the state worth notifying about.
- **ConcreteObserver** reacts to the change.

## Push vs Pull

The two models differ in **how data flows from the subject to the observer.**

| Aspect | Push | Pull |
|---|---|---|
| Who decides what data the observer sees | The subject — it pushes specific data into `update(...)` | The observer — it holds a reference to the subject and queries whatever it needs |
| Coupling to subject state | Looser (observer only sees what's pushed) | Tighter (observer knows the subject's API) |
| Wasted work | Subject may push data observers don't care about | Observers fetch only what they need |
| Typical signature | `update(EventData data)` | `update()` — observer then calls `subject.getX()`, `subject.getY()` |
| Best when | All observers want the same payload, payload is small | Observers need different views of the subject's state |

In Java's old `java.util.Observable` (deprecated since Java 9), `update(Observable o, Object arg)` actually supports both — `arg` is the push, `o` is the pull handle. Most modern code does explicit subject-specific interfaces.

## When NOT to use

- Only one observer ever — a direct method call is simpler.
- The notification graph has cycles. Observer makes update storms easy to introduce: A notifies B, B updates A, A re-notifies B, ...
- Order of notification matters strictly. The pattern says nothing about order; relying on it is fragile.

## Pitfalls

- **Memory leaks** — observers that forget to unsubscribe keep the subject alive (or vice versa, depending on reference direction). Always pair `register` with `unregister`.
- **Cascading updates** — one notify triggering chains of further notifies can be hard to reason about.
- **Blocking observers** — if `update()` does heavy work synchronously, all later observers wait. Consider async dispatch when observers are slow.

## Observer vs related patterns

| Pattern | Difference |
|---|---|
| **Mediator** | Mediator centralises *bidirectional* communication between many peers. Observer is *one-way* broadcast from subject to many. |
| **Pub/Sub (message bus)** | A scaled-up Observer with a broker in the middle. Publishers and subscribers don't know each other at all; the broker routes by topic. |
| **Event sourcing** | Stores the events themselves as the source of truth. Observer just notifies; event sourcing persists. |

## Code examples: social media feed

Two parallel implementations live side-by-side so you can compare:

**Push model** (`feed/push/`):

- `Subscriber.java` — receives `update(Post post)` with the data already in hand.
- `SocialAccount.java` — pushes the new post to every subscriber.
- `EmailSubscriber.java`, `MobilePushSubscriber.java` — concrete subscribers.
- `PushDemo.java` — runs the demo.

**Pull model** (`feed/pull/`):

- `Subscriber.java` — receives `update(SocialAccount account)` and queries it.
- `SocialAccount.java` — exposes `latestPost()` (and friends) for subscribers to pull from.
- `EmailSubscriber.java`, `MobilePushSubscriber.java` — concrete subscribers.
- `PullDemo.java` — runs the demo.
