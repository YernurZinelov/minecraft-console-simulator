package model.items;

import exceptions.InvalidDataException;

import java.util.Objects;

public abstract class Weapon {

    private final String name;
    private final double bonusDamage;
    private final double bonusAttackSpeed;

    public Weapon(String name, double bonusDamage, double bonusAttackSpeed) {
        this.name = Objects.requireNonNull(name, "Weapon name cannot be null!");

        if (bonusDamage < 0) throw new InvalidDataException("Bonus damage cannot be less than zero!");
        this.bonusDamage = bonusDamage;

        if (bonusAttackSpeed <= -0.9) throw new InvalidDataException("Weapon cannot decrease attack speed by more than 0.9 seconds!");
        this.bonusAttackSpeed = bonusAttackSpeed;
    }

    public String getName() {
        return name;
    }

    public double getBonusDamage() {
        return bonusDamage;
    }

    public double getBonusAttackSpeed() {
        return bonusAttackSpeed;
    }

    @Override
    public String toString() {
        return String.format(
                "[Name: %s, Bonus Damages: %.2f, Bonus Attack Speed: %.2f]", name, bonusDamage, bonusAttackSpeed);
    }
}
