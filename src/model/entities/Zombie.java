package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import exceptions.InvalidDataException;
import model.base.Mob;

public class Zombie extends Mob {

    public Zombie(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, false, LocationType.CAVE, MobBehavior.HOSTILE);
    }
}
