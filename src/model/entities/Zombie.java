package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import model.base.Mob;

public class Zombie extends Mob {

    public Zombie(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, false, false, LocationType.CAVE, MobBehavior.HOSTILE);
    }
}
