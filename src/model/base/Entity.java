package model.base;

import exceptions.InvalidDataException;

import java.util.Locale;
import java.util.Objects;

public abstract class Entity {
    private String name;
    private double healthPoints;
    private double damagePoints;
    private double attackSpeed;
    private boolean hasSpecialEffect;

    public Entity(String name, double healthPoints, double damagePoints, double attackSpeed, boolean hasSpecialEffect) {
        this.name = name;

        if (healthPoints < 0) {
            healthPoints = 0.0;
        }
        this.healthPoints = healthPoints;

        if (damagePoints < 0) {
            throw new InvalidDataException("Damage cannot be less than zero!");
        }
        this.damagePoints = damagePoints;

        if (attackSpeed <= 0) {
            throw new InvalidDataException("Attack speed cannot be less than or equal to zero!");
        }
        this.attackSpeed = attackSpeed;
        this.hasSpecialEffect = hasSpecialEffect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(double healthPoints) {
        if (healthPoints < 0) {
            this.healthPoints = 0.0;
        } else {
            this.healthPoints = healthPoints;
        }
    }

    public double getDamagePoints() {
        return damagePoints;
    }

    public void setDamagePoints(double damagePoints) {
        if (damagePoints < 0) {
            throw new InvalidDataException("Damage cannot be less than zero!");
        }
        this.damagePoints = damagePoints;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(double attackSpeed) {
        if (attackSpeed <= 0) {
            throw new InvalidDataException("Attack speed cannot be less than or equal to zero!");
        }
        this.attackSpeed = attackSpeed;
    }

    public boolean hasSpecialEffect() {
        return hasSpecialEffect;
    }

    public void setHasSpecialEffect(boolean hasSpecialEffect) {
        this.hasSpecialEffect = hasSpecialEffect;
    }

    public void takeDamage(double amount) {
        healthPoints -= amount;
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
    public String toString() {
        return String.format(Locale.US,
                "%-25s | HP: %-4.1f | Damage: %.1f | Attack Speed: %.1f", name, healthPoints, damagePoints, attackSpeed
        );
    }
}
