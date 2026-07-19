package model.base;

import enums.LocationType;
import enums.MobBehavior;

import java.util.Objects;

public abstract class Mob extends Entity {
    private final LocationType mainLocation;
    private final MobBehavior behavior;

    public Mob(String name, double healthPoints, double damagePoints, double attackSpeed,
               boolean hasSpecialEffect, boolean ignoreArmor, LocationType mainLocation, MobBehavior behavior) {
        super(name, healthPoints, damagePoints, attackSpeed, hasSpecialEffect, ignoreArmor);
        this.mainLocation = Objects.requireNonNull(mainLocation, "Location type cannot be null!");
        this.behavior = Objects.requireNonNull(behavior, "Behavior type cannot be null!");
    }

    public LocationType getMainLocation() {
        return mainLocation;
    }

    public MobBehavior getBehavior() {
        return behavior;
    }
}
