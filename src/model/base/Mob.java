package model.base;

import enums.LocationType;
import enums.MobBehavior;
import exceptions.InvalidDataException;

public abstract class Mob extends Entity {
    private LocationType mainLocation;
    private MobBehavior behavior;

    public Mob(String name, double healthPoints, double damagePoints, double attackSpeed,
               boolean hasSpecialEffect, LocationType mainLocation, MobBehavior behavior) {
        super(name, healthPoints, damagePoints, attackSpeed, hasSpecialEffect);
        this.mainLocation = mainLocation;
        this.behavior = behavior;
    }

    public LocationType getMainLocation() {
        return mainLocation;
    }

    public MobBehavior getBehavior() {
        return behavior;
    }
}
