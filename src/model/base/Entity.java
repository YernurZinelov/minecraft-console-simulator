package model.base;

import exceptions.InvalidDataException;
import model.effects.ActiveEffect;
import model.effects.EffectApplier;
import service.Log;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Entity {

    private final String name;
    private double healthPoints;
    private double damagePoints;
    private double attackSpeed;
    private final boolean hasSpecialEffect;

    private ScheduledExecutorService scheduler;
    private final Map<String, ActiveEffect> activeEffects = new ConcurrentHashMap<>();
    private final boolean ignoreArmor;

    public Entity(String name, double healthPoints, double damagePoints, double attackSpeed, boolean hasSpecialEffect, boolean ignoreArmor) {
        this.name = Objects.requireNonNull(name, "Entity name cannot be null!");

        if (healthPoints < 0) throw new InvalidDataException("Health cannot be less than zero!");
        this.healthPoints = healthPoints;

        if (damagePoints < 0) throw new InvalidDataException("Damage cannot be less than zero!");
        this.damagePoints = damagePoints;

        if (attackSpeed <= 0) throw new InvalidDataException("Attack speed cannot be less than or equal to zero!");
        this.attackSpeed = attackSpeed;

        this.hasSpecialEffect = hasSpecialEffect;
        this.ignoreArmor = ignoreArmor;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized double getHealthPoints() {
        return healthPoints;
    }

    public synchronized void setHealthPoints(double healthPoints) {
        if (healthPoints < 0) {
            this.healthPoints = 0.0;
        } else {
            this.healthPoints = healthPoints;
        }
    }

    public boolean isAlive() {
        return getHealthPoints() > 0;
    }

    public synchronized double getDamagePoints() {
        return damagePoints;
    }

    public synchronized void setDamagePoints(double damagePoints) {
        if (damagePoints < 0) throw new InvalidDataException("Damage cannot be less than zero!");
        this.damagePoints = damagePoints;
    }

    public synchronized double getAttackSpeed() {
        return Math.max(0.1, attackSpeed);
    }

    public synchronized void setAttackSpeed(double attackSpeed) {
        if (attackSpeed <= 0) throw new InvalidDataException("Attack speed cannot be less than or equal to zero!");
        this.attackSpeed = attackSpeed;
    }

    public boolean hasSpecialEffect() {
        return hasSpecialEffect;
    }

    public boolean isIgnoreArmor() {
        return ignoreArmor;
    }

    private synchronized void initSchedulerIfAbsent() {
        if (this.scheduler == null) {
            this.scheduler = Executors.newScheduledThreadPool(1, runnable -> {
               Thread thread = new Thread(runnable);
               thread.setDaemon(true);
               return thread;
            });
        }
    }

    public synchronized void takeDamage(double amount, boolean ignoreArmor) {
        if (amount < 0) return;
        setHealthPoints(getHealthPoints() - amount);
    }

    public void attack(Entity target) {
        double damage = getDamagePoints();
        String attackerName = getName();

        synchronized (target) {
            double healthBefore = target.getHealthPoints();
            target.takeDamage(damage, this.ignoreArmor);
            double damageDealt = healthBefore - target.getHealthPoints();

            Log.printf("%s hits %s for %.1f damage! (%s has %.1f HP left)\n",
                    attackerName, target.getName(), damageDealt, target.getName(), target.getHealthPoints());
        }
    }

    public void applyActiveEffect(String effectId, EffectApplier applier, int durationSeconds) {
        int period = applier.getTickPeriodSeconds();
        int totalTicks = durationSeconds / period;

        ActiveEffect existingEffect = activeEffects.get(effectId);

        if (existingEffect != null && existingEffect.getScheduledFuture() != null
                && !existingEffect.getScheduledFuture().isCancelled()) {
            existingEffect.resetTicks(totalTicks);
            return;
        }

        ActiveEffect newEffect = new ActiveEffect(applier, totalTicks);
        activeEffects.put(effectId, newEffect);

        Runnable tickTask = () -> {
            if (!isAlive()) {
                if (newEffect.getScheduledFuture() != null) {
                    newEffect.getScheduledFuture().cancel(true);
                }
                activeEffects.remove(effectId);
                return;
            }

            applier.applyEffect(this);

            int remainingTicks = newEffect.decrementAndGetTicks();

            if (!isAlive() || remainingTicks <= 0) {
                if (newEffect.getScheduledFuture() != null) {
                    newEffect.getScheduledFuture().cancel(true);
                }
                activeEffects.remove(effectId);
            }
        };

        initSchedulerIfAbsent();

        java.util.concurrent.ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(
                tickTask,
                period,
                period,
                TimeUnit.SECONDS
        );

        newEffect.setScheduledFuture(future);
    }

    public synchronized void shutDownScheduler() {
        if (this.scheduler != null) {
            this.scheduler.shutdown();
        }
    }

    public synchronized boolean hasActiveEffects() {
        return !activeEffects.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity other = (Entity) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public synchronized String toString() {
        return String.format(Locale.US,
                "%-25s | HP: %-4.1f | Damage: %-4.1f | Attack Speed: %.1f", name, healthPoints, damagePoints, attackSpeed
        );
    }
}
