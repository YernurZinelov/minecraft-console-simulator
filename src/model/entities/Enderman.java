package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import model.base.Mob;

public class Enderman extends Mob {

    public Enderman(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, false, false, LocationType.END, MobBehavior.NEUTRAL);
    }
}
