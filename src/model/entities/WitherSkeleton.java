package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import model.effects.EffectApplier;
import model.base.Entity;
import model.base.Mob;
import service.Log;

public class WitherSkeleton extends Mob implements EffectApplier {

    public static final String EFFECT_NAME = "wither";
    private final double witherEffectDamage = 1.0;

    public WitherSkeleton(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, true, true, LocationType.NETHER, MobBehavior.HOSTILE);
    }

    public double getWitherEffectDamage() {
        return witherEffectDamage;
    }

    @Override
    public void applyEffect(final Entity target) {
        synchronized (target) {
            target.takeDamage(witherEffectDamage, true);
            Log.println(target.getName() + " took " + witherEffectDamage + " damage from withering effect! HP left: "
                    + target.getHealthPoints());
        }
    }

    @Override
    public int getTickPeriodSeconds() {
        return 2;
    }

    @Override
    public void attack(Entity target) {
        super.attack(target);
        int effectDurationSeconds = 10;
        target.applyActiveEffect(EFFECT_NAME, this, effectDurationSeconds);
    }
}
