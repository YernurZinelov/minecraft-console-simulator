package model.entities;

import exceptions.InvalidDataException;
import model.items.Armor;
import model.items.Weapon;
import model.base.Entity;

public class Player extends Entity {

    private Weapon weapon;
    private Armor armor;
    private final double maxHealthPoints;

    public Player(String name, double healthPoints, double damagePoints, double attackSpeed)
            throws InvalidDataException {
        super(name, healthPoints, damagePoints, attackSpeed, false);
        this.weapon = null;
        this.armor = null;
        this.maxHealthPoints = healthPoints;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public double getMaxHealthPoints() {
        return maxHealthPoints;
    }

    @Override
    public double getDamagePoints() {
        double baseDamage = super.getDamagePoints();

        if (weapon != null) {
            return baseDamage + weapon.getBonusDamage();
        }
        return baseDamage;
    }

    @Override
    public double getAttackSpeed() {
        double baseAttackSpeed = super.getAttackSpeed();

        if (weapon != null) {
            return Math.max(0.1, baseAttackSpeed + weapon.getBonusAttackSpeed());
        }
        return baseAttackSpeed;
    }

    @Override
    public void takeDamage(double amount) {
        double finalDamage = amount;
        if (armor != null) {
            finalDamage = Math.max(0.0, (amount - armor.getProtectionPoints()));
        }
        super.takeDamage(finalDamage);
    }
}
