# Minecraft Console Simulator

A console-based simulation of the Minecraft combat system, written purely
in Java. It demonstrates OOP principles, polymorphism, interfaces, generics,
multithreading, and robust exception handling.

## Technologies
- Java 17
- Pure Java, no frameworks
- `java.util.concurrent` (multithreaded combat engine)

## Project Architecture
- `model/base` — abstract classes (`Entity`, `Mob`)
- `model/effects` — active effect system (`ActiveEffect`, `EffectApplier`)
- `model/entities` — specific entities (mobs and players)
- `model/items` — weapons and armor
- `service` — business logic (combat engine, entity management, game loop, logging)
- `enums` — location types and mob behaviors
- `exceptions` — custom exceptions

## Key Concepts Implemented

- **Inheritance**: `Entity` → `Mob` → specific mobs; `Entity` → `Player`
- **Polymorphism**: `Player` overrides `getDamagePoints()` / `getAttackSpeed()` / `takeDamage()` to account for equipped weapon and armor
- **Interfaces**: `EffectApplier` is implemented only by entities with recurring effects (e.g. `WitherSkeleton`'s wither effect)
- **Generics**: `EntityManager<T extends Entity>` allows type-safe management of specific entity subsets (e.g. all `Mob`s, or all `Player`s)
- **Multithreading**: each combat duel runs as two independent threads (`AttackTask`), synchronized via `AtomicBoolean` and per-target locking to avoid race conditions and deadlocks; recurring effects (like Wither) run on a lazily-initialized `ScheduledExecutorService` per entity
- **Custom exceptions**: 5 unchecked exceptions for distinct game scenarios (duplicate entity, entity not found, entity already dead, invalid data, zero damage)
- **Collections**: `List`, `Optional`, `Comparator`, `ConcurrentHashMap` used for thread-safe state management
- **Stream API**: used throughout `EntityManager` for filtering (`getEntitiesFilteredBySpecialEffect`), sorting (`getEntitiesSorted`), and mapping (`getMobsByLocation`)
- **Immutability**: identity fields (`name`, behavior flags) are `final`, preventing state corruption and hashing bugs in collections

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA (or any other IDE)
3. Run `Main.java`

## Output Example
```text
=== PLAYER: Technoblade IS ATTEMPTING TO BEAT MINECRAFT ===
==================================================
🌳 CURRENT DIMENSION: PLAIN 🌳
==================================================
🐑 🟢 Cow is just chilling here. You walk past.
⚔️ 🔴 AGGRESSIVE MOB SPOTTED! [Vindicator] attacks!
Technoblade VS Vindicator
[Technoblade] hits first!
Verdict: [Technoblade] beats [Vindicator]. His final HP: 8.5
⭐ Success! Vindicator is no more. Moving forward...
```

## Known Limitations / Possible Improvements
- Add more mobs with special effects
- Improve the logic behind "beating Minecraft" in `AdventureEngine`
- Add unit tests for deterministic logic (validation, equals/hashCode, sorting/filtering)