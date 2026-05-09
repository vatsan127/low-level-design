# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

A personal learning repo for Low-Level Design (LLD) in Java. It is **not** a working application — `Main.java` is an empty stub. Each topic lives in its own folder under `src/main/java/lld/`, with theory in `notes.md` and Java code examples (when present) sitting alongside it.

The user's preferred workflow is **theory first, code later**: write the notes for a topic first (often with `todo:` markers for examples to add later), then add Java examples in the same folder.

## Layout

- `src/main/java/lld/_NN_topic/` — one folder per topic, numbered in study order. Folder names use a leading underscore (`_01_overview`) so they are valid Java package names.
  - `notes.md` — Markdown theory file for the topic.
  - Java sources for examples live next to it; their package is `lld._NN_topic` (matching the folder).
- Existing topics: `_01_overview`, `_02_solid`, `_03_strategy`, `_04_observer`.
- `src/main/java/Main.java` — empty placeholder, default package.
- `pom.xml` — minimal Maven POM, Java 21, no dependencies, no plugins, no test scope configured.

Maven's compiler ignores non-`.java` files, so keeping `notes.md` inside `src/main/java/` does not break the build.

## Build / run

Standard Maven on Java 21. There are no tests, no lint config, and no dependencies.

```
mvn compile         # compile (currently just Main.java)
mvn package         # produces target/low-level-design-1.0-SNAPSHOT.jar
```

There is no `mvn test` target worth running — no test sources exist and `pom.xml` declares no test framework. If you add code for a topic and want to demonstrate it, the convention so far is to extend `Main.java` or add classes under `src/main/java/`; pick a package that mirrors the notes folder name when adding the first real example.

## Conventions when adding to this repo

- **New topic** → create `src/main/java/lld/_NN_topicname/notes.md`, continuing the numbering. Match the existing tone: short prose, bullet-style, with `todo:` markers for examples the user wants to revisit later.
- **Code examples** for a pattern → put `.java` files in the same topic folder under package `lld._NN_topicname`. Package-private classes are fine; keep examples small enough to read top-to-bottom. The user values clear bad-vs-good contrasts (see how SOLID notes are framed) over production-grade code. Common sub-pattern: a `Bad.java` / `Good.java` pair, or a small demo with a `Main` `main()` runnable from IntelliJ.
- Don't add a test framework, linters, or build plugins unprompted — the POM is deliberately bare.
