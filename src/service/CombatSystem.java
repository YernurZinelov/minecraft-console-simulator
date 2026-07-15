package service;

import exceptions.EntityDeadException;
import exceptions.ZeroDamageException;
import model.base.EffectApplier;
import model.base.Entity;

import java.util.*;

public class CombatSystem {

    private final Random random = new Random();

    public void oneVSOne(Entity e1, Entity e2) {

        // === 1. Validation ===
        validateFighters(e1, e2);

        // === 2. Combat Initialization ===
        double timer = 0.0;
        int e1AttackCount = 1;
        int e2AttackCount = 1;

        System.out.println(e1.getName() + " VS " + e2.getName());

        // === Who hits first? ===
        determineFirstAttacker(e1, e2);

        boolean e1IsWithered = false;
        boolean e2IsWithered = false;

        double e1WitheringPeriod = 10.0;
        double e2WitheringPeriod = 10.0;

        double e2NextWitherDamageTime = 0.0;
        double e1NextWitherDamageTime = 0.0;

        // === 3. Combat Simulation per second ===
        while ((e1.getHealthPoints() > 0 && e2.getHealthPoints() > 0) || e1IsWithered || e2IsWithered) {
            if (e1.getHealthPoints() > 0 && e2.getHealthPoints() > 0 && timer >= (e1.getAttackSpeed() * e1AttackCount)) {
                e2.takeDamage(e1.getDamagePoints());

                if (e1 instanceof EffectApplier) {

                    if (!e2IsWithered) {
                        e2NextWitherDamageTime = timer + 2.0;
                    }
                    e2IsWithered = true;
                    e2WitheringPeriod = 10.0;
                }
                e1AttackCount++;
            }

            if (e1.getHealthPoints() > 0 && e2.getHealthPoints() > 0 && timer >= (e2.getAttackSpeed() * e2AttackCount)) {
                e1.takeDamage(e2.getDamagePoints());

                if (e2 instanceof EffectApplier) {

                    if (!e1IsWithered) {
                        e1NextWitherDamageTime = timer + 2.0;
                    }
                    e1IsWithered = true;
                    e1WitheringPeriod = 10.0;
                }
                e2AttackCount++;
            }

            if (e1IsWithered && e1.getHealthPoints() > 0) {
                e1WitheringPeriod -= 0.1;

                if (timer >= e1NextWitherDamageTime) {

                    if (e2 instanceof EffectApplier applier) {
                        applier.applyEffect(e1);
                    }

                    e1NextWitherDamageTime = timer + 2.0;
                }
                if (e1WitheringPeriod <= 0.0) {
                    e1IsWithered = false;
                }
            }

            if (e2IsWithered && e2.getHealthPoints() > 0) {
                e2WitheringPeriod -= 0.1;

                if (timer >= e2NextWitherDamageTime) {

                    if (e1 instanceof EffectApplier applier) {
                        applier.applyEffect(e2);
                    }

                    e2NextWitherDamageTime = timer + 2.0;
                }
                if (e2WitheringPeriod <= 0.0) {
                    e2IsWithered = false;
                }
            }

            if ((e1.getHealthPoints() <= 0 || !e1IsWithered) && (e2.getHealthPoints() <= 0 ||
                    !e2IsWithered) && (e1.getHealthPoints() <= 0 || e2.getHealthPoints() <= 0)) {
                break;
            }
            timer += 0.1;
        }

        // === Determining the winner ===
        determineWinner(e1, e2);
    }

    private void validateFighters(Entity e1, Entity e2) {
        if (e1.getHealthPoints() <= 0 && e2.getHealthPoints() <= 0)
            throw new EntityDeadException("Both entities [" + e1.getName() + "] and [" + e2.getName()+ "] are dead! " +
                    "Verdict: Request for combat was cancelled.");
        if (e1.getHealthPoints() <= 0 && e2.getHealthPoints() > 0)
            throw new EntityDeadException("Entity [" + e1.getName() + "] is already dead. " +
                    "Entity [" + e2.getName() + "] is the winner.");
        if (e2.getHealthPoints() <= 0)
            throw new EntityDeadException("Entity [" + e2.getName() + "] is already dead. " +
                    "Entity [" + e1.getName() + "] is the winner.");

        if (e1.getDamagePoints() <= 0 && e2.getDamagePoints() <= 0)
            throw new ZeroDamageException("Both entities [" + e1.getName() + "] and [" + e2.getName()+ "] have 0 damaging points! " +
                    "Verdict: Request for combat was cancelled.");
    }

    private void determineFirstAttacker(Entity e1, Entity e2) {
        boolean e1Starts = random.nextBoolean();

        if (e1Starts) {
            e2.takeDamage(e1.getDamagePoints());
            System.out.println("[" + e1.getName() + "] hits first!");
        } else {
            e1.takeDamage(e2.getDamagePoints());
            System.out.println("[" + e2.getName() + "] hits first!");
        }
    }

    private void determineWinner(Entity e1, Entity e2) {
        if (e1.getHealthPoints() <= 0 && e2.getHealthPoints() <= 0) {
            System.out.println("Both [" + e1.getName() + "] and [" + e2.getName() + "] are dead. Verdict: Draw");
            return;
        }
        if (e1.getHealthPoints() > e2.getHealthPoints()) {
            System.out.println("Verdict: [" + e1.getName() + "] beats [" + e2.getName() + "]. " +
                    "His final HP: " + e1.getHealthPoints());
            return;
        }
        if (e1.getHealthPoints() < e2.getHealthPoints()) {
            System.out.println("Verdict: [" + e2.getName() + "] beats [" + e1.getName() + "]. " +
                    "His final HP: " + e2.getHealthPoints());
        }
    }
}
