package service;

import exceptions.EntityDeadException;
import exceptions.ZeroDamageException;
import model.base.Entity;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CombatSystem {

    private final Random random = new Random();

    public void oneVSOne(Entity e1, Entity e2) {

        // === 1. Validation ===
        validateFighters(e1, e2);

        // === 2. Determining who attacks first ===
        boolean e1Starts = random.nextBoolean();

        AtomicBoolean battleIsOver = new AtomicBoolean(false);

        AttackTask task1 = new AttackTask(e1, e2, battleIsOver, e1Starts);
        AttackTask task2 = new AttackTask(e2, e1, battleIsOver, !e1Starts);

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Log.println("The battle was unexpectedly interrupted!");
            battleIsOver.set(true);
            Thread.currentThread().interrupt();
            return;
        }

        // === 3. Waiting for effects to vanish ===
        if (e1.isAlive() && e1.hasActiveEffects()) {
            waitForEffects(e1);
        }
        if (e2.isAlive() && e2.hasActiveEffects()) {
            waitForEffects(e2);
        }

        if (!e1.isAlive()) e1.shutDownScheduler();
        if (!e2.isAlive()) e2.shutDownScheduler();

        // === 4. Determining the winner ===
        determineWinner(e1, e2);
    }

    private void waitForEffects(Entity entity) {
        int timeOut = 0;
        while (entity.isAlive() && entity.hasActiveEffects() && timeOut < 100) {
            try {
                Thread.sleep(100);
                timeOut++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
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

    private void determineWinner(Entity e1, Entity e2) {
        if (!e1.isAlive() && !e2.isAlive()) {
            Log.println("Both [" + e1.getName() + "] and [" + e2.getName() + "] are dead. Verdict: Draw");
            return;
        }
        if (e1.isAlive()) {
            Log.println("Verdict: [" + e1.getName() + "] beats [" + e2.getName() + "]. " +
                    "His final HP: " + e1.getHealthPoints());
        } else {
            Log.println("Verdict: [" + e2.getName() + "] beats [" + e1.getName() + "]. " +
                    "His final HP: " + e2.getHealthPoints());
        }
    }
}
