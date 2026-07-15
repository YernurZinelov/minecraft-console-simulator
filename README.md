# Minecraft Console Simulator

A console-based simulation of the Minecraft combat system, written purely
in Java. It demonstrates the use of OOP principles, polymorphism, interfaces,
and exception handling.

## Technologies

- Java 17
- Pure Java, no frameworks

## Project Architecture

- `model/base` — abstract classes and interfaces (Entity, Mob, EffectApplier)
- `model/entities` — specific entities (mobs and players)
- `model/items` — weapons and armor
- `service` — business logic (combat, entity management, game loop)
- `enums` — location types and mob behaviors
- `exceptions` — custom exceptions

## Key Concepts Implemented

- **Inheritance**: `Entity` → `Mob` → specific mobs
- **Interfaces**: `EffectApplier` is implemented only by mobs with special effects (e.g. `WitherSkeleton`)
- **Polymorphism**: `Player` overrides `getDamagePoints()`/`takeDamage()` to account for equipped weapon/armor
- **Custom exceptions**: 5 unchecked exceptions for distinct game scenarios (duplicate entity, entity not found, entity already dead, invalid data, zero damage)
- **Collections**: `List`, `ArrayList`, `Optional`, `Comparator` used for sorting and filtering entities

## How to Run

1. Clone the repository
2. Open in IntelliJ IDEA (or any other IDE)
3. Run `Main.java`

## Output Example

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

## Known limitations / Possible Improvements

- Add more mobs with special effects
- Improve the logic behind "beating Minecraft" in AdventureEngine