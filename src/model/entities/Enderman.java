package model.entities;

import enums.LocationType;
import enums.MobBehavior;
import exceptions.InvalidDataException;
import model.base.Mob;

public class Enderman extends Mob {

    public Enderman(String name, double healthPoints, double damagePoints, double attackSpeed) {
        super(name, healthPoints, damagePoints, attackSpeed, false, LocationType.END, MobBehavior.NEUTRAL);
    }
}
