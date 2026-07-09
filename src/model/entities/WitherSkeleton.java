package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import exceptions.InvalidDataException;
import model.base.EffectApplier;
import model.base.Entity;
import model.base.Mob;

public class WitherSkeleton extends Mob implements EffectApplier {

    private final double witherEffectDamage = 1.0;

    public WitherSkeleton(String name, double healthPoints, double damagePoints, double attackSpeed)
            throws InvalidDataException {
        super(name, healthPoints, damagePoints, attackSpeed, true, LocationType.NETHER, MobBehavior.HOSTILE);
    }

    public double getWitherEffectDamage() {
        return witherEffectDamage;
    }

    @Override
    public void applyEffect(Entity target) {
        target.takeDamage(witherEffectDamage);
        System.out.println(target.getName() + " took " + witherEffectDamage + " damage from withering effect! HP left: "
                + target.getHealthPoints());
    }
}
