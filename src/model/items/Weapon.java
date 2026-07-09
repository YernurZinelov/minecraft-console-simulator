package model.items;

import exceptions.InvalidDataException;

public abstract class Weapon {

    private String name;
    private double bonusDamage;
    private double bonusAttackSpeed;

    public Weapon(String name, double bonusDamage, double bonusAttackSpeed) {
        this.name = name;

        if (bonusDamage < 0) {
            throw new InvalidDataException("Bonus damage cannot be less than zero!");
        }
        this.bonusDamage = bonusDamage;
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
