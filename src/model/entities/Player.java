package model.entities;

import model.items.Armor;
import model.items.Weapon;
import model.base.Entity;

public class Player extends Entity {

    private volatile Weapon weapon;
    private volatile Armor armor;
    private final double maxHealthPoints;

    public Player(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, false, false);
        this.weapon = null;
        this.armor = null;
        this.maxHealthPoints = getHealthPoints();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public synchronized void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public synchronized void setArmor(Armor armor) {
        this.armor = armor;
    }

    public double getMaxHealthPoints() {
        return maxHealthPoints;
    }

    @Override
    public double getDamagePoints() {
        double baseDamage = super.getDamagePoints();

        Weapon currentWeapon = this.weapon;

        if (currentWeapon != null) {
            return baseDamage + currentWeapon.getBonusDamage();
        }
        return baseDamage;
    }

    @Override
    public double getAttackSpeed() {
        double baseAttackSpeed = super.getAttackSpeed();

        Weapon currentWeapon = this.weapon;

        if (currentWeapon != null) {
            return Math.max(0.1, baseAttackSpeed + currentWeapon.getBonusAttackSpeed());
        }
        return baseAttackSpeed;
    }

    @Override
    public synchronized void takeDamage(double amount, boolean ignoreArmor) {
        double finalDamage = amount;

        Armor currentArmor = this.armor;

        if (currentArmor != null && !ignoreArmor) {
            finalDamage = Math.max(0.0, (amount - currentArmor.getProtectionPoints()));
        }
        super.takeDamage(finalDamage, ignoreArmor);
    }
}
