package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import model.base.Mob;

public class Vindicator extends Mob {

    public Vindicator(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, false, false, LocationType.PLAIN, MobBehavior.HOSTILE);
    }
}
